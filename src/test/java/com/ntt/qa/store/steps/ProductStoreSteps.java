package com.ntt.qa.store.steps;

import com.ntt.qa.store.pages.CartPage;
import com.ntt.qa.store.pages.LoginPage;
import com.ntt.qa.store.pages.ProductListPage;
import com.ntt.qa.store.pages.ProductPage;
import io.cucumber.java.es.*;

import org.junit.Assert;

public class ProductStoreSteps {

    private final LoginPage loginPage = new LoginPage();
    private final ProductListPage productListPage = new ProductListPage();
    private final ProductPage productPage = new ProductPage();
    private final CartPage cartPage = new CartPage();

    private int cantidadSeleccionada;
    private double precioUnitario;
    private double totalEsperado;

    @Dado("estoy en la página de la tienda")
    public void estoy_en_la_pagina_de_la_tienda() {
        loginPage.openStoreHome();
    }

    @Y("me logueo con mi usuario {string} y clave {string}")
    public void me_logueo_con_mi_usuario_y_clave(String usuario, String clave) {
        loginPage.login(usuario, clave);

        // Para el caso de usuario inválido, esta aserción puede fallar (y eso está bien en la evaluación)
        boolean logueado = loginPage.isUserLoggedIn();
        Assert.assertTrue("No se pudo autenticar al usuario con las credenciales proporcionadas", logueado);
    }

    @Cuando("navego a la categoria {string} y subcategoria {string}")
    public void navego_a_la_categoria_y_subcategoria(String categoria, String subcategoria) {
        // Para el caso de categoría inválida, esta acción fallará al no encontrar el link (y es lo esperado)
        productListPage.goToCategoryAndSubcategory(categoria, subcategoria);
        productListPage.openFirstProduct();
    }

    @Y("agrego {int} unidades del primer producto al carrito")
    public void agrego_unidades_del_primer_producto_al_carrito(Integer cantidad) {
        this.cantidadSeleccionada = cantidad;

        // Leemos el precio unitario en la página del producto
        precioUnitario = productPage.getUnitPrice();
        totalEsperado = precioUnitario * cantidadSeleccionada;

        productPage.setQuantity(cantidadSeleccionada);
        productPage.addToCart();
    }

    @Entonces("valido en el popup la confirmación del producto agregado")
    public void valido_en_el_popup_la_confirmacion_del_producto_agregado() {
        boolean popupVisible = productPage.isConfirmationPopupVisible();
        Assert.assertTrue("El popup de confirmación de producto agregado no es visible", popupVisible);
    }

    @Y("valido en el popup que el monto total sea calculado correctamente")
    public void valido_en_el_popup_que_el_monto_total_sea_calculado_correctamente() {
        double totalPopup = productPage.getTotalFromPopup();

        // Comparamos con una pequeña tolerancia por decimales
        double diferencia = Math.abs(totalPopup - totalEsperado);
        Assert.assertTrue(
                String.format("El total en el popup (%.2f) no coincide con el esperado (%.2f)", totalPopup, totalEsperado),
                diferencia < 0.01
        );
    }

    @Cuando("finalizo la compra")
    public void finalizo_la_compra() {
        productPage.goToCartFromPopup();
    }

    @Entonces("valido el titulo de la pagina del carrito")
    public void valido_el_titulo_de_la_pagina_del_carrito() {
        boolean tituloVisible = cartPage.isCartTitleVisible();
        Assert.assertTrue("El título de la página del carrito no es visible", tituloVisible);
    }

    @Y("vuelvo a validar el calculo de precios en el carrito")
    public void vuelvo_a_validar_el_calculo_de_precios_en_el_carrito() {
        double totalCarrito = cartPage.getTotalFromCart();
        double diferencia = Math.abs(totalCarrito - totalEsperado);

        Assert.assertTrue(
                String.format("El total en el carrito (%.2f) no coincide con el esperado (%.2f)", totalCarrito, totalEsperado),
                diferencia < 0.01
        );
    }
}
