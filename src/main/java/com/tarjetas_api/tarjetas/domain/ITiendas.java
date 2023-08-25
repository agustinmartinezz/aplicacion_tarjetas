package com.tarjetas_api.tarjetas.domain;

import java.sql.SQLException;
import java.util.List;

public interface ITiendas {
    List<Tienda> getTiendas() throws SQLException;
    Tienda getTienda(int tiendaId) throws SQLException;
    void ingresarTienda(Tienda tienda) throws SQLException;
    void modificarTienda(Tienda tienda) throws SQLException;
}
