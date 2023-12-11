package com.example.skypro.automation.of.sock.warehouse.accounting.repository;

import com.example.skypro.automation.of.sock.warehouse.accounting.model.Socks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SocksRepository extends JpaRepository<Socks, Long> {
    Optional<Socks> findSocksByColorAndCottonPart(String color, int cottonPart);
    @Query("SELECT SUM(u.quantity) FROM Socks u WHERE u.color = :color and u.cottonPart > :cottonPart")
    Optional<Integer> findQuantityByParamsMoreThan(@Param("color") String color, @Param("cottonPart") int cottonPart);

    @Query("SELECT SUM(u.quantity) FROM Socks u WHERE u.color = :color and u.cottonPart < :cottonPart")
    Optional<Integer> findQuantityByParamsLessThan(@Param("color") String color, @Param("cottonPart") int cottonPart);

    @Query("SELECT SUM(u.quantity) FROM Socks u WHERE u.color = :color and u.cottonPart = :cottonPart")
    Optional<Integer> findQuantityByParamsEqual(@Param("color") String color, @Param("cottonPart") int cottonPart);

}
