package dev.fabiosimones.smartstock.service;

import dev.fabiosimones.smartstock.domain.CsvStockItem;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SmartStockService {

    private final ReportService reportService;

    public SmartStockService(ReportService reportService) {
        this.reportService = reportService;
    }

    public void process(String reportPath){
        //Ler o arquivo csv
        try {
            var items = reportService.readStockReport(reportPath);

            items.forEach(item -> {
                if(item.getQuantity() < item.getReorderThreshould()){
                    //Calcular a quantidade a ser recomprada.
                    var reorderQuantity = calculateReorderQuantity(item);
                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Para cada item do CSV chamar a api do setor de compras.

        //Salvar no mongodb os itens que foram recomprados.
    }

    private Integer calculateReorderQuantity(CsvStockItem item) {
        return item.getReorderThreshould() + ((int) Math.ceil(item.getReorderThreshould()*0.2));
    }
}
