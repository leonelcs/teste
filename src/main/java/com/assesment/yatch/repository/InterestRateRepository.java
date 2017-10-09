package com.assesment.yatch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assesment.yatch.model.InterestRate;

@Repository
public interface InterestRateRepository extends JpaRepository<InterestRate, Long> {
	
	public Optional<InterestRate> findInterestRateByMaturityPeriod(Integer maturityPeriod);

}
