package com.ifsp.pedido.service;

import com.ifsp.carrinho.entity.Carrinho;
import com.ifsp.carrinho.service.CarrinhoService;
import com.ifsp.livro.entity.Livro;
import com.ifsp.livro.service.LivroService;
import com.ifsp.pedido.entity.ItemPedido;
import com.ifsp.pedido.entity.Pedido;
import com.ifsp.pedido.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final CarrinhoService carrinhoService;
    private final LivroService livroService;

    public PedidoService(PedidoRepository repository,
                         CarrinhoService carrinhoService,
                         LivroService livroService) {

        this.repository = repository;
        this.carrinhoService = carrinhoService;
        this.livroService = livroService;
    }

    public Pedido criarPedidoDesdeCarrinho(String idCliente) {
        Carrinho carrinho = carrinhoService.getCarrinho(idCliente);

        if (carrinho.getItens().isEmpty()) {
            throw new RuntimeException("Carrinho vazio.");
        }

        Pedido pedido = new Pedido(idCliente);

        carrinho.getItens().forEach(item -> {
            Livro l = item.getLivro();
            ItemPedido itemPedido = new ItemPedido(
                    l.getId(),
                    l.getTitulo(),
                    item.getQuantidade(),
                    l.getPreco()
            );
            pedido.adicionarItem(itemPedido);

            livroService.diminuirEstoque(l.getId(), item.getQuantidade());
        });

        carrinhoService.limparCarrinho(idCliente);

        return repository.save(pedido);
    }

    public List<Pedido> listarPorCliente(String idCliente) {
        return repository.findByIdCliente(idCliente);
    }

    public Pedido buscarPorId(String idPedido) {
        return repository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    public Pedido atualizarStatus(String idPedido, Pedido.StatusPedido novoStatus) {
        Pedido pedido = buscarPorId(idPedido);
        pedido.setStatus(novoStatus);
        return repository.save(pedido);
    }
}
