package com.atguigu.gmall.search;

import com.atguigu.gmall.search.bean.Person;
import com.atguigu.gmall.search.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author xjrstart
 * @Date 2022-09-03-21:33
 */
@SpringBootTest
public class EsTest {

    @Autowired
    PersonRepository personRepository;

    @Test
    public void saveTest(){
        Person person = new Person();
        person.setId(1L);
        person.setFirstName("薛");
        person.setLastName("杰仁");
        person.setAge(18);
        person.setAddress("北京市昌平区");

        personRepository.save(person);

        System.out.println("完成...");
    }
}

























