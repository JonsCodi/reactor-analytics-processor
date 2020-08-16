package com.analytics.processor.domain.sales.pojo;

import com.analytics.processor.domain.sales.enums.IDType;
import lombok.Getter;

import java.io.Serializable;

@Getter
public abstract class Person implements Serializable {

    private IDType idType;
    private String name;

    public Person(IDType idType, String name) {
        this.idType = idType;
        this.name = name;
    }
}
