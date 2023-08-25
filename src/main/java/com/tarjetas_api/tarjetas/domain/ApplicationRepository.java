package com.tarjetas_api.tarjetas.domain;

public interface ApplicationRepository {

    String getTarjetas();

    String getCompras();

    String getBancos();

    String getPersonas();

    void ingresarCompra(Compra compra);

    void modificarCompra(Compra compra);

    void eliminarCompra(Compra compra);

    String getTiendas();

    void ingresarTienda(Tienda tienda);

    void modificarTienda(Tienda tienda);
}
