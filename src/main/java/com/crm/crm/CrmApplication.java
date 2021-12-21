package com.crm.crm;

import com.crm.crm.DbHandler.DbHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class CrmApplication {

	public CrmApplication() throws SQLException {
	}

	public static void main(String[] args) {
		SpringApplication.run(CrmApplication.class, args);
	}
	// Создаем экземпляр по работе с БД
	DbHandler dbHandler = DbHandler.getInstance();
}
