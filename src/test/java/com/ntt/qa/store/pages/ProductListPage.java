package com.ntt.qa.store.pages;

import com.ntt.qa.store.hooks.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ProductListPage {

    private final WebDriver driver;

    public ProductListPage() {
        this.driver = Hooks.getDriver();
    }

    public void goToCategoryAndSubcategory(String categoria, String subcategoria) {
        // Click en la categoría (ej: Clothes)
        By categoryLink = By.linkText(categoria);
        Hooks.getWait().until(ExpectedConditions.elementToBeClickable(categoryLink)).click();

        // Click en la subcategoría (ej: Men)
        By subCategoryLink = By.linkText(subcategoria);
        Hooks.getWait().until(ExpectedConditions.elementToBeClickable(subCategoryLink)).click();
    }

    public void openFirstProduct() {
        // En PrestaShop, los títulos de productos tienen class "product-title"
        By productTitles = By.cssSelector(".product-title a");

        Hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(productTitles));
        List<WebElement> products = driver.findElements(productTitles);

        if (products.isEmpty()) {
            throw new RuntimeException("No se encontraron productos en la lista");
        }

        // Abrimos el primer producto
        products.get(0).click();
    }
}
