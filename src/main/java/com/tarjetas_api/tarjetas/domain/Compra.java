package com.tarjetas_api.tarjetas.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Compra {
    private int compraId;
    private String compraDescripcion;
    private double compraMonto;
    private LocalDate compraFecha;
    private int compraCuotas;
    private Tienda tienda;
    private Tarjeta tarjeta;
    private Banco banco;
    private Persona persona;

    public String toString() {
        String string = compraId + " - " + compraDescripcion + " - " + compraFecha.toString() + " - $" + compraMonto;
        return string;
    }
}
