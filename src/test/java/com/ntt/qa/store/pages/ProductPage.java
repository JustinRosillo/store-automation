package com.ntt.qa.store.pages;

import com.ntt.qa.store.hooks.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductPage {

    private final WebDriver driver;

    // Selector del precio que aparece en la página del producto
    private final By unitPriceSpan = By.cssSelector("span[itemprop='price']");

    // Selectores para cambiar la cantidad y agregar el producto al carrito
    private final By quantityInput = By.id("quantity_wanted");
    private final By addToCartButton = By.cssSelector("button.add-to-cart, button[data-button-action='add-to-cart']");

    // Selectores del popup que aparece después de agregar el producto
    private final By cartModal = By.id("blockcart-modal");
    private final By cartModalTitle = By.cssSelector("#blockcart-modal h4, #blockcart-modal .modal-title");
    private final By cartModalTotal = By.cssSelector("#blockcart-modal .cart-content .cart-total .value, #blockcart-modal .cart-content .product-total .value");

    // Botón para ir al carrito desde el popup
    private final By proceedToCheckoutButton = By.cssSelector("#blockcart-modal a.btn-primary, #blockcart-modal a[href*='order']");

    public ProductPage() {
        this.driver = Hooks.getDriver();
    }

    public double getUnitPrice() {
        // Obtengo el precio del producto desde la página
        WebElement priceElement = Hooks.getWait().until(
                ExpectedConditions.visibilityOfElementLocated(unitPriceSpan)
        );
        String priceText = priceElement.getText();
        return parsePrice(priceText);
    }

    public void setQuantity(int quantity) {
        // Cambio la cantidad del producto antes de agregarlo al carrito
        Hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(quantityInput));
        WebElement qty = driver.findElement(quantityInput);
        qty.clear();
        qty.sendKeys(String.valueOf(quantity));
    }

    public void addToCart() {
        // Hago clic en el botón para agregar el producto al carrito
        Hooks.getWait().until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
    }

    public boolean isConfirmationPopupVisible() {
        // Verifico si salió el popup de confirmación después de agregar el producto
        try {
            Hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(cartModal));
            Hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(cartModalTitle));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public double getTotalFromPopup() {
        // Leo el precio total que aparece en el popup
        WebElement totalElement = Hooks.getWait().until(
                ExpectedConditions.visibilityOfElementLocated(cartModalTotal)
        );
        String totalText = totalElement.getText();
        return parsePrice(totalText);
    }

    public void goToCartFromPopup() {
        // Voy al carrito desde el popup
        Hooks.getWait().until(ExpectedConditions.elementToBeClickable(proceedToCheckoutButton)).click();
    }

    // Convierte el texto del precio a número (quitando símbolos como PEN, $, etc.)
    private double parsePrice(String text) {
        String cleaned = text.replaceAll("[^0-9.,]", "").replace(",", ".");
        if (cleaned.isEmpty()) {
            throw new RuntimeException("No se pudo obtener el precio del texto: " + text);
        }
        return Double.parseDouble(cleaned);
    }
}
