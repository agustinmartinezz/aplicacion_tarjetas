package com.tarjetas.tarjetas.infrastructure;

import com.tarjetas.tarjetas.domain.Compra;
import com.tarjetas.tarjetas.domain.ICompras;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/compras")
public class ComprasAPI implements ICompras {
    @Autowired
    DataSource dataSource;

    @GetMapping()
    public List<Compra> getCompras(@RequestParam("eliminadas") int eliminadas) throws SQLException {
        List<Compra> compras = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM COMPRAS WHERE CompraEliminada = " + eliminadas);

        while (rs.next()) {
            Compra compraAux = new Compra();

            compraAux.setCompraId(Integer.parseInt(rs.getString("CompraId")));
            compraAux.setCompraDescripcion(rs.getString("CompraDescripcion"));
            compraAux.setCompraMonto(Double.parseDouble(rs.getString("CompraMonto")));
            compraAux.setCompraFecha(LocalDate.parse(rs.getString("CompraFecha"), formatter));
            compraAux.setCompraCuotas(Integer.parseInt(rs.getString("CompraCuotas")));
            compraAux.setTiendaId(Integer.parseInt(rs.getString("TiendaId")));
            compraAux.setTarjetaId(Integer.parseInt(rs.getString("TarjetaId")));

            compras.add(compraAux);
        }

        con.close();
        stmt.close();
        rs.close();
        return compras;
    }

    @PostMapping
    public void ingresarCompra(@RequestBody Compra compra) throws SQLException {
        Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement();

        String compraFecha = compra.getCompraFecha().getYear() + "-" + compra.getCompraFecha().getMonthValue() + "-" + compra.getCompraFecha().getDayOfMonth();

        String query = "INSERT INTO COMPRAS (CompraDescripcion, CompraMonto, CompraFecha, CompraCUotas, TiendaId, TarjetaId) VALUES (\"" + compra.getCompraDescripcion()+"\","+ compra.getCompraMonto()+ ",'" + compraFecha +"'," + compra.getCompraCuotas() + "," + compra.getTiendaId() + "," + compra.getTarjetaId()+");";

        stmt.execute(query);
        con.close();
        stmt.close();
    }

    @PutMapping
    public void modificarCompra(@RequestBody Compra compra) throws SQLException {
        Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement();

        String compraFecha = compra.getCompraFecha().getYear() + "-" + compra.getCompraFecha().getMonthValue() + "-" + compra.getCompraFecha().getDayOfMonth();

        String query = "UPDATE COMPRAS SET CompraDescripcion = \"" + compra.getCompraDescripcion() + "\", CompraMonto = " + compra.getCompraMonto() + ", CompraFecha = '" + compraFecha + "', CompraCuotas = " + compra.getCompraCuotas() + ", TiendaId = " + compra.getTiendaId() + ", TarjetaId = " + compra.getTarjetaId() + " WHERE CompraId = " + compra.getCompraId() + ";";

        stmt.execute(query);

        con.close();
        stmt.close();
    }

    @DeleteMapping()
    public void eliminarCompra(@RequestParam("compraId") int compraId) throws SQLException {
        Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement();

        String query = "UPDATE COMPRAS SET CompraEliminada = 1 WHERE CompraId = " + compraId;

        stmt.execute(query);

        con.close();
        stmt.close();
    }
}
