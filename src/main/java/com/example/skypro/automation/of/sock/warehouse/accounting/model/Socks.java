package com.example.skypro.automation.of.sock.warehouse.accounting.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@Table(name = "socks")
public class Socks {
    private long id;
    private String color;
    private Integer cottonPart;
    private Integer quantity;


    public Socks(String color, Integer cottonPart, Integer quantity) {
        this.color = color;
        this.cottonPart = cottonPart;
        this.quantity = quantity;
    }
}
