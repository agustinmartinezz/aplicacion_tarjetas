package com.tarjetas.tarjetas.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tarjeta {
    private int tarjetaId;
    private int tarjetaDiaCierre;
    private Banco banco;
    private Persona persona;

    public Tarjeta(int tarjetaId) {
        this.tarjetaId = tarjetaId;
    }
}
