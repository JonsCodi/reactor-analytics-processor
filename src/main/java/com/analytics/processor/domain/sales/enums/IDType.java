package com.analytics.processor.domain.sales.enums;

import lombok.Getter;

import java.util.Arrays;

public enum IDType {

    SALESMAN("001"),
    CLIENT("002"),
    SALE("003");

    @Getter
    private String id;

    IDType(String id) {
        this.id = id;
    }

    public static IDType getBy(String id) {
        return Arrays.stream(values())
                .filter((type) -> type.getId().equals(id)).findAny()
                .orElse(null);
    }

}
