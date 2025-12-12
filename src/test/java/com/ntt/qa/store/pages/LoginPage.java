package com.ntt.qa.store.pages;

import com.ntt.qa.store.hooks.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage {

    private final WebDriver driver;

    private final String baseUrl = "https://qalab.bensg.com/store";

    // Localizadores
    private final By signInLink = By.linkText("Sign in");
    private final By emailInput = By.name("email");       // campo correo
    private final By passwordInput = By.name("password"); // campo clave
    private final By submitLoginButton = By.id("submit-login");

    // Elemento que indica que el usuario ya está logueado (ej: "Sign out")
    private final By signOutLink = By.linkText("Sign out");

    public LoginPage() {
        this.driver = Hooks.getDriver();
    }

    public void openStoreHome() {
        driver.get(baseUrl);
    }

    public void clickSignIn() {
        Hooks.getWait().until(ExpectedConditions.elementToBeClickable(signInLink)).click();
    }

    public void login(String user, String password) {
        // Abre modal de login
        clickSignIn();

        // Espera a que estén visibles los campos
        Hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(user);

        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);

        driver.findElement(submitLoginButton).click();
    }

    public boolean isUserLoggedIn() {
        try {
            Hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(signOutLink));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
