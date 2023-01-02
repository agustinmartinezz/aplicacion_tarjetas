package com.tarjetas.tarjetas.infrastructure;

import com.tarjetas.tarjetas.domain.Banco;
import com.tarjetas.tarjetas.domain.Persona;
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
@RequestMapping("/personas")
public class PersonasAPI {

    @Autowired
    DataSource dataSource;

    @GetMapping
    public List<Persona> getPersonas() throws SQLException {
        List<Persona> personas = new ArrayList<>();

        Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM PERSONAS");

        while (rs.next()) {
            Persona personaAux = new Persona();

            personaAux.setPersonaId(rs.getInt("PersonaId"));
            personaAux.setPersonaNombre(rs.getString("PersonaNombre"));
            personaAux.setPersonaApellido(rs.getString("PersonaApellido"));


            personas.add(personaAux);
        }

        con.close();
        stmt.close();
        rs.close();

        return personas;
    }
}
