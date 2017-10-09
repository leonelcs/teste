package com.assesment.yatch.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author llsa
 * Mortgage entity - Can hold a relation with customer and contract in the big picture.
 * The mortgage is persisted when customer confirm it, but it won't when it is a simulation 
 */
@Data
@Entity
@NoArgsConstructor
public class Mortgage {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id", nullable=false, updatable=false)
	private Long id;
	
	/*
	 * 
	 * @Column(name="customer")
	 * private Customer customer;
	 */
	
	@Embedded @AttributeOverrides({@AttributeOverride(name = "currency", column = @Column(name = "income_currency")),
								   @AttributeOverride(name = "value", column = @Column(name = "income_value"))})
	private Amount income;
	
	@Column(name="maturity_period", nullable=false)
	private Integer maturityPeriod;
	
	@Embedded @AttributeOverrides({@AttributeOverride(name = "currency", column = @Column(name = "loanvalue_currency")),
		   @AttributeOverride(name = "value", column = @Column(name = "loanvalue_value"))})
	private Amount loanValue;
	
	@Embedded @AttributeOverrides({@AttributeOverride(name = "currency", column = @Column(name = "homevalue_currency")),
		   @AttributeOverride(name = "value", column = @Column(name = "homevalue_value"))})
	private Amount homeValue;
	
	@Column(name="feasible", nullable=true)
	private Boolean isFeasible;
	
	@Embedded @AttributeOverrides({@AttributeOverride(name = "currency", column = @Column(name = "monthlypay_currency")),
		   @AttributeOverride(name = "value", column = @Column(name = "monthlypay_value"))})
	private Amount monthlyPayment;
	
	/**
	 * copy mortgage
	 * @param m
	 */
	public Mortgage(Mortgage m){
		this.id = m.id;
		this.homeValue = m.homeValue;
		this.loanValue = m.loanValue;
		this.maturityPeriod = m.maturityPeriod;
		this.income = m.income;
		this.monthlyPayment = m.monthlyPayment;
		this.isFeasible = m.isFeasible;
	}

}
