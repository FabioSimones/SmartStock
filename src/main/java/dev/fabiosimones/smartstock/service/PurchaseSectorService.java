package dev.fabiosimones.smartstock.service;

import dev.fabiosimones.smartstock.client.PurchaseSectorClient;
import dev.fabiosimones.smartstock.client.dto.PurchaseRequest;
import dev.fabiosimones.smartstock.domain.CsvStockItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PurchaseSectorService {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseSectorService.class);

    private final AuthService authService;
    private final PurchaseSectorClient purchaseSectorClient;

    public PurchaseSectorService(AuthService authService, PurchaseSectorClient purchaseSectorClient) {
        this.authService = authService;
        this.purchaseSectorClient = purchaseSectorClient;
    }

    public boolean sendPurchaseRequest(CsvStockItem item,
                                       Integer purchaseQuantity){
        // Autenticação na api para recuperar token
        var token = authService.getToken();

        //Enviar solicitacao de compra
        var request = new PurchaseRequest(
                item.getItemId(),
                item.getItemName(),
                item.getSupplierName(),
                item.getSupplierEmail(),
                purchaseQuantity
        );

        var response = purchaseSectorClient.sendPurchaseRequest(token, request);

        //Validar se a resposta veio com sucesso.
        if(response.getStatusCode().value() != HttpStatus.ACCEPTED.value()){
            logger.error("Error while sending purchase request, Status: {}, Response: {}",
                    response.getStatusCode(), response.getBody());

            return false;
        }

        return true;
    }
}
