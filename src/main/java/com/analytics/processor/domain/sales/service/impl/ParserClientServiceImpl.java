package com.analytics.processor.domain.sales.service.impl;

import com.analytics.processor.domain.sales.pojo.Client;
import com.analytics.processor.domain.sales.service.IParserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ParserClientServiceImpl implements IParserService<Client> {

    @Override
    public List<Client> toParser(List<String> list) {
        log.info("Service - toParser | list: {}", list);

        return list.stream().map(
                clientsLine -> {
                    String[] clientsSplitFile = clientsLine.split("ç");

                    return new Client(clientsSplitFile[1], clientsSplitFile[2], clientsSplitFile[3]);
                }).collect(Collectors.toList());

    }
}
