package com.tarjetas.tarjetas.domain;

import java.sql.SQLException;
import java.util.List;

public interface IPersonas {
    List<Persona> getPersonas() throws SQLException;
}
