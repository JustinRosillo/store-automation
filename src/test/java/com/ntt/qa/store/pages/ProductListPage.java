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
        // Busco y doy click en la categoría (por ejemplo: Clothes)
        By categoryLink = By.linkText(categoria);
        Hooks.getWait().until(ExpectedConditions.elementToBeClickable(categoryLink)).click();

        // Luego entro a la subcategoría (por ejemplo: Men)
        By subCategoryLink = By.linkText(subcategoria);
        Hooks.getWait().until(ExpectedConditions.elementToBeClickable(subCategoryLink)).click();
    }

    public void openFirstProduct() {
        // Aquí obtengo todos los productos que aparecen en la lista
        By productTitles = By.cssSelector(".product-title a");

        Hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(productTitles));
        List<WebElement> products = driver.findElements(productTitles);

        // Si no hay productos, algo salió mal y no debería continuar
        if (products.isEmpty()) {
            throw new RuntimeException("No se encontraron productos en la lista");
        }

        // Abro el primer producto encontrado
        products.get(0).click();
    }
}
