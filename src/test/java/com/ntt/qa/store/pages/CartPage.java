package com.ntt.qa.store.pages;

import com.ntt.qa.store.hooks.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage {

    private final WebDriver driver;

    // Título del carrito (ej. "Shopping Cart")
    private final By cartTitle = By.cssSelector("h1, h1.page-title");

    // Selectores aproximados típicos de PrestaShop 1.7 para carrito
    private final By cartUnitPrice = By.cssSelector(".cart-item .product-price, .cart-summary-line .product-price, .cart-item .current-price span");
    private final By cartTotalPrice = By.cssSelector(".cart-item .product-total .value, .cart-summary-line .cart-total .value, .cart-summary-line .value");

    public CartPage() {
        this.driver = Hooks.getDriver();
    }

    public boolean isCartTitleVisible() {
        try {
            Hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(cartTitle));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public double getUnitPriceFromCart() {
        WebElement priceElement = Hooks.getWait().until(
                ExpectedConditions.visibilityOfElementLocated(cartUnitPrice)
        );
        String priceText = priceElement.getText();
        return parsePrice(priceText);
    }

    public double getTotalFromCart() {
        WebElement totalElement = Hooks.getWait().until(
                ExpectedConditions.visibilityOfElementLocated(cartTotalPrice)
        );
        String totalText = totalElement.getText();
        return parsePrice(totalText);
    }

    private double parsePrice(String text) {
        String cleaned = text.replaceAll("[^0-9.,]", "").replace(",", ".");
        if (cleaned.isEmpty()) {
            throw new RuntimeException("No se pudo parsear el precio desde el texto del carrito: " + text);
        }
        return Double.parseDouble(cleaned);
    }
}
