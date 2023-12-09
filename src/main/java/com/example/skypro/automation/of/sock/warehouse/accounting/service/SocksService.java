package com.example.skypro.automation.of.sock.warehouse.accounting.service;

import com.example.skypro.automation.of.sock.warehouse.accounting.model.Socks;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface SocksService {

    ResponseEntity<?> incomeOfSocks(Socks socks);

    ResponseEntity<?> outcomeOfSocks(Socks socks);

    Optional<Integer> getSocksAmount(String color, Operation operation, int cottonPart);

    List<Socks> getAll();
}
