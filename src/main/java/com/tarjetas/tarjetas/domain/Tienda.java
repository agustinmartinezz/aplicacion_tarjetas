package com.tarjetas.tarjetas.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tienda {
    private int tiendaId;
    private String tiendaNombre;

    public String toString() {
        String string = tiendaId + " - " + tiendaNombre;
        return string;
    }
}
