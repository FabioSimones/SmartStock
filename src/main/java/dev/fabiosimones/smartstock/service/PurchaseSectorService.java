package dev.fabiosimones.smartstock.service;

import dev.fabiosimones.smartstock.domain.CsvStockItem;
import org.springframework.stereotype.Service;

@Service
public class PurchaseSectorService {

    private final AuthService authService;

    public PurchaseSectorService(AuthService authService) {
        this.authService = authService;
    }

    public boolean sendPurchaseRequest(CsvStockItem item,
                                       Integer purchaseQuantity){
        // Autenticação na api para recuperar token
        var token = authService.getToken();

        //Enviar solicitacao de compra

        return false;
    }
}
