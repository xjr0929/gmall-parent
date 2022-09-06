package com.atguigu.gmall.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @Author xjrstart
 * @Date 2022-09-06-23:53
 */
@Component
public class GlobalAuthFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return null;
    }

    public static void main(String[] args) throws InterruptedException {

        /*
        * Flux 和 Mono 是 Reactor 中的两个基本概念。Flux 表示的是包含 0 到 N 个元素的异步序列。
        * 在该序列中可以包含三种不同类型的消息通知：正常的包含元素的消息、序列结束的消息和序列出错的消息。
        * 当消息通知产生时，订阅者中对应的方法 onNext(), onComplete()和 onError()会被调用。Mono 表示的是包含 0 或者 1 个元素的异步序列。
        * 该序列中同样可以包含与 Flux 相同的三种类型的消息通知。Flux 和 Mono 之间可以进行转换。对一个 Flux 序列进行计数操作，
        * 得到的结果是一个 Mono对象。把两个 Mono 序列合并在一起，得到的是一个 Flux 对象。
        * */
        //1、单数据流。 数据发布者
//        Mono<Integer> just = Mono.just(1);
        Flux<Long> flux = Flux.interval(Duration.ofSeconds(1));

        //2、数据订阅者，感兴趣发布者的数据
        flux.subscribe((t)->{
            System.out.println("消费者1："+t);
        });


        flux.subscribe((t)->{
            System.out.println("消费者2："+t);
        });


        flux.subscribe((t)->{
            System.out.println("消费者3："+t);
        });

        Thread.sleep(100000l);
    }
}




























