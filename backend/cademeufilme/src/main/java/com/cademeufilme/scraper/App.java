package com.cademeufilme.scraper;

import com.cademeufilme.scraper.netflix.NetflixScraper;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author - Leonardo Carvalho
 * @since 1.0.0
 */

public class App {

    private static final String netflixUrl = "https://netflix.com/br/";

    public static void main(String[] args) throws InterruptedException, SQLException, IOException {
        Scraper netflix = new NetflixScraper();
        netflix.start(netflixUrl);
    }
}
