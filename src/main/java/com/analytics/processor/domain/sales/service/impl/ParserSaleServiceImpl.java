package com.analytics.processor.domain.sales.service.impl;

import com.analytics.processor.domain.sales.enums.IDType;
import com.analytics.processor.domain.sales.pojo.Item;
import com.analytics.processor.domain.sales.pojo.Sale;
import com.analytics.processor.domain.sales.pojo.Salesman;
import com.analytics.processor.domain.sales.service.IParserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ParserSaleServiceImpl implements IParserService<Sale> {

    @Override
    public List<Sale> toParser(List<String> list) {
        log.info("Service - toParser | list: {}", list);

        return list.stream().map(
                salesmanLine -> {
                    String[] salesSplitFile = salesmanLine.split("ç");

                    List<Item> items = parseItems(salesSplitFile[2]);

                    return new Sale(Integer.parseInt(salesSplitFile[1]), items, salesSplitFile[3]);
                }).collect(Collectors.toList());
    }

    private List<Item> parseItems(String salesSplitFile) {
        String[] itemsAsString = salesSplitFile.replace("[", "")
                .replace("]", "")
                .split(",");

        List<Item> items = new ArrayList<>();

        for (String itemAsString : itemsAsString) {
            String[] itemsColumn = itemAsString.split("-");

            items.add(new Item(Integer.parseInt(itemsColumn[0]), Integer.parseInt(itemsColumn[1]),
                    Float.parseFloat(itemsColumn[2])));
        }
        return items;
    }
}
