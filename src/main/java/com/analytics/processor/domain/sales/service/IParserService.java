package com.analytics.processor.domain.sales.service;

import java.util.List;

public interface IParserService<T> {

    List<T> toParser(List<String> list);

}
