package com.cademeufilme.scraper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Map;

public class MovieScraper {

    public static void main(String[] args) throws InterruptedException {
//        Map<String, String> env = System.getenv();
//        for (String key: env.keySet()) {
//            System.out.println(key);
//        }
        ChromeDriver driver = new ChromeDriver();

        driver.get("https://www.netflix.com/br/");
        Thread.sleep(5000);
        WebElement element = driver.findElement(By.cssSelector("[href='/login']"));
        element.click();
        WebElement login = driver.findElement(By.id("id_userLoginId"));
        WebElement password = driver.findElement(By.id("id_password"));
        WebElement rememberMe = driver.findElement(By.cssSelector("[data-uia='label+rememberMe']"));
        WebElement submit = driver.findElement(By.cssSelector("[data-uia='login-submit-button']"));
        login.sendKeys("");
        password.sendKeys("");
        rememberMe.click();
        submit.click();
        Thread.sleep(2000);
        List<WebElement> profiles = driver.findElements(By.cssSelector("[class='profile']"));
        profiles.get(2).click();

        Thread.sleep(2000);
        WebElement movies = driver.findElement(By.cssSelector("[href='/browse/genre/34399']"));
        movies.click();
        Thread.sleep(4000);
        WebElement genres = driver.findElement(By.cssSelector("[label='Gêneros']"));
        genres.click();
        WebElement genre = driver.findElement(By.cssSelector("[href='/browse/genre/1365?bc=34399']"));
        genre.click();
        driver.executeScript("window.scrollTo(0, 1113)");
        Thread.sleep(2000);
        List<WebElement> moviesCtn = driver.findElements(By.className("lolomoRow"));
        WebElement movieType = moviesCtn.get(2);
        Thread.sleep(2000);
        WebElement modalLink = movieType.findElement(By.className("rowTitle"));
        modalLink.click();
        Thread.sleep(15000);

        driver.quit();
    }
}
