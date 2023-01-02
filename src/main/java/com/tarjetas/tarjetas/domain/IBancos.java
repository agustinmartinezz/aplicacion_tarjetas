package com.tarjetas.tarjetas.domain;

import java.sql.SQLException;
import java.util.List;

public interface IBancos {
    List<Banco> getBancos() throws SQLException;
}
