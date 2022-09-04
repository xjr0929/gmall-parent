package com.atguigu.gmall.search.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepository extends ElasticsearchRepository {
}
