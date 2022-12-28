package com.tarjetas.tarjetas.domain;

import java.util.List;

public interface ITiendas {
    List<Tienda> getTiendas();
    void ingresarTienda(Tienda tienda);
    void modificarTienda(Tienda tienda);
}
