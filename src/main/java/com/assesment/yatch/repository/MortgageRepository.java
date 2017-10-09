package com.assesment.yatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assesment.yatch.model.Mortgage;

public interface MortgageRepository extends JpaRepository<Mortgage, Long> {

}
