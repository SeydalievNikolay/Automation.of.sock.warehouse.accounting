package com.example.skypro.automation.of.sock.warehouse.accounting.service;

import com.example.skypro.automation.of.sock.warehouse.accounting.dto.SocksDto;
import java.util.Optional;

public interface SocksService {

    SocksDto incomeOfSocks(SocksDto socksDto);

    SocksDto outcomeOfSocks(SocksDto socksDto);

    Optional<Integer> getSocksAmount(String color, Operation operation, int cottonPart);
}
