package com.tarjetas_api.tarjetas.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Persona {
    private int personaId;
    private String personaNombre;
    private String personaApellido;
}
