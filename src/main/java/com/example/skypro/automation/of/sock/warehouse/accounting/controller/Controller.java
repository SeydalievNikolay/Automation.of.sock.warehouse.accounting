package com.example.skypro.automation.of.sock.warehouse.accounting.controller;

import com.example.skypro.automation.of.sock.warehouse.accounting.model.Socks;
import com.example.skypro.automation.of.sock.warehouse.accounting.service.Operation;
import com.example.skypro.automation.of.sock.warehouse.accounting.service.SocksService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/socks")
@RequiredArgsConstructor
public class Controller {

    @Autowired
    private final SocksService socksService;

    @PostMapping("/income")
    public ResponseEntity<?> incomeOfSocks(@RequestBody Socks socks) {
        return socksService.incomeOfSocks(socks);
    }

    @PostMapping("/outcome")
    public ResponseEntity<?> outcomeOfSocks(@RequestBody Socks socks) {
        return socksService.outcomeOfSocks(socks);
    }

    @GetMapping("/")
    public ResponseEntity<?> getSocksAmount(@RequestParam String color,
                                                  @RequestParam Operation operation,
                                                  @RequestParam int cottonPart){
        Optional<Integer> result =  socksService.getSocksAmount(color, operation, cottonPart);
        return new ResponseEntity<>(result.orElse(0), HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<Socks> getAll(){
        return socksService.getAll();
    }
}
