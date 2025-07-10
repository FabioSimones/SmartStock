package dev.fabiosimones.smartstock.service;

import dev.fabiosimones.smartstock.domain.CsvStockItem;
import dev.fabiosimones.smartstock.entity.PurchaseRequestEntity;
import dev.fabiosimones.smartstock.repository.PurchaseRequestRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class SmartStockService {

    private final ReportService reportService;
    private final PurchaseSectorService purchaseSectorService;
    private final PurchaseRequestRepository repository;

    public SmartStockService(ReportService reportService, PurchaseSectorService purchaseSectorService, PurchaseRequestRepository repository) {
        this.reportService = reportService;
        this.purchaseSectorService = purchaseSectorService;
        this.repository = repository;
    }

    public void process(String reportPath){
        //Ler o arquivo csv
        try {
            var items = reportService.readStockReport(reportPath);

            items.forEach(item -> {
                if(item.getQuantity() < item.getReorderThreshold()){
                    //Calcular a quantidade a ser recomprada.
                    var reorderQuantity = calculateReorderQuantity(item);

                    //Para cada item do CSV chamar a api do setor de compras.
                    var purchasedWithSuccess = purchaseSectorService.sendPurchaseRequest(item, reorderQuantity);

                    //Salvar no mongodb os itens que foram recomprados.
                    persist(item, reorderQuantity, purchasedWithSuccess);
                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void persist(CsvStockItem item,
                         Integer reorderQuantity,
                         boolean purchasedWithSuccess) {

        var entity = new PurchaseRequestEntity();
        entity.setItemId(item.getItemId());
        entity.setItemName(item.getItemName());
        entity.setQuantityOnStock(item.getQuantity());
        entity.setReorderThreshold(item.getReorderThreshold());
        entity.setSupplierName(item.getSupplierName());
        entity.setSupplierEmail(item.getSupplierEmail());
        entity.setLastStockUpdateTime(LocalDateTime.parse(item.getLastStockUpdateTime()));

        entity.setPurchaseQuantity(reorderQuantity);
        entity.setPurchaseWithSuccess(purchasedWithSuccess);
        entity.setPurchaseDateTime(LocalDateTime.now());

        repository.save(entity);
    }

    private Integer calculateReorderQuantity(CsvStockItem item) {
        return item.getReorderThreshold() + ((int) Math.ceil(item.getReorderThreshold()*0.2));
    }
}
