package com.ntt.qa.store.pages;

import com.ntt.qa.store.hooks.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductPage {

    private final WebDriver driver;

    // Precio en la página del producto
    private final By unitPriceSpan = By.cssSelector("span[itemprop='price']");

    // Cantidad y botón agregar al carrito
    private final By quantityInput = By.id("quantity_wanted");
    private final By addToCartButton = By.cssSelector("button.add-to-cart, button[data-button-action='add-to-cart']");

    // Popup (modal) de confirmación
    private final By cartModal = By.id("blockcart-modal");
    private final By cartModalTitle = By.cssSelector("#blockcart-modal h4, #blockcart-modal .modal-title");
    private final By cartModalTotal = By.cssSelector("#blockcart-modal .cart-content .cart-total .value, #blockcart-modal .cart-content .product-total .value");

    // Botón para ir al carrito desde el popup
    private final By proceedToCheckoutButton = By.cssSelector("#blockcart-modal a.btn-primary, #blockcart-modal a[href*='order']");

    public ProductPage() {
        this.driver = Hooks.getDriver();
    }

    public double getUnitPrice() {
        WebElement priceElement = Hooks.getWait().until(
                ExpectedConditions.visibilityOfElementLocated(unitPriceSpan)
        );
        String priceText = priceElement.getText(); // Ej: "PEN19.12"
        return parsePrice(priceText);
    }

    public void setQuantity(int quantity) {
        Hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(quantityInput));
        WebElement qty = driver.findElement(quantityInput);
        qty.clear();
        qty.sendKeys(String.valueOf(quantity));
    }

    public void addToCart() {
        Hooks.getWait().until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
    }

    public boolean isConfirmationPopupVisible() {
        try {
            Hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(cartModal));
            Hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(cartModalTitle));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public double getTotalFromPopup() {
        WebElement totalElement = Hooks.getWait().until(
                ExpectedConditions.visibilityOfElementLocated(cartModalTotal)
        );
        String totalText = totalElement.getText();
        return parsePrice(totalText);
    }

    public void goToCartFromPopup() {
        Hooks.getWait().until(ExpectedConditions.elementToBeClickable(proceedToCheckoutButton)).click();
    }

    private double parsePrice(String text) {
        // Quita letras y espacios, deja solo números, puntos y comas
        String cleaned = text.replaceAll("[^0-9.,]", "").replace(",", ".");
        if (cleaned.isEmpty()) {
            throw new RuntimeException("No se pudo parsear el precio desde el texto: " + text);
        }
        return Double.parseDouble(cleaned);
    }
}
