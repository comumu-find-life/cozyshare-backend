package com.core.home.repository;

import com.core.home.model.HomeDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface HomeElasticRepository extends ElasticsearchRepository<HomeDocument, Long> {
}
