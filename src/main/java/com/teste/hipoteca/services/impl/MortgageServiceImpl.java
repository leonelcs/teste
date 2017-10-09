package com.teste.hipoteca.services.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teste.hipoteca.model.Amount;
import com.teste.hipoteca.model.InterestRate;
import com.teste.hipoteca.model.Mortgage;
import com.teste.hipoteca.repository.InterestRateRepository;
import com.teste.hipoteca.repository.MortgageRepository;
import com.teste.hipoteca.services.MortgageService;

/**
 * 
 * @author llsa
 * 
 */
@Service
public class MortgageServiceImpl implements MortgageService {

	@Autowired
	InterestRateRepository irRepository;
	
	@Autowired
	MortgageRepository mortRepository;
	
	@Override
	public List<InterestRate> findAllInterestRates() {
		return irRepository.findAll();
	}

	@Override
	public Optional<InterestRate> findInterestRateByMaturityPeriod(Integer maturityPeriod) {
		return irRepository.findInterestRateByMaturityPeriod(maturityPeriod);
	}
	
	/**
	 * the method is calculating the mortgage using the receipt
	 * MonthlyPayment = PrincipalValue * ( monthlyRate * ( 1 + monthlyRate )^(maturity*12) <- called rateFactor )/(rateFactor -1) )
	 * 
	 */
	@Override
	public Mortgage checkMortgage(Mortgage mortgage) {
		
		//find by the maturity level
		Optional<InterestRate> optIR = irRepository.findInterestRateByMaturityPeriod(mortgage.getMaturityPeriod());
		Mortgage mortReturn = new Mortgage(mortgage);
		
		if (optIR.isPresent()) {
			InterestRate ir = optIR.get();
			Double monthlyRate = calculateMonthlyRate(ir.getInterestRate());
			Double rateFactor = calculateRateFactor(monthlyRate, mortReturn.getMaturityPeriod());
			Double monthlyPayment = calculateMonthlyPayment(rateFactor, mortReturn.getLoanValue().getValue(), monthlyRate);

			BigDecimal mP = BigDecimal.valueOf(monthlyPayment).setScale(2, BigDecimal.ROUND_HALF_DOWN);
			mortReturn.setMonthlyPayment(new Amount(mP, mortgage.getLoanValue().getCurrency()));
			mortReturn.setIsFeasible(true);
			if (monthlyPayment > (mortReturn.getIncome().getValue().doubleValue()/4)) {
				mortReturn.setIsFeasible(false);
				return mortReturn;
			} else if (monthlyPayment*mortReturn.getMaturityPeriod()*12 > mortgage.getHomeValue().getValue().doubleValue()) {
				mortReturn.setIsFeasible(false);
				return mortReturn;
			}
		} else {
			mortReturn.setIsFeasible(false);
		}
		
		return mortReturn;
	}
	
	private Double calculateMonthlyPayment(Double rateFactor, BigDecimal principalAmount, Double monthlyRate) {
		Double aux = (monthlyRate*rateFactor)/(rateFactor-1);
		return principalAmount.doubleValue()*aux;
	}
	
	private Double calculateRateFactor(Double montlhyRate, Integer maturity) {
		return Math.pow((1+montlhyRate), maturity*12);
	}
	
	private Double calculateMonthlyRate(BigDecimal anualRate) {
		return (anualRate.doubleValue()/12)/100;
	}

}
