package com.tarjetas.tarjetas.domain;

import com.tarjetas.tarjetas.dtos.CompraDTO;

import java.sql.SQLException;
import java.util.List;

public interface ICompras {
    List<Compra> getCompras(int eliminadas) throws SQLException;
    void ingresarCompra(CompraDTO compra) throws SQLException;
    void modificarCompra(CompraDTO compra) throws SQLException;
    void eliminarCompra(int compraId) throws SQLException;
}
