package com.teste.hipoteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teste.hipoteca.model.Mortgage;

public interface MortgageRepository extends JpaRepository<Mortgage, Long> {

}
