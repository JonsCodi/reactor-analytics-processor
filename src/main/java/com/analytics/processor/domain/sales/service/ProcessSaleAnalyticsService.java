package com.analytics.processor.domain.sales.service;

import com.analytics.processor.config.HomePathProperties;
import com.analytics.processor.domain.sales.enums.IDType;
import com.analytics.processor.domain.sales.mapper.GenerateListMapperOfFiles;
import com.analytics.processor.domain.sales.pojo.Client;
import com.analytics.processor.domain.sales.pojo.Sale;
import com.analytics.processor.domain.sales.pojo.Salesman;
import com.analytics.processor.service.GenerateAnalysisService;
import com.analytics.processor.service.IFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProcessSaleAnalyticsService {

    private final GenerateListMapperOfFiles generateListMapperOfFiles;
    private final GenerateAnalysisService generateAnalysisService;
    private final HomePathProperties pathProperties;

    private final IFileService fileService;

    private final IParserService<Salesman> parserSalesmanService;
    private final IParserService<Sale> parserSaleService;
    private final IParserService<Client> parserClientService;

    public void processFiles(Set<ChangedFile> setChangedFile) {
        log.info("Service - Process Files... [Started]");

        String home = System.getProperty("user.home");

        List<File> files = fileService.getRecentAddedFiles(setChangedFile);

        files.forEach(file -> {
            String fileAsString = fileService.fileToStringList(file);

            Map<IDType, List<String>> map = generateListMapperOfFiles.mapperType(fileAsString);

            List<String> clientAsString = map.get(IDType.CLIENT);
            List<String> saleAsString = map.get(IDType.SALE);
            List<String> salesmanAsString = map.get(IDType.SALESMAN);

            List<Client> clients = parserClientService.toParser(clientAsString);
            List<Sale> sales = parserSaleService.toParser(saleAsString);
            List<Salesman> salesman = parserSalesmanService.toParser(salesmanAsString);

            String result = generateAnalysisService.generateLineAnalyses(clients, sales, salesman);

            log.info("Arquivo: {} | Resultado -> {}", file.getName(), result);

            Path path = Paths.get(
                    home.concat(pathProperties.getDataOutPath()).concat(file.getName()));

            fileService.create(path, result);
        });
    }
}
