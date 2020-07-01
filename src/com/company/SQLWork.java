package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLWork {
    // data from MySQL
    private static final String url = "jdbc:mysql://localhost:3306/parser";
    private static final String user = "root";
    private static final String password = "Zaharin0479";

    // connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static void main(String[] args) {
        String query = "INSERT INTO parser.new_table (name , description , href , users , tags) \n"+
                "VALUES ('Neko F63' ,'☆ Привет, котда можно найти компанию для общени' , 'https://discord-server.com528509008022208513' ,'3544 Онлайн подписчиков: 11936 Подписчиков всего 56 Лайков' ,'[game, anime, аниме, друзья, ивенты, See Popular Tags]')";

        try {
            // открываем соединение с БД
            con = DriverManager.getConnection(url, user, password);

            // получение оператора для выполнения запроса
            stmt = con.createStatement();

            // Выполнение запроса
            stmt.executeUpdate(query);

            //while (rs.next()) {
            //    int count = rs.getInt(1);
            //    System.out.println("Total number of books in the table : " + count);
            //}

        } catch (SQLException sqlEx) { // обработка ошибок|исключений
            System.out.println("Произошла ошибка во время подключения к БД");
            sqlEx.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                rs.close();
            } catch (SQLException se) { /*can't do anything */ }
        }
    }
}


