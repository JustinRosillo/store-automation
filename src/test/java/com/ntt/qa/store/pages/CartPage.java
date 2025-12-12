package com.ntt.qa.store.pages;

import com.ntt.qa.store.hooks.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage {

    private final WebDriver driver;

    // Título que aparece en la página del carrito
    private final By cartTitle = By.cssSelector("h1, h1.page-title");

    // Selectores para obtener precios dentro del carrito
    private final By cartUnitPrice = By.cssSelector(".cart-item .product-price, .cart-summary-line .product-price, .cart-item .current-price span");
    private final By cartTotalPrice = By.cssSelector(".cart-item .product-total .value, .cart-summary-line .cart-total .value, .cart-summary-line .value");

    public CartPage() {
        this.driver = Hooks.getDriver();
    }

    public boolean isCartTitleVisible() {
        // Verifico si estoy realmente en la página del carrito
        try {
            Hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(cartTitle));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public double getUnitPriceFromCart() {
        // Obtengo el precio unitario del carrito
        WebElement priceElement = Hooks.getWait().until(
                ExpectedConditions.visibilityOfElementLocated(cartUnitPrice)
        );
        String priceText = priceElement.getText();
        return parsePrice(priceText);
    }

    public double getTotalFromCart() {
        // Obtengo el precio total mostrado en el carrito
        WebElement totalElement = Hooks.getWait().until(
                ExpectedConditions.visibilityOfElementLocated(cartTotalPrice)
        );
        String totalText = totalElement.getText();
        return parsePrice(totalText);
    }

    // Convierte el texto del precio a número (elimina PEN, $, espacios, etc.)
    private double parsePrice(String text) {
        String cleaned = text.replaceAll("[^0-9.,]", "").replace(",", ".");
        if (cleaned.isEmpty()) {
            throw new RuntimeException("No se pudo obtener el precio del texto del carrito: " + text);
        }
        return Double.parseDouble(cleaned);
    }
}
