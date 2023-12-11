package com.example.skypro.automation.of.sock.warehouse.accounting.service;

import com.example.skypro.automation.of.sock.warehouse.accounting.dto.SocksDto;
import com.example.skypro.automation.of.sock.warehouse.accounting.model.Socks;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface SocksService {

    SocksDto incomeOfSocks(SocksDto socksDto);

    SocksDto outcomeOfSocks(SocksDto socksDto);

    Optional<String> getSocksAmount(String color, Operation operation, int cottonPart);

    List<Socks> getAll();
}
