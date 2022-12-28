package com.tarjetas.tarjetas.infrastructure;

import com.tarjetas.tarjetas.domain.ITiendas;
import com.tarjetas.tarjetas.domain.Tienda;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tiendas")
public class TiendasAPI implements ITiendas {
    @GetMapping()
    public List<Tienda> getTiendas() {
        //TODO: Logica para consultar tiendas a la base de datos y devolverla en una lista
        return null;
    }

    @PostMapping
    public void ingresarTienda(@RequestBody Tienda tienda) {
        //TODO: Logica para dar de alta una tienda en base de datos
    }

    @PutMapping
    public void modificarTienda(@RequestBody Tienda tienda) {
        int tiendaId = tienda.getTiendaId();
        //TODO: Logica para modificar tienda en base de datos
    }
}
