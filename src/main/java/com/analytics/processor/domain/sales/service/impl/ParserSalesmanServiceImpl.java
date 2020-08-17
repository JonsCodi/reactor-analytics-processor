package com.analytics.processor.domain.sales.service.impl;

import com.analytics.processor.domain.sales.pojo.Salesman;
import com.analytics.processor.domain.sales.service.IParserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Lazy
@Service
public class ParserSalesmanServiceImpl implements IParserService<Salesman> {

    @Override
    public List<Salesman> toParser(List<String> salesmanLineFile) {
        log.info("Service - toParser | list: {}", salesmanLineFile);

        return salesmanLineFile.stream().map(
                salesmanLine -> {
                    String[] salesmanSplitFile = salesmanLine.split("ç");

                    return new Salesman(salesmanSplitFile[1], salesmanSplitFile[2], new BigDecimal(salesmanSplitFile[3]));
                }).collect(Collectors.toList());
    }
}
