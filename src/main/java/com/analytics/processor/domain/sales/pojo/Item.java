package com.analytics.processor.domain.sales.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Item implements Serializable {

    private int id;
    private int quantity;
    private BigDecimal price;

}
