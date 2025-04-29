//package com.core.home.repository.impl;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
//import org.springframework.data.elasticsearch.core.SearchHits;
//import org.springframework.data.elasticsearch.core.query.Criteria;
//import org.springframework.data.elasticsearch.core.query.Query;
//import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
//import com.core.home.model.HomeDocument;
//import com.core.home.repository.CustomHomeElasticRepository;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//public class CustomHomeElasticRepositoryImpl implements CustomHomeElasticRepository {
//
//    private final ElasticsearchOperations elasticsearchOperations;
//
//    @Override
//    public List<HomeDocument> findByAddress(String keyword) {
//        Criteria criteria = Criteria.where("address").contains(keyword);
//        Query query = new CriteriaQuery(criteria);
//        SearchHits<HomeDocument> result = elasticsearchOperations.search(query, HomeDocument.class);
//        return result.getSearchHits().stream()
//                .map(hit -> hit.getContent())
//                .collect(Collectors.toList());
//    }
//}
