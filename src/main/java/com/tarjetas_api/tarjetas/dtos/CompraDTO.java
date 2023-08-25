package com.tarjetas_api.tarjetas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompraDTO {
    private int compraId;
    private String compraDescripcion;
    private double compraMonto;
    private LocalDate compraFecha;
    private int compraCuotas;
    private int tiendaId;
    private int tarjetaId;
}
