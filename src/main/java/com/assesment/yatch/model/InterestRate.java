package com.assesment.yatch.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class InterestRate {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name = "maturity_period", nullable = false)
	private Integer maturityPeriod;
	
	@Column(name="interest_rate", nullable=false, precision = 5, scale = 4)
	private BigDecimal interestRate;
	
	@Column(name="last_update", nullable=false)
	private Date lastUpdate;
	
	public InterestRate(Integer maturiryPeriod, BigDecimal interestRate) {
		this.maturityPeriod = maturiryPeriod;
		this.interestRate = interestRate;
		this.lastUpdate = Date.valueOf(LocalDate.now());
	}

}
