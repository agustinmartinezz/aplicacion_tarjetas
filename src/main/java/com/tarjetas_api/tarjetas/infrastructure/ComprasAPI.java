package com.tarjetas_api.tarjetas.infrastructure;

import com.tarjetas_api.tarjetas.domain.*;
import com.tarjetas_api.tarjetas.dtos.CompraDTO;
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

    @CrossOrigin
    @GetMapping()
    public List<Compra> getCompras(@RequestParam("eliminadas") int eliminadas) throws SQLException {
        List<Compra> compras = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT C.CompraId, C.CompraDescripcion, C.CompraMonto, C.CompraFecha, C.CompraCuotas, C.TiendaId, T.TiendaNombre, C.TarjetaId, B.BancoNombre, TA.PersonaId, P.PersonaNombre, P.PersonaApellido\n" +
                "FROM COMPRAS AS C, TIENDAS AS T, BANCOS AS B, PERSONAS AS P, TARJETAS AS TA\n" +
                "WHERE C.TiendaId = T.TiendaId\n" +
                "AND C.TarjetaId = TA.TarjetaId\n" +
                "AND TA.BancoId = B.BancoId\n" +
                "AND TA.PersonaId = P.PersonaId AND CompraEliminada = " + eliminadas+ "\n" +
                "ORDER BY C.CompraFecha DESC");

        while (rs.next()) {
            Compra compraAux = new Compra();

            compraAux.setCompraId(Integer.parseInt(rs.getString("CompraId")));
            compraAux.setCompraDescripcion(rs.getString("CompraDescripcion"));
            compraAux.setCompraMonto(Double.parseDouble(rs.getString("CompraMonto")));
            compraAux.setCompraFecha(LocalDate.parse(rs.getString("CompraFecha"), formatter));
            compraAux.setCompraCuotas(Integer.parseInt(rs.getString("CompraCuotas")));
            Tienda tienda = new Tienda(rs.getInt("TiendaId"), rs.getString("TiendaNombre"));
            Persona persona = new Persona(rs.getInt("PersonaId"), rs.getString("PersonaNombre"),rs.getString("PersonaApellido"));
            Banco banco = new Banco(0,rs.getString("BancoNombre"));
            Tarjeta tarjeta = new Tarjeta(rs.getInt("TarjetaId"));
            compraAux.setTienda(tienda);
            compraAux.setPersona(persona);
            compraAux.setBanco(banco);
            compraAux.setTarjeta(tarjeta);

            compras.add(compraAux);
        }

        con.close();
        stmt.close();
        rs.close();
        return compras;
    }

    @CrossOrigin
    @GetMapping("/{compraId}")
    public Compra getCompra(@PathVariable int compraId) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT C.CompraId, C.CompraDescripcion, C.CompraMonto, C.CompraFecha, C.CompraCuotas, C.TiendaId, T.TiendaNombre, C.TarjetaId, B.BancoNombre, TA.PersonaId, P.PersonaNombre, P.PersonaApellido\n" +
                "FROM COMPRAS AS C, TIENDAS AS T, BANCOS AS B, PERSONAS AS P, TARJETAS AS TA\n" +
                "WHERE C.TiendaId = T.TiendaId\n" +
                "AND C.TarjetaId = TA.TarjetaId\n" +
                "AND TA.BancoId = B.BancoId\n" +
                "AND TA.PersonaId = P.PersonaId AND CompraId = " + compraId);

        Compra compra = new Compra();

        while (rs.next()) {
            compra.setCompraId(Integer.parseInt(rs.getString("CompraId")));
            compra.setCompraDescripcion(rs.getString("CompraDescripcion"));
            compra.setCompraMonto(Double.parseDouble(rs.getString("CompraMonto")));
            compra.setCompraFecha(LocalDate.parse(rs.getString("CompraFecha"), formatter));
            compra.setCompraCuotas(Integer.parseInt(rs.getString("CompraCuotas")));
            Tienda tienda = new Tienda(rs.getInt("TiendaId"), rs.getString("TiendaNombre"));
            Persona persona = new Persona(rs.getInt("PersonaId"), rs.getString("PersonaNombre"),rs.getString("PersonaApellido"));
            Banco banco = new Banco(0,rs.getString("BancoNombre"));
            Tarjeta tarjeta = new Tarjeta(rs.getInt("TarjetaId"));
            compra.setTienda(tienda);
            compra.setPersona(persona);
            compra.setBanco(banco);
            compra.setTarjeta(tarjeta);
        }

        con.close();
        stmt.close();
        rs.close();
        return compra;
    }

    @CrossOrigin
    @PostMapping
    public void ingresarCompra(@RequestBody CompraDTO compra) throws SQLException {
        Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement();

        String compraFecha = compra.getCompraFecha().getYear() + "-" + compra.getCompraFecha().getMonthValue() + "-" + compra.getCompraFecha().getDayOfMonth();

        String query = "INSERT INTO COMPRAS (CompraDescripcion, CompraMonto, CompraFecha, CompraCUotas, TiendaId, TarjetaId) VALUES (\"" + compra.getCompraDescripcion()+"\","+ compra.getCompraMonto()+ ",'" + compraFecha +"'," + compra.getCompraCuotas() + "," + compra.getTiendaId() + "," + compra.getTarjetaId()+");";

        stmt.execute(query);
        con.close();
        stmt.close();
    }

    @CrossOrigin
    @PutMapping
    public void modificarCompra(@RequestBody CompraDTO compra) throws SQLException {
        Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement();

        String compraFecha = compra.getCompraFecha().getYear() + "-" + compra.getCompraFecha().getMonthValue() + "-" + compra.getCompraFecha().getDayOfMonth();

        String query = "UPDATE COMPRAS SET CompraDescripcion = \"" + compra.getCompraDescripcion() + "\", CompraMonto = " + compra.getCompraMonto() + ", CompraFecha = '" + compraFecha + "', CompraCuotas = " + compra.getCompraCuotas() + ", TiendaId = " + compra.getTiendaId() + ", TarjetaId = " + compra.getTarjetaId() + " WHERE CompraId = " + compra.getCompraId() + ";";

        stmt.execute(query);

        con.close();
        stmt.close();
    }

    @CrossOrigin
    @DeleteMapping("/{compraId}")
    public void eliminarCompra(@PathVariable int compraId) throws SQLException {
        Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement();

        String query = "UPDATE COMPRAS SET CompraEliminada = 1 WHERE CompraId = " + compraId;

        stmt.execute(query);

        con.close();
        stmt.close();
    }
}
