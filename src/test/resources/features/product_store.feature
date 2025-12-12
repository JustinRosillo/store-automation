Feature: Product - Store
  Como automatizador del bootcamp
  Quiero verificar el precio de un producto en la tienda
  Para asegurarme que todo funciona bien y no haya errores en la Store

  # En este Scenario Outline probamos 3 casos distintos:
  # 1) Usuario válido → debería funcionar normal
  # 2) Usuario inválido → debería fallar el login
  # 3) Categoría que no existe → debería romper al buscarla
  Scenario Outline: Validación del precio de un producto
    Dado estoy en la página de la tienda
    Y me logueo con mi usuario "<usuario>" y clave "<clave>"
    Cuando navego a la categoria "<categoria>" y subcategoria "<subcategoria>"
    Y agrego <cantidad> unidades del primer producto al carrito
    Entonces valido en el popup la confirmación del producto agregado
    Y valido en el popup que el monto total sea calculado correctamente
    Cuando finalizo la compra
    Entonces valido el titulo de la pagina del carrito
    Y vuelvo a validar el calculo de precios en el carrito

    Examples:
      | descripcion         | usuario                       | clave        | categoria | subcategoria | cantidad |
      | usuario_valido      | jxavy1597@gmail.com           | mystore123   | Clothes   | Men          | 2        |
      | usuario_invalido    | justinrosillo123@prueba.com   | pass123      | Clothes   | Men          | 2        |
      | categoria_invalida  | jxavy1597@gmail.com           | mystore123   | Autos     | Men          | 2        |
