package com.tarjetas.tarjetas.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tarjeta {
    private int tarjetaId;
    private Date tarjetaDiaCierre;
    private int bancoId;
    private int personaId;
}
