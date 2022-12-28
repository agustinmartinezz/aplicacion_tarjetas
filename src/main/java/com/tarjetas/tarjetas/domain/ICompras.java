package com.tarjetas.tarjetas.domain;

import java.sql.SQLException;
import java.util.List;

public interface ICompras {
    List<Compra> getCompras(int eliminadas) throws SQLException;
    void ingresarCompra(Compra compra);
    void modificarCompra(Compra compra);
    void eliminarCompra(int compraId);
}
