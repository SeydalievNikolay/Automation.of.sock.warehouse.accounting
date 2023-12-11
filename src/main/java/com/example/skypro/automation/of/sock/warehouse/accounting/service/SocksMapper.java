package com.example.skypro.automation.of.sock.warehouse.accounting.service;

import com.example.skypro.automation.of.sock.warehouse.accounting.dto.SocksDto;
import com.example.skypro.automation.of.sock.warehouse.accounting.model.Socks;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SocksMapper {
    SocksDto toSocksDto (Socks socks);
    Socks toSocksEntity (SocksDto socksDto);
}
