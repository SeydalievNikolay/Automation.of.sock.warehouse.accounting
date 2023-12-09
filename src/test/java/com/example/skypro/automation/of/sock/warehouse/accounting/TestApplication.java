package com.example.skypro.automation.of.sock.warehouse.accounting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.from(Application::main).with(TestApplication.class).run(args);
	}

}
