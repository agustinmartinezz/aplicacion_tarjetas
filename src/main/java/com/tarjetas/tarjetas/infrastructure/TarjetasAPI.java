package com.tarjetas.tarjetas.infrastructure;

import com.tarjetas.tarjetas.domain.Compra;
import com.tarjetas.tarjetas.domain.ITarjetas;
import com.tarjetas.tarjetas.domain.Tarjeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tarjetas")
public class TarjetasAPI implements ITarjetas {
    @Autowired
    DataSource dataSource;

    @GetMapping
    public List<Tarjeta> getTarjetas() throws SQLException {
        List<Tarjeta> tarjetas = new ArrayList<>();

        Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM TARJETAS");

        while (rs.next()) {
            Tarjeta tarjetaAux = new Tarjeta();

            tarjetaAux.setTarjetaId(rs.getInt("TarjetaId"));
            tarjetaAux.setTarjetaDiaCierre(rs.getInt("TarjetaDiaCierre"));
            tarjetaAux.setBancoId(rs.getInt("BancoId"));
            tarjetaAux.setPersonaId(rs.getInt("PersonaId"));

            tarjetas.add(tarjetaAux);
        }

        con.close();
        stmt.close();
        rs.close();

        return tarjetas;
    }
}
