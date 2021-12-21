package com.crm.crm.DbHandler;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbHandler {

    // Константа, в которой хранится адрес подключения
    private static final String CON_STR = "jdbc:" +
            "postgresql://ec2-44-198-80-194.compute-1.amazonaws.com:5432/dftr8b30c1u80a?user=rolwwrvvzjueeb&password=33d37c97eb2dde3f373e52bc00126a55dfc2dc6b8cce1ad7af1611d73e5d5e68";


    // экземпляров класса DbHandler
    private static DbHandler instance = null;
    // Объект, в котором будет храниться соединение с БД
    private Connection connection;

    private DbHandler() throws SQLException {
        // Регистрируем драйвер, с которым будем работать
        // в нашем случае Sqlite
        DriverManager.registerDriver(new Driver());
        // Выполняем подключение к базе данных
        this.connection = DriverManager.getConnection(CON_STR);

    }

    public static synchronized DbHandler getInstance() throws SQLException {
        if (instance == null)
            instance = new DbHandler();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}