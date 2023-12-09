package com.example.skypro.automation.of.sock.warehouse.accounting.service.impl;

import com.example.skypro.automation.of.sock.warehouse.accounting.model.Socks;
import com.example.skypro.automation.of.sock.warehouse.accounting.repository.SocksRepository;
import com.example.skypro.automation.of.sock.warehouse.accounting.service.Operation;
import com.example.skypro.automation.of.sock.warehouse.accounting.service.SocksService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SocksServiceImpl implements SocksService {
    @Autowired
    private SocksRepository socksRepository;

    @Override
    public ResponseEntity<?> incomeOfSocks(Socks socks) {
        if (socks.getColor() == null || socks.
                getCottonPart() == null || socks.getQuantity() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<Socks> optional = socksRepository.
                findSocksByColorAndCottonPart(socks.getColor(),
                socks.getCottonPart());

        Socks socksInDB = null;
        if (optional.isPresent()) {
            socksInDB = optional.get();
            int oldQuantity = socksInDB.getQuantity();
            socksInDB.setQuantity(oldQuantity + socks.getQuantity());
        } else {
            socksInDB = new Socks(socks.getColor(), socks.getCottonPart(),
                    socks.getQuantity());
        }
        socksRepository.save(socksInDB);

        optional = socksRepository.findById(socksInDB.getId());
        if (optional.isPresent() && Objects.equals(optional.get().getQuantity(),
                socksInDB.getQuantity()))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> outcomeOfSocks(Socks socks) {
        if (socks.getColor() == null || socks.getCottonPart() == null
                || socks.getQuantity() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<Socks> socksOptional = socksRepository
                .findSocksByColorAndCottonPart(socks.getColor(),
                        socks.getCottonPart());
        Socks socksInDB = null;
        if (socksOptional.isPresent()) {
            socksInDB = socksOptional.get();
            if (socks.getQuantity() > socksInDB.getQuantity())
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        int oldQuantity = socksInDB.getQuantity();
        socksInDB.setQuantity(oldQuantity - socks.getQuantity());
        socksRepository.save(socksInDB);

        //check that saved
        socksOptional = socksRepository.findById(socksInDB.getId());
        if (socksOptional.isPresent() && Objects.
                equals(socksOptional.get().getQuantity(), socksInDB.getQuantity()))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public Optional<Integer> getSocksAmount(String color, Operation operation, int cottonPart) {
        return switch (operation) {
            case MORE_THAN -> socksRepository.findQuantityByParamsMoreThan(color, cottonPart);
            case LESS_THAN -> socksRepository.findQuantityByParamsLessThan(color, cottonPart);
            case EQUAL -> socksRepository.findQuantityByParamsEqual(color, cottonPart);
            default -> throw new IllegalStateException("Unexpected value: " + operation);
        };
    }

    @Override
    public List<Socks> getAll() {
        return socksRepository.findAll();
    }
}
