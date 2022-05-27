package com.cademeufilme.scraper;

import com.cademeufilme.database.Movies;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * @author - Leonardo Carvalho
 */
public interface Scraper {
    Movies moviesTable = new Movies();

    /**
     * Start the scraper
     * @param url url to start scraping
     * @throws InterruptedException
     * @throws SQLException
     */
    void start(String url) throws InterruptedException, SQLException;
    void login() throws InterruptedException, SQLException;
}
