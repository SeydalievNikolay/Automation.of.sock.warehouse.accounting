package com.example.skypro.automation.of.sock.warehouse.accounting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocksDto {
    private String color;
    @Min(0)
    @Max(100)
    private int cottonPart;
    @Min(1)
    private int quantity;
}
