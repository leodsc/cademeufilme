package com.cademeufilme.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

/**
 * @author - Leonardo Carvalho
 * @since 1.0.0
 */

public class Movies extends Jdbc {

    private final Logger logger = LogManager.getLogger("Movies table");

    public void insertMovies(List<String> titles, String streaming) throws SQLException {
        Connection conn = connect();
        Statement stmt = conn.createStatement();
        int totalMovies = 0;
        for (String title: titles) {
            title = title.replace("'", "");
            String query = String.format("""
                    INSERT INTO movies(title, streaming) VALUES('%s', '%s')
                    """, title, streaming);
            totalMovies += stmt.executeUpdate(query);
            logger.info("Inserting movie -> " + title);
        }
        logger.info(totalMovies + " movies added");
        conn.close();
    }
}
