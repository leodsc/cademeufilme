package com.cademeufilme.scraper.netflix;

import com.cademeufilme.scraper.Scraper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author - Leonardo Carvalho
 * @since 1.0.0
 * Class to scrape data from netflix
 */
public class NetflixScraper implements Scraper {

    private ChromeDriver driver;
    private final Logger logger = LogManager.getLogger("Netflix movies scrapping");
    private final String user = System.getenv("NETFLIX_LOGIN");
    private final String password = System.getenv("NETFLIX_PASSWORD");
    private final List<String> genres = new ArrayList<>();
    private final Set<String> moviesTitle = new HashSet<>();

    /**
     * Start the scraper
     * @throws InterruptedException
     * @throws SQLException
     */
    @Override
    public void start(String url) throws InterruptedException, SQLException {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get(url);
        login();
        selectProfile();
        getMoviesPage();

        driver.quit();
    }

    @Override
    public void login() throws InterruptedException {
        WebElement element = driver.findElement(By.cssSelector("[href='/login']"));
        element.click();
        WebElement loginInput = driver.findElement(By.id("id_userLoginId"));
        WebElement passwordInput = driver.findElement(By.id("id_password"));
        WebElement rememberMe = driver.findElement(By.cssSelector("[data-uia='label+rememberMe']"));
        WebElement submit = driver.findElement(By.cssSelector("[data-uia='login-submit-button']"));
        loginInput.sendKeys(user);
        passwordInput.sendKeys(password);
        rememberMe.click();
        submit.click();
    }

    private void selectProfile() throws InterruptedException {
        List<WebElement> profiles = driver.findElements(By.cssSelector("[class='profile']"));
        profiles.get(2).click();
    }

    private void getMoviesPage() throws InterruptedException, SQLException {
        String moviesItemSelector = "[href='/browse/genre/34399']";
        WebElement moviesItem = driver.findElement(By.cssSelector(moviesItemSelector));
        moviesItem.click();

        getMoviesGenres();
    }

    private void getMoviesGenres() throws InterruptedException, SQLException {
        List<WebElement> genres = getAllGenres();
        logger.info("Number of genres: " + genres.size());
        int totalOfGenres = genres.size();
        int currentGenreNumber = 0;
        while (totalOfGenres != currentGenreNumber) {
            WebElement genre = genres.get(currentGenreNumber);
            logger.info("Scrapping genre -> " + genre.getText());
            getMoviesByGenre(genre);
            driver.navigate().to("https://www.netflix.com/browse/genre/34399");
            currentGenreNumber++;
            genres = getAllGenres();
        }
    }

    private List<WebElement> getAllGenres() {
        WebElement genresButton = driver.findElement(By.cssSelector("[label='Gêneros']"));
        genresButton.click();
        WebElement genresMenu = genresButton.findElement(By.cssSelector("[role='menu'"));
        return genresMenu.findElements(By.cssSelector("ul > li > [class=sub-menu-link]"));
    }

    private void getMoviesByGenre(WebElement genre) throws InterruptedException, SQLException {
        genre.click();
        Thread.sleep(1000);
        WebElement gridView = driver.findElement(By.cssSelector("[aria-label='Visualizar como grade']"));
        gridView.click();
//        sortByAlphabeticalOrder();
        List<WebElement> moviesElems = loadMoviesByGenre();
        List<String> moviesTitles = new ArrayList<>();
        for (WebElement movie: moviesElems) {
            moviesTitles.add(movie.getText());
        }
        moviesTable.insertMovies(moviesTitles, "netflix");
    }

    private void sortByAlphabeticalOrder() throws InterruptedException {
        driver.navigate().refresh();
        WebElement sortGallery = driver.findElement(By.className("sortGallery"));
        sortGallery.click();
        WebElement suggestions = sortGallery.findElement(By.className("sub-menu"));
        WebElement suggestionsList = suggestions.findElement(By.className("sub-menu-list"));
        List<WebElement> orderingItems = suggestionsList.findElements(By.className("sub-menu-item"));
        for (WebElement item: orderingItems) {
            WebElement orderItem = item.findElement(By.className("sub-menu-link"));
            if (orderItem.getText().equals("A-Z")) {
                orderItem.click();
                break;
            }
        }
    }

    private List<WebElement> loadMoviesByGenre() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        int currentNumberOfMoviesInPage = 0;
        int updatedCurrentNumberOfMoviesInPage;
        boolean moreMoviesWereLoaded = true;
        List<WebElement> moviesElems = null;
        while (moreMoviesWereLoaded) {
            waitForMoviesToLoad();
            moviesElems = selectAllMoviesInPage();
            updatedCurrentNumberOfMoviesInPage = moviesElems.size();
            moreMoviesWereLoaded = currentNumberOfMoviesInPage < updatedCurrentNumberOfMoviesInPage;
            currentNumberOfMoviesInPage = updatedCurrentNumberOfMoviesInPage;
            logger.info("Total movies loaded: " + moviesElems.size());
            driver.executeScript(scrollToEndOfPage());
        }
        return moviesElems;
    }

    private void waitForMoviesToLoad() {
        ExpectedCondition<List<WebElement>> ec = ExpectedConditions.
                visibilityOfAllElementsLocatedBy(By.className("slider-item"));
        var wait = new WebDriverWait(driver, 5);
        wait.until(ec);
    }

    private List<WebElement> selectAllMoviesInPage() {
        return driver.findElements(By.className("slider-refocus"));
    }
    private String scrollToEndOfPage() {
        return """
                const div = document.getElementById("appMountPoint");
                window.scrollTo(0, div.scrollHeight);
                """;
    }
}
