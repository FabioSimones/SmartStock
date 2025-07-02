package dev.fabiosimones.smartstock.controller;

import dev.fabiosimones.smartstock.controller.dto.StartDTO;
import dev.fabiosimones.smartstock.service.SmartStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class StartController {

    private final SmartStockService smartStockService;

    public StartController(SmartStockService smartStockService) {
        this.smartStockService = smartStockService;
    }

    @PostMapping(path = "/start")
    public ResponseEntity<Void> start(@RequestBody StartDTO dto){

        CompletableFuture.runAsync(() -> {
            smartStockService.process(dto.reportPath());
        });

        return ResponseEntity.accepted().build();
    }

}
