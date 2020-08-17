package com.analytics.processor.domain.sales.pojo;

import com.analytics.processor.domain.sales.enums.IDType;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Salesman extends Person {

    private String cpf;
    private BigDecimal salary;

    public Salesman(String cpf, String name, BigDecimal salary) {
        super(IDType.SALESMAN, name);
        this.cpf = cpf;
        this.salary = salary;
    }
}
