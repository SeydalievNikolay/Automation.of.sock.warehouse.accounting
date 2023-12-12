package com.example.skypro.automation.of.sock.warehouse.accounting.controller;

import com.example.skypro.automation.of.sock.warehouse.accounting.model.Socks;
import com.example.skypro.automation.of.sock.warehouse.accounting.repository.SocksRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class Constants {
    private final SocksRepository socksRepository;
    public Socks addDB() {
        Socks socks = new Socks();
        socks.setColor("green");
        socks.setCottonPart(90);
        socks.setQuantity(3);
        return socksRepository.save(socks);
    }
}
