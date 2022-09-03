package com.atguigu.starter.cache.aspect;

import com.atguigu.starter.cache.constant.SysRedisConst;
import com.atguigu.starter.cache.service.CacheOpsService;
import com.atguigu.starter.cache.annotation.GmallCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @Author xjrstart
 * @Date 2022-09-01-19:36
 */
@Aspect //声明这是一个切面
@Component
public class CacheAspect {

    @Autowired
    CacheOpsService cacheOpsService;

    // 创建一个表达式解析器，线程安全的
    ExpressionParser parser = new SpelExpressionParser();
    ParserContext context = new TemplateParserContext();

    @Around("@annotation(com.atguigu.starter.cache.annotation.GmallCache)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        // key不同方法可能不一样
        String cacheKey = determinCacheKey(joinPoint);
        // 1. 先查缓存  不同方法返回数据不一样
        Type returnType = getMethodGenericReturnType(joinPoint);
        //SkuDetailTo cacheData = cacheOpsService.getCacheData(cacheKey, SkuDetailTo.class);
        Object cacheData = cacheOpsService.getCacheData(cacheKey,returnType);
        // 2.缓存
        if (cacheData == null){
            //3. 说明缓存中没有
            //4. 先问布隆  有些场景并不一定需要布隆，比如，三级分类(只有一个大数据)
            //boolean contains = cacheOpsService.bloomContains(arg);
            String bloomName = determinBloomName(joinPoint);
            if (!StringUtils.isEmpty(bloomName)) {
                // 指定开启的布隆
                Object bVal = determinBloomValue(joinPoint);
                boolean contains = cacheOpsService.bloomContains(bloomName, bVal);
                if (!contains) { // 布隆说没有
                    return null;
                }
            }
            // 5.布隆说有，准备回源 有击穿风险
            boolean lock = false;
            String lockName ="";
            try {
                // 不同场景用自己的锁
                lockName = determinLockName(joinPoint);
            lock = cacheOpsService.tryLock(lockName);
            if (lock){
                // 6.获得锁，开始回源 ,调用目标方法
                result = joinPoint.proceed(joinPoint.getArgs());
                // 7.调用成功，重新保存到缓存
                long ttl = determinTtl(joinPoint);
                cacheOpsService.saveData(cacheKey,result,ttl);
                return result;
            }else {
                // 没有获取到锁，等一秒直接返回查询到的值
                Thread.sleep(1000l);
                return cacheOpsService.getCacheData(cacheKey, returnType);
            }
            }finally{
                // 解锁  需要判断，加上锁后才需要解锁
                if (lock) cacheOpsService.unlock(lockName);
            }
        }
        //  缓存中有的话直接返回  可以再这里修改返回值
        return cacheData;
    }

    private long determinTtl(ProceedingJoinPoint joinPoint) {
        // 拿到方法签名
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        // 通过方法名 拿到目标方法
        Method method = signature.getMethod();
        // 通过反射用目标方法拿到方法上的所有注解
        GmallCache cacheAnnotation = method.getDeclaredAnnotation(GmallCache.class);
        // 获取注解中的ttl
        long ttl = cacheAnnotation.ttl();
        return ttl;
    }

    // 根据表达式计算出要用到锁的名字
    private String determinLockName(ProceedingJoinPoint joinPoint) {
        // 拿到方法签名
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        // 通过方法名 拿到目标方法
        Method method = signature.getMethod();
        // 通过反射用目标方法拿到方法上的所有注解
        GmallCache cacheAnnotation = method.getDeclaredAnnotation(GmallCache.class);
        // 拿到锁表达式
        String lockName = cacheAnnotation.lockName();
        if (StringUtils.isEmpty(lockName)){
            return SysRedisConst.LOCK_PREFIX + method.getName();
        }
        // 计算锁值
        String lockNameVal = evaluationExpression(lockName, joinPoint, String.class);
        return lockNameVal;
    }

    //根据布隆过滤器表达式计算出布隆需要判定的值
    private Object determinBloomValue(ProceedingJoinPoint joinPoint) {
        // 拿到方法签名
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        // 通过方法名 拿到目标方法
        Method method = signature.getMethod();
        // 通过反射用目标方法拿到方法上的所有注解
        GmallCache cacheAnnotation = method.getDeclaredAnnotation(GmallCache.class);
        // 拿到布隆的vlaue
        String bloomValue = cacheAnnotation.bloomValue();
        Object expression = evaluationExpression(bloomValue, joinPoint, Object.class);
        return expression;
    }

    // 获取布隆过滤器的名字
    private String determinBloomName(ProceedingJoinPoint joinPoint) {
        // 拿到方法签名
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        // 通过方法名 拿到目标方法
        Method method = signature.getMethod();
        // 通过反射用目标方法拿到方法上的所有注解
        GmallCache cacheAnnotation = method.getDeclaredAnnotation(GmallCache.class);

        // 获取布隆过滤器的名字
        String bloomName = cacheAnnotation.bloomName();

        return bloomName;

    }

    //获取目标方法的精确返回值类型
    private Type getMethodGenericReturnType(ProceedingJoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        Type type = method.getGenericReturnType();

        return type;
    }

    // 根据当前整个连接点的执行信息，确定缓存用什么key
    private String determinCacheKey(ProceedingJoinPoint joinPoint) {
    //拿到目标方法上的@GmallCache注解
        // 拿到方法签名
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        // 通过方法名 拿到目标方法
        Method method = signature.getMethod();
        // 通过反射用目标方法拿到方法上的所有注解
        GmallCache cacheAnnotation = method.getDeclaredAnnotation(GmallCache.class);

        // 看注解中的cacheKey
        String expression = cacheAnnotation.cacheKey();

        // 3.根据表达式计算缓存键
        String cacheKey = evaluationExpression(expression,joinPoint,String.class);

        return cacheKey;
    }

    private<T> T evaluationExpression(String expression,
                                        ProceedingJoinPoint joinPoint,
                                        Class<T> clz) {
        // 得到表达式  expression   context  在类开始new  线程安全
        Expression exp = parser.parseExpression(expression, context);
        // sku:info:#{#params[0]}
        StandardEvaluationContext evaluationContext= new StandardEvaluationContext();
        // 去除所有参数，绑定到上下文
        Object[] args = joinPoint.getArgs();
        evaluationContext.setVariable("params",args);

        //得到表达式的值
        T expValue = exp.getValue(evaluationContext, clz);

        return expValue;

    }
}























