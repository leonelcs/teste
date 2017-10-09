package com.teste.hipoteca;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.teste.hipoteca.model.Amount;
import com.teste.hipoteca.model.Currency;
import com.teste.hipoteca.model.InterestRate;
import com.teste.hipoteca.model.Mortgage;
import com.teste.hipoteca.services.MortgageService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MortgageApplicationTests {
	
	@Autowired
	MortgageService mortService;

	@Test
	public void testInterestRateFindAll() {
		List<InterestRate> irList = mortService.findAllInterestRates();
		assertThat(irList.size(), is(20));
	}
	
	@Test
	public void testInterestRateByMaturityPeriod() {
		Optional<InterestRate> optIR = mortService.findInterestRateByMaturityPeriod(1);
		if (optIR.isPresent()) {
			InterestRate ir = optIR.get();
			assertThat(ir.getMaturityPeriod(), is(1));
		} else {
			assertFalse(true);
		}
	}
	
	@Test
	public void testInterestRateBy15() {
		Optional<InterestRate> optIR = mortService.findInterestRateByMaturityPeriod(15);
		if (optIR.isPresent()) {
			InterestRate ir = optIR.get();
			assertThat(ir.getInterestRate().doubleValue(), is(4.055));
		} else {
			assertFalse(true);
		}
	}
	
	@Test
	public void testCheckMortgage() {
		Mortgage mortgage = new Mortgage();
		mortgage.setHomeValue(new Amount(BigDecimal.valueOf(300000.00),Currency.EUR));
		mortgage.setIncome(new Amount(BigDecimal.valueOf(10000.00),Currency.EUR));
		mortgage.setLoanValue(new Amount(BigDecimal.valueOf(200000.00),Currency.EUR));
		mortgage.setMaturityPeriod(15);
		Mortgage returnMortgage = mortService.checkMortgage(mortgage);
		assertThat(returnMortgage.getMonthlyPayment().getValue().doubleValue(), Matchers.closeTo(1484.00, 1485.00));
	}
	
	@Test
	public void testCheckMortgageLowIncome() {
		Mortgage mortgage = new Mortgage();
		mortgage.setHomeValue(new Amount(BigDecimal.valueOf(300000.00),Currency.EUR));
		mortgage.setIncome(new Amount(BigDecimal.valueOf(2000.00),Currency.EUR));
		mortgage.setLoanValue(new Amount(BigDecimal.valueOf(200000.00),Currency.EUR));
		mortgage.setMaturityPeriod(15);
		Mortgage returnMortgage = mortService.checkMortgage(mortgage);
		assertThat(returnMortgage.getIsFeasible(), is(false));
	}
	
	@Test
	public void testCheckMortgageTotalAmountBiggerThanHome() {
		Mortgage mortgage = new Mortgage();
		mortgage.setHomeValue(new Amount(BigDecimal.valueOf(250000.00),Currency.EUR));
		mortgage.setIncome(new Amount(BigDecimal.valueOf(6000.00),Currency.EUR));
		mortgage.setLoanValue(new Amount(BigDecimal.valueOf(200000.00),Currency.EUR));
		mortgage.setMaturityPeriod(15);
		Mortgage returnMortgage = mortService.checkMortgage(mortgage);
		assertThat(returnMortgage.getIsFeasible(), is(false));
	}


}
