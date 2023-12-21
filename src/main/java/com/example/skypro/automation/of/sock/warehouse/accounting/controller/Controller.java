package com.example.skypro.automation.of.sock.warehouse.accounting.controller;

import com.example.skypro.automation.of.sock.warehouse.accounting.dto.SocksDto;
import com.example.skypro.automation.of.sock.warehouse.accounting.model.Socks;
import com.example.skypro.automation.of.sock.warehouse.accounting.service.Operation;
import com.example.skypro.automation.of.sock.warehouse.accounting.service.SocksService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/socks")
public class Controller {
    private final SocksService socksService;

    public Controller(SocksService socksService) {
        this.socksService = socksService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The request has been completed",
                    content = @Content(schema = @Schema(implementation = Socks.class))),
            @ApiResponse(responseCode = "400", description = "The request parameters are missing or have an incorrect format"),
            @ApiResponse(responseCode = "500", description = "An error occurred that is independent of the caller")})
    @PostMapping("/income")
    public SocksDto incomeOfSocks(@RequestBody SocksDto socksDto) {
        return socksService.incomeOfSocks(socksDto);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The request has been completed",
                    content = @Content(schema = @Schema(implementation = Socks.class))),
            @ApiResponse(responseCode = "400", description = "The request parameters are missing or have an incorrect format"),
            @ApiResponse(responseCode = "500", description = "An error occurred that is independent of the caller")})
    @PostMapping("/outcome")
    public void outcomeOfSocks(@RequestBody SocksDto socksDto) {
        socksService.outcomeOfSocks(socksDto);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The request has been completed",
                    content = @Content(schema = @Schema(implementation = Socks.class))),
            @ApiResponse(responseCode = "400", description = "The request parameters are missing or have an incorrect format"),
            @ApiResponse(responseCode = "500", description = "An error occurred that is independent of the caller")})
    @GetMapping("/")
    public Integer getSocksAmount(@RequestParam String color,
                                            @RequestParam Operation operation,
                                            @RequestParam int cottonPart){
        return socksService.getSocksAmount(color, operation, cottonPart);

    }
}
