package com.example.skypro.automation.of.sock.warehouse.accounting.service;

import com.example.skypro.automation.of.sock.warehouse.accounting.dto.SocksDto;
import com.example.skypro.automation.of.sock.warehouse.accounting.model.Socks;

import java.util.Optional;

public interface SocksService {

    SocksDto incomeOfSocks(SocksDto socksDto);

    void outcomeOfSocks(SocksDto socksDto);

    Integer getSocksAmount(String color, Operation operation, int cottonPart);

    void deleteCertainQuantityOfSocks(Socks socks, int quantity);
}
