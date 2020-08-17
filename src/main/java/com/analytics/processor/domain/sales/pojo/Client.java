package com.analytics.processor.domain.sales.pojo;

import com.analytics.processor.domain.sales.enums.IDType;
import lombok.Getter;

@Getter
public class Client extends Person {

    private String cnpj;
    private String businessArea;

    public Client(String name, String cnpj, String businessArea) {
        super(IDType.CLIENT, name);
        this.cnpj = cnpj;
        this.businessArea = businessArea;
    }
}
