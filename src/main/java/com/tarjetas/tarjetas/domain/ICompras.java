package com.tarjetas.tarjetas.domain;

import java.sql.SQLException;
import java.util.List;

public interface ICompras {
    List<Compra> getCompras(int eliminadas) throws SQLException;
    void ingresarCompra(Compra compra) throws SQLException;
    void modificarCompra(Compra compra) throws SQLException;
    void eliminarCompra(int compraId) throws SQLException;
}
