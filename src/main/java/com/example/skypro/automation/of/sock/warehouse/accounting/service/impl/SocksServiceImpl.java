package com.example.skypro.automation.of.sock.warehouse.accounting.service.impl;

import com.example.skypro.automation.of.sock.warehouse.accounting.dto.SocksDto;
import com.example.skypro.automation.of.sock.warehouse.accounting.exceptions.ParametersNotFoundException;
import com.example.skypro.automation.of.sock.warehouse.accounting.model.Socks;
import com.example.skypro.automation.of.sock.warehouse.accounting.repository.SocksRepository;
import com.example.skypro.automation.of.sock.warehouse.accounting.service.Operation;
import com.example.skypro.automation.of.sock.warehouse.accounting.mapper.SocksMapper;
import com.example.skypro.automation.of.sock.warehouse.accounting.service.SocksService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SocksServiceImpl implements SocksService {
    private final SocksRepository socksRepository;
    private final SocksMapper socksMapper;

    public SocksServiceImpl(SocksRepository socksRepository, SocksMapper socksMapper) {
        this.socksRepository = socksRepository;
        this.socksMapper = socksMapper;
    }

    @Override
    public SocksDto incomeOfSocks(SocksDto socksDto) {
        Optional<Socks> optional = socksRepository.
                findSocksByColorAndCottonPart(socksDto.getColor().toLowerCase(),
                        socksDto.getCottonPart());

        Socks socks;
        if (optional.isPresent()) {
            socks = optional.get();
            socks.setQuantity(socks.getQuantity() + socksDto.getQuantity());
        } else {
            socks = socksMapper.toSocksEntity(socksDto);
        }
        socksRepository.save(socks);
        return socksMapper.toSocksDto(socks);
    }

    @Override
    public void outcomeOfSocks(SocksDto socksDto) {
        socksRepository.findSocksByColorAndCottonPart(socksDto.getColor(),
                socksDto.getCottonPart()).ifPresentOrElse
                (sock -> deleteCertainQuantityOfSocks(sock, socksDto.getQuantity()),
                        () -> {
                            throw new ParametersNotFoundException("Not found socks with such parameters");
                        }
                );
    }

    @Override
    public Integer getSocksAmount(String color, Operation operation, int cottonPart) {
        List<Socks> socksList = socksRepository.findSocksByColor(color);
        int result = 0;
        List<Integer> quantity = switch (operation) {
            case MORE_THAN -> socksList.stream()
                    .filter(socks -> socks.getCottonPart() > cottonPart)
                    .map(Socks::getQuantity)
                    .collect(Collectors.toList());
            case LESS_THAN -> socksList.stream()
                    .filter(socks -> socks.getCottonPart() < cottonPart)
                    .map(Socks::getQuantity)
                    .collect(Collectors.toList());
            case EQUAL -> socksList.stream()
                    .filter(socks -> socks.getCottonPart() == cottonPart)
                    .map(Socks::getQuantity)
                    .collect(Collectors.toList());
        };

        for (Integer a : quantity)
            result += a;
        return result;
    }

    @Override
    public void deleteCertainQuantityOfSocks(Socks socks, int quantity) {
        Integer quantityValueFromDB = socks.getQuantity();
        if (quantityValueFromDB < quantity) {
            throw new ParametersNotFoundException("There is no such amount of socks in stock");
        }
        if (quantityValueFromDB.equals(quantity)) {
            socksRepository.delete(socks);
        } else {
            socks.setQuantity(quantityValueFromDB - quantity);
            socksRepository.save(socks);
        }
    }
}
