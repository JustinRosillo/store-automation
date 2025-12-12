package com.ntt.qa.store.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

// Esta clase es la que ejecuta todos los escenarios de Cucumber
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",   // Ruta donde están los .feature
        glue = "com.ntt.qa.store",                  // Paquete donde están los steps y hooks
        plugin = {
                "pretty",                           // Muestra los pasos de forma más ordenada en consola
                "html:target/cucumber-report.html"  // Genera el reporte en HTML dentro de /target
        },
        monochrome = true                           // Hace más limpia la salida en consola
)
public class RunCucumberTest {
    // No va código aquí. JUnit se encarga de ejecutar todo
}
