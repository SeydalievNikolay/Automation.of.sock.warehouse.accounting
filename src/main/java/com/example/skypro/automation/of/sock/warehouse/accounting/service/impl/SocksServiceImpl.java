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
            socks = socksRepository.findSocksByColorAndCottonPart(socksDto.getColor().toLowerCase(),
                    socksDto.getCottonPart()).orElseThrow();
            socks.setQuantity(socks.getQuantity() + socksDto.getQuantity());
        } else {
            socks = socksMapper.toSocksEntity(socksDto);
        }
        socksRepository.save(socks);
        return socksMapper.toSocksDto(socks);
    }

    @Override
    public SocksDto outcomeOfSocks(SocksDto socksDto) {
        Socks socks = socksRepository.
                findSocksByColorAndCottonPart(socksDto.getColor().
                        toLowerCase(), socksDto.getCottonPart()).get();
        if (socksRepository.findSocksByColorAndCottonPart(socksDto.getColor().
                toLowerCase(), socksDto.getCottonPart()).isEmpty()) {
            throw new ParametersNotFoundException("Not found position");
        } else {
            socks.setQuantity(socks.getQuantity() - socksDto.getQuantity());
            socksRepository.save(socks);
        }
        return socksMapper.toSocksDto(socks);
    }

    @Override
    public Optional<Integer> getSocksAmount(String color, Operation operation, int cottonPart) {
        return switch (operation) {
            case MORE_THAN -> socksRepository.findQuantityByParamsMoreThan(color, cottonPart);
            case LESS_THAN -> socksRepository.findQuantityByParamsLessThan(color, cottonPart);
            case EQUAL -> socksRepository.findQuantityByParamsEqual(color, cottonPart);
        };
    }

    @Override
    public List<Socks> getAll() {
        return socksRepository.findAll();
    }
}
