package com.analytics.processor.domain.sales.pojo;

import com.analytics.processor.domain.sales.enums.IDType;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class Sale implements Serializable {

    private IDType idType;
    private int id;
    private List<Item> items;
    private String salesmanName;

    public Sale(int id, List<Item> item, String salesmanName) {
        this.idType = IDType.SALE;
        this.id = id;
        this.items = item;
        this.salesmanName = salesmanName;
    }

    public Float getTotalSale() {
        var total = (float) 0;
//        for (Item item : items) {
//            total += item.getTotal();
//        }

        return total;
    }

}
