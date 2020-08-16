package com.analytics.processor.domain.sales.pojo;

import com.analytics.processor.domain.sales.enums.IDType;
import lombok.Getter;

@Getter
public class Client extends Person {

    private String cnpj;
    private String businessArea;

    public Client(String name, String businessArea, String cnpj) {
        super(IDType.CLIENT, name);
        this.businessArea = businessArea;
        this.cnpj = cnpj;
    }
}
