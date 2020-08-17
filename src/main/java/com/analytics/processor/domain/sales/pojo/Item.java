package com.analytics.processor.domain.sales.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class Item implements Serializable {

    private int id;
    private int quantity;
    private float price;

    public float getTotal() {
        return price * quantity;
    }

}
