package dev.fabiosimones.smartstock.service;

import dev.fabiosimones.smartstock.domain.CsvStockItem;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SmartStockService {

    private final ReportService reportService;
    private final PurchaseSectorService purchaseSectorService;

    public SmartStockService(ReportService reportService, PurchaseSectorService purchaseSectorService) {
        this.reportService = reportService;
        this.purchaseSectorService = purchaseSectorService;
    }

    public void process(String reportPath){
        //Ler o arquivo csv
        try {
            var items = reportService.readStockReport(reportPath);

            items.forEach(item -> {
                if(item.getQuantity() < item.getReorderThreshould()){
                    //Calcular a quantidade a ser recomprada.
                    var reorderQuantity = calculateReorderQuantity(item);

                    //Para cada item do CSV chamar a api do setor de compras.
                    purchaseSectorService.sendPurchaseRequest(item, reorderQuantity);

                    //Salvar no mongodb os itens que foram recomprados.
                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private Integer calculateReorderQuantity(CsvStockItem item) {
        return item.getReorderThreshould() + ((int) Math.ceil(item.getReorderThreshould()*0.2));
    }
}
