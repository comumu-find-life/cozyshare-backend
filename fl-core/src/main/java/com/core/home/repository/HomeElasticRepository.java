//package com.core.home.repository;
//
//import com.core.home.model.HomeDocument;
//import com.core.home.model.HomeStatus;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//
//import java.util.List;
//
//
//public interface HomeElasticRepository extends ElasticsearchRepository<HomeDocument, Long>, CustomHomeElasticRepository {
//    List<HomeDocument> findByHomeStatusIn(List<HomeStatus> statuses);
//}
