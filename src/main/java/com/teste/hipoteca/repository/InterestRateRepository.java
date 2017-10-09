package com.teste.hipoteca.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teste.hipoteca.model.InterestRate;

@Repository
public interface InterestRateRepository extends JpaRepository<InterestRate, Long> {
	
	public Optional<InterestRate> findInterestRateByMaturityPeriod(Integer maturityPeriod);

}
