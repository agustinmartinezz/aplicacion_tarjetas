package com.tarjetas_api.tarjetas.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Banco {
    private int bancoId;
    private String bancoNombre;
}
