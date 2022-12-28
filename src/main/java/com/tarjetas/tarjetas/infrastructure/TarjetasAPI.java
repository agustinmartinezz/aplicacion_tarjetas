package com.tarjetas.tarjetas.infrastructure;

import com.tarjetas.tarjetas.domain.ITarjetas;
import com.tarjetas.tarjetas.domain.Tarjeta;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tarjetas")
public class TarjetasAPI implements ITarjetas {

    @GetMapping
    public List<Tarjeta> getTarjetas() {
        //TODO: Logica para consultar tarjetas a la base de datos y devolverlas en una lista

        return null;
    }
}
