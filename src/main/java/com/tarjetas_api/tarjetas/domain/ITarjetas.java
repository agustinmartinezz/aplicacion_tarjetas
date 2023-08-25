package com.tarjetas_api.tarjetas.domain;

import java.sql.SQLException;
import java.util.List;

public interface ITarjetas {
    List<Tarjeta> getTarjetas() throws SQLException;
}
