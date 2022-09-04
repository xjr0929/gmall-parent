package com.atguigu.gmall.search.repository;

import com.atguigu.gmall.search.bean.Person;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends PagingAndSortingRepository<Person,Long> {

    @Query("{\n" +
            "  \"query\": {\n" +
            "    \"match\": {\n" +
            "      \"address\": \"?0\"\n" +
            "    }\n" +
            "  }\n" +
            "}")
    List<Person> aaaa(String add);
}































