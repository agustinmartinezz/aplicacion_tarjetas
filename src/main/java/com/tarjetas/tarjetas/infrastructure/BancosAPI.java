package com.tarjetas.tarjetas.infrastructure;

import com.tarjetas.tarjetas.domain.Banco;
import com.tarjetas.tarjetas.domain.IBancos;
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
@RequestMapping("/bancos")
public class BancosAPI implements IBancos {

    @Autowired
    DataSource dataSource;

    @GetMapping
    public List<Banco> getBancos() throws SQLException {
        List<Banco> bancos = new ArrayList<>();

        Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM BANCOS");

        while (rs.next()) {
            Banco bancoAux = new Banco();

            bancoAux.setBancoId(rs.getInt("BancoId"));
            bancoAux.setBancoNombre(rs.getString("BancoNombre"));

            bancos.add(bancoAux);
        }

        con.close();
        stmt.close();
        rs.close();

        return bancos;
    }
}
