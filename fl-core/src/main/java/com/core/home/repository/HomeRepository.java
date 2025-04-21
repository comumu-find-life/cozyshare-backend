package com.core.home.repository;

import com.core.home.model.Home;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeRepository extends JpaRepository<Home, Long>, CustomHomeRepository {
}
