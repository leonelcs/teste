package com.assesment.yatch.services;

import java.util.List;
import java.util.Optional;

import com.assesment.yatch.model.InterestRate;
import com.assesment.yatch.model.Mortgage;

public interface MortgageService {
	
	public List<InterestRate> findAllInterestRates();
	
	public Optional<InterestRate> findInterestRateByMaturityPeriod(Integer maturityPeriod);
	
	public Mortgage checkMortgage(Mortgage mortgage);
	

}
