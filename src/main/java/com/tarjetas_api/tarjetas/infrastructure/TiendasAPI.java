package com.tarjetas_api.tarjetas.infrastructure;

import com.tarjetas_api.tarjetas.domain.ITiendas;
import com.tarjetas_api.tarjetas.domain.Tienda;
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
@RequestMapping("/tiendas")
public class TiendasAPI implements ITiendas {
    @Autowired
    DataSource dataSource;

    @GetMapping()
    public List<Tienda> getTiendas() throws SQLException {
        List<Tienda> tiendas = new ArrayList<>();

        Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM TIENDAS;");

        while (rs.next()) {
            Tienda tiendaAux = new Tienda();

            tiendaAux.setTiendaId(rs.getInt("TiendaId"));
            tiendaAux.setTiendaNombre(rs.getString("TiendaNombre"));

            tiendas.add(tiendaAux);
        }

        con.close();
        stmt.close();
        rs.close();

        return tiendas;
    }

    @PostMapping
    public void ingresarTienda(@RequestBody Tienda tienda) throws SQLException {
        Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement();

        String query = "INSERT INTO TIENDAS (TiendaNombre) VALUES (\""+ tienda.getTiendaNombre() +"\");";

        stmt.execute(query);

        con.close();
        stmt.close();
    }

    @PutMapping
    public void modificarTienda(@RequestBody Tienda tienda) throws SQLException {
        Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement();

        String query = "UPDATE TIENDAS SET TiendaNombre = \"" + tienda.getTiendaNombre() + "\" WHERE TiendaId = " + tienda.getTiendaId() + ";";

        stmt.execute(query);

        con.close();
        stmt.close();
    }
}