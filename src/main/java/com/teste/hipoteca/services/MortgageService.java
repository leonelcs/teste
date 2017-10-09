package com.teste.hipoteca.services;

import java.util.List;
import java.util.Optional;

import com.teste.hipoteca.model.InterestRate;
import com.teste.hipoteca.model.Mortgage;

public interface MortgageService {
	
	public List<InterestRate> findAllInterestRates();
	
	public Optional<InterestRate> findInterestRateByMaturityPeriod(Integer maturityPeriod);
	
	public Mortgage checkMortgage(Mortgage mortgage);
	

}
