package com.cademeufilme.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author - Leonardo Carvalho
 * @since 1.0.0
 */

public class Jdbc {
    private final Logger logger = LogManager.getLogger("JDBC Connection");;
    protected final String url = "jdbc:postgresql://localhost:5432/cademeufilme";
    protected final String user = "postgres";
    protected final String password = System.getenv("MYSQL_PASSWORD");

    protected Connection connect() {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            logger.info("Connection was successful!");
            return conn;
        } catch (Exception e) {
            logger.fatal("An error occurred and the connection couldn't be initiated.");
            logger.info(e.getMessage());
            return null;
        }
    }
}
