package com.example.skypro.automation.of.sock.warehouse.accounting.repository;

import com.example.skypro.automation.of.sock.warehouse.accounting.model.Socks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SocksRepository extends JpaRepository<Socks, Long> {
    Optional<Socks> findSocksByColorAndCottonPart(String color, Integer cottonPart);
    @Query("SELECT SUM (quantity) FROM socks_balance WHERE color = #{color} AND cotton_part > #{cottonPart}")
    Optional<Integer> findQuantityByParamsMoreThan(@Param("color") String color, @Param("cottonPart") int cottonPart);

    @Query("SELECT SUM (quantity) FROM socks_balance WHERE color = #{color} AND cotton_part < #{cottonPart}")
    Optional<Integer> findQuantityByParamsLessThan(@Param("color") String color, @Param("cottonPart") int cottonPart);

    @Query("SELECT SUM (quantity) FROM socks_balance WHERE color = #{color} AND cotton_part = #{cottonPart}")
    Optional<Integer> findQuantityByParamsEqual(@Param("color") String color, @Param("cottonPart") int cottonPart);
}
