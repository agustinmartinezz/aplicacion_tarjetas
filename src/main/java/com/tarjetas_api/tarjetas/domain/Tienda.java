package com.tarjetas_api.tarjetas.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tienda {
    private int tiendaId;
    private String tiendaNombre;

    public Tienda (int tiendaId) {
        this.tiendaId = tiendaId;
    }
    public String toString() {
        String string = tiendaId + " - " + tiendaNombre;
        return string;
    }
}
