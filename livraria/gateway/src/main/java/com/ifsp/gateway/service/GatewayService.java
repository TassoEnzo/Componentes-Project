package com.ifsp.gateway.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GatewayService {

    private final RestTemplate rest = new RestTemplate();

    private final String LIVRO_URL = "http://localhost:8080/livro";
    private final String CLIENTE_URL = "http://localhost:8080/cliente";
    private final String CARRINHO_URL = "http://localhost:8080/carrinho";
    private final String PEDIDO_URL = "http://localhost:8080/pedido";
    private final String FRETE_URL = "http://localhost:8080/frete";
    private final String PAGAMENTO_URL = "http://localhost:8080/pagamento";

    public Object listarLivros() {
        return rest.getForObject(LIVRO_URL, Object.class);
    }

    public Object buscarLivro(String id) {
        return rest.getForObject(LIVRO_URL + "/" + id, Object.class);
    }

    public Object adicionarAoCarrinho(String idCliente, String idLivro, int qtd) {
        String url = CARRINHO_URL + "/" + idCliente + "/add/" + idLivro + "/" + qtd;
        return rest.postForObject(url, null, Object.class);
    }

    public Object criarPedido(String idCliente) {
        return rest.postForObject(PEDIDO_URL + "/criar/" + idCliente, null, Object.class);
    }

    public Object calcularFrete(String cep) {
        return rest.getForObject(FRETE_URL + "/" + cep, Object.class);
    }

    public Object pagarPedido(String idPedido, String metodo, String chave) {
        String url = PAGAMENTO_URL + "/pagar?pedido=" + idPedido + "&metodo=" + metodo + "&chave=" + chave;
        return rest.postForObject(url, null, Object.class);
    }
}
