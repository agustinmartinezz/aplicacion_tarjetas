package com.tarjetas.tarjetas.infrastructure;

import com.tarjetas.tarjetas.domain.ApplicationRepository;
import com.tarjetas.tarjetas.domain.Compra;
import com.tarjetas.tarjetas.domain.Tienda;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@AllArgsConstructor
public class RestRepository implements ApplicationRepository {
    RestTemplate restTemplate;

    @Override
    public String getTarjetas() {
        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:7002/tarjetas", HttpMethod.GET, null, String.class);
        return exchange.getBody();
    }

    @Override
    public String getCompras() {
        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:7002/compras?eliminadas=0", HttpMethod.GET, null, String.class);
        return exchange.getBody();
    }

    @Override
    public String getBancos() {
        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:7002/bancos", HttpMethod.GET, null, String.class);
        return exchange.getBody();
    }

    @Override
    public String getPersonas() {
        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:7002/personas", HttpMethod.GET, null, String.class);
        return exchange.getBody();
    }

    @Override
    public void ingresarCompra(Compra compra) {

    }

    @Override
    public void modificarCompra(Compra compra) {

    }

    @Override
    public void eliminarCompra(Compra compra) {

    }

    @Override
    public String getTiendas() {
        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:7002/tiendas", HttpMethod.GET, null, String.class);
        return exchange.getBody();
    }

    @Override
    public void ingresarTienda(Tienda tienda) {

    }

    @Override
    public void modificarTienda(Tienda tienda) {

    }
}
