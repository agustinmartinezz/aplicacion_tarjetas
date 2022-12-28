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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/compras")
public class ComprasAPI implements ICompras {

    @Autowired
    DataSource dataSource;

    @GetMapping()
    public List<Compra> getCompras(@RequestParam("eliminadas") int eliminadas) throws SQLException {
        //TODO: Logica para consultar compras a la base de datos y devolverlas en una lista
        List<Compra> compras = new ArrayList<Compra>();
        Compra compraAux = new Compra();

        Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM COMPRAS WHERE ");

        while (rs.next()) {
            compraAux.setCompraId(Integer.parseInt(rs.getString("CompraId")));
            compraAux.setCompraDescripcion(rs.getString("CompraDescripcion"));
            compraAux.setCompraMonto(Integer.parseInt(rs.getString("CompraMonto")));
            //TODO: Ver tema de parseo de string a date, o capaz puedo obtenerlo directamente como date
            /*compraAux.setCompraFecha(rs.getString("CompraFecha"));*/
            compraAux.setCompraCuotas(Integer.parseInt(rs.getString("CompraCuotas")));
            compraAux.setTiendaId(Integer.parseInt(rs.getString("TiendaId")));
            compraAux.setTarjetaId(Integer.parseInt(rs.getString("TarjetaId")));
        }

        con.close();
        stmt.close();
        rs.close();
        return null;
    }

/*    @GetMapping("/{eliminadas}")
    public List<Compra> getCompras(@PathVariable("eliminadas") int eliminadas) throws SQLException {
        //TODO: Logica para consultar compras a la base de datos y devolverlas en una lista
        Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM COMPRA");

        while (rs.next()) {
        System.out.println("Entre aca");
        }

        con.close();
        stmt.close();
        rs.close();
        return null;
    }*/

    @PostMapping
    public void ingresarCompra(@RequestBody Compra compra) {
        //TODO: Logica para ingresar compra a la base de datos
    }

    @PutMapping
    public void modificarCompra(@RequestBody Compra compra) {
        //TODO: Logica para modificar compra en base de datos
    }

    @DeleteMapping("/{compraId}")
    public void eliminarCompra(@PathVariable("compraId") int compraId) {
        //TODO: Logica para eliminar compra logicamente de base de datos
    }
}
