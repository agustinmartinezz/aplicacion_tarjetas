package com.tarjetas_api.tarjetas.infrastructure;

import com.tarjetas_api.tarjetas.domain.ApplicationRepository;
import com.tarjetas_api.tarjetas.domain.Compra;
import com.tarjetas_api.tarjetas.domain.Tienda;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

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
        restTemplate.postForEntity("http://localhost:7002/compras", compra, Compra.class);
    }

    @Override
    public void modificarCompra(Compra compra) {
        restTemplate.put("http://localhost:7002/compras", compra, Compra.class);
    }

    @Override
    public void eliminarCompra(Compra compra) {
        restTemplate.delete("http://localhost:7002/compras?compraId=" + compra.getCompraId(), compra, Compra.class);
    }

    @Override
    public String getTiendas() {
        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:7002/tiendas", HttpMethod.GET, null, String.class);
        return exchange.getBody();
    }

    @Override
    public void ingresarTienda(Tienda tienda) {
        restTemplate.postForEntity("http://localhost:7002/tiendas", tienda, Tienda.class);
    }

    @Override
    public void modificarTienda(Tienda tienda) {
        restTemplate.put("http://localhost:7002/tiendas", tienda, Tienda.class);
    }
}
