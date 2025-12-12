package com.ntt.qa.store.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Hooks {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @Before
    public void setUp() {
        // Configuro el driver de Chrome para no tener que descargarlo a mano
        WebDriverManager.chromedriver().setup();

        // Abro el navegador
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Creo una espera explícita que usaré en las páginas
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @After
    public void tearDown() {
        // Cierro el navegador al terminar cada escenario
        if (driver != null) {
            driver.quit();
        }
    }

    // Métodos para acceder al driver y a la espera desde otras clases
    public static WebDriver getDriver() {
        return driver;
    }

    public static WebDriverWait getWait() {
        return wait;
    }
}
