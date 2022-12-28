package com.tarjetas.tarjetas.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Compra {
    private int compraId;
    private String compraDescripcion;
    private double compraMonto;
    private Date compraFecha;
    private int compraCuotas;
    private int tiendaId;
    private  int tarjetaId;
}
