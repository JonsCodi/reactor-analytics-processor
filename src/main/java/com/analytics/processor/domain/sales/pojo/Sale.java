package com.analytics.processor.domain.sales.pojo;

import com.analytics.processor.domain.sales.enums.IDType;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class Sale implements Serializable {

    private IDType idType;
    private int saleId;
    private Item item;
    private String salesmanName;

    public Sale(int saleId, Item item, String salesmanName) {
        this.idType = IDType.SALE;
        this.saleId = saleId;
        this.item = item;
        this.salesmanName = salesmanName;
    }
}
