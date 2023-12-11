package com.example.skypro.automation.of.sock.warehouse.accounting.controller;

import com.example.skypro.automation.of.sock.warehouse.accounting.dto.SocksDto;
import com.example.skypro.automation.of.sock.warehouse.accounting.model.Socks;
import com.example.skypro.automation.of.sock.warehouse.accounting.service.Operation;
import com.example.skypro.automation.of.sock.warehouse.accounting.service.SocksService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/socks")
public class Controller {
    private final SocksService socksService;

    public Controller(SocksService socksService) {
        this.socksService = socksService;
    }
    @PostMapping("/income")
    public SocksDto incomeOfSocks(@RequestBody SocksDto socksDto) {
        return socksService.incomeOfSocks(socksDto);
    }

    @PostMapping("/outcome")
    public SocksDto outcomeOfSocks(@RequestBody SocksDto socksDto) {
        return socksService.outcomeOfSocks(socksDto);
    }

    @GetMapping()
    public Optional<Integer> getSocksAmount(@RequestParam String color,
                                            @RequestParam Operation operation,
                                            @RequestParam int cottonPart){
        return socksService.getSocksAmount(color, operation, cottonPart);

    }

    @GetMapping("/all")
    public List<Socks> getAll(){
        return socksService.getAll();
    }
}
