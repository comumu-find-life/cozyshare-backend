package com.core.domain.deal.repository;

import com.core.domain.deal.model.ProtectedDeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProtectedDealRepository extends JpaRepository<ProtectedDeal, Long>, CustomProtectedDealRepository {

}
