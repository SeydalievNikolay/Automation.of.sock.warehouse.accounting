package com.example.skypro.automation.of.sock.warehouse.accounting.repository;

import com.example.skypro.automation.of.sock.warehouse.accounting.model.Socks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SocksRepository extends JpaRepository<Socks, Long> {
    @Query(value = "SELECT id, color, cotton_part, quantity FROM Socks WHERE color =:color and cotton_part =:cottonPart LIMIT 1", nativeQuery = true)
    Optional<Socks> findSocksByColorAndCottonPart(@Param("color") String color, @Param("cottonPart") int cottonPart);
    @Query(value = "SELECT SUM(u.quantity) FROM Socks u WHERE u.color = :color and u.cotton_part > :cottonPart", nativeQuery = true)
    Optional<Integer> findQuantityByParamsMoreThan(@Param("color") String color, @Param("cottonPart") int cottonPart);

    @Query(value = "SELECT SUM(u.quantity) FROM Socks u WHERE u.color = :color and u.cotton_part < :cottonPart", nativeQuery = true)
    Optional<Integer> findQuantityByParamsLessThan(@Param("color") String color, @Param("cottonPart") int cottonPart);

    @Query(value = "SELECT SUM(u.quantity) FROM Socks u WHERE u.color = :color and u.cotton_part = :cottonPart", nativeQuery = true)
    Optional<Integer> findQuantityByParamsEqual(@Param("color") String color, @Param("cottonPart") int cottonPart);

}
