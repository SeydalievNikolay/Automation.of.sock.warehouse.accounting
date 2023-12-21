package com.example.skypro.automation.of.sock.warehouse.accounting.controller;

import com.example.skypro.automation.of.sock.warehouse.accounting.dto.SocksDto;
import com.example.skypro.automation.of.sock.warehouse.accounting.model.Socks;
import com.example.skypro.automation.of.sock.warehouse.accounting.repository.SocksRepository;
import com.example.skypro.automation.of.sock.warehouse.accounting.service.Operation;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class ControllerIT {
    @Autowired
    private DataSource dataSource;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private SocksRepository socksRepository;
    @Autowired
    private Constants constants;
    @Container
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:latest")
                    .withUsername("postgres")
                    .withPassword("postgres");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void testPostgresql() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }

    @AfterEach
    void cleanDB() {
        socksRepository.deleteAll();
    }

    @Test
    public void incomeOfSocksToDb_isOk() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("color", "green");
        jsonObject.put("cottonPart", 90);
        jsonObject.put("quantity", 3);
        mockMvc.perform(post("/api/socks/income")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andExpectAll(status().isOk(),
                        jsonPath("$.color").value("green"),
                        jsonPath("$.cottonPart").value(90),
                        jsonPath("$.quantity").value(3));

    }

    @Test
    public void outcomeOfSocks_isOk() throws Exception {
        Socks socks = constants.addDB();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("color", socks.getColor());
        jsonObject.put("cottonPart", socks.getCottonPart());
        jsonObject.put("quantity", socks.getQuantity());
        mockMvc.perform(post("/api/socks/outcome")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andExpectAll(status().isOk(),
                        jsonPath("$.color").value(socks.getColor()),
                        jsonPath("$.cottonPart").value(socks.getCottonPart()),
                        jsonPath("$.quantity").value(0));

    }

    @Test
    void getSocksAmount_isOk() throws Exception {
        Socks socks = constants.addDB();
        Operation operation = Operation.EQUAL;

        mockMvc.perform(get("/api/socks?color=green&operation=equal&cottonPart=90")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(),
                        jsonPath("$.color").value(socks.getColor()),
                        jsonPath("$.cottonPart").value(socks.getCottonPart()));
    }
}
