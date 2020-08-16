package com.analytics.processor.domain.sales.enums;

import lombok.Getter;

public enum IDType {

    SALESMAN("001"),
    CLIENT("002"),
    SALE("003");

    @Getter
    private String id;

    IDType(String id) {
        this.id = id;
    }
}
