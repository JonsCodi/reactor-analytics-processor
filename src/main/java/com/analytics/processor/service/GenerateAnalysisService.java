package com.analytics.processor.service;

import com.analytics.processor.domain.sales.pojo.Client;
import com.analytics.processor.domain.sales.pojo.Sale;
import com.analytics.processor.domain.sales.pojo.Salesman;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class GenerateAnalysisService {

    public String generateLineAnalyses(List<Client> clients, List<Sale> sales, List<Salesman> salesmen) {
        StringBuilder stringBuilder = new StringBuilder();
        generateClientAnalysis(stringBuilder, clients);
        generateSalesmanAnalysis(stringBuilder, salesmen);
        generateSalesAnalysis(stringBuilder, sales);

        return stringBuilder.toString();
    }

    private void generateClientAnalysis(StringBuilder stringBuilder, List<Client> clients) {
        int clientsQuantity = clients == null ? 0 : clients.size();

        stringBuilder.append("Quantity clients: ").append(clientsQuantity).append("\r\n");
    }

    private void generateSalesmanAnalysis(StringBuilder stringBuilder, List<Salesman> salesmen) {
        var salesmanQuantity = salesmen == null ? 0 : salesmen.size();

        stringBuilder.append("Salesman quantity: ").append(salesmanQuantity).append("\r\n");
    }

    private void generateSalesAnalysis(StringBuilder stringBuilder, List<Sale> sales) {
        var salesmanOptional = sales.stream().max(Comparator.comparing(Sale::getTotalSale));
        salesmanOptional.ifPresent(sale ->
                stringBuilder.append("Most expensive sale: ").append(sale.getId()).append("\r\n")
        );

        Map<String, Float> salesPerSaleman = new HashMap<>();
        sales.forEach(
                sale -> {
                    if (Objects.isNull(salesPerSaleman.get(sale.getSalesmanName()))) {
                        salesPerSaleman.put(sale.getSalesmanName(), Float.valueOf("0"));
                    }

                    var totalSale = salesPerSaleman.get(sale.getSalesmanName()) + sale.getTotalSale();
                    salesPerSaleman.put(sale.getSalesmanName(), totalSale);
                });
        var worstSeller = salesPerSaleman.entrySet().stream().min(Map.Entry.comparingByValue());

        worstSeller.ifPresent(sale -> stringBuilder
                .append("Worst seller: ")
                .append(sale.getKey())
                .append("\r\n"));
    }
}
