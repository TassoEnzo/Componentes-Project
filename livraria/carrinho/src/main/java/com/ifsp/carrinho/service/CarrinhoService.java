package com.ifsp.carrinho.service;

import com.ifsp.carrinho.entity.Carrinho;
import com.ifsp.carrinho.entity.ItemCarrinho;
import com.ifsp.carrinho.repository.CarrinhoRepository;
import com.ifsp.cliente.entity.Cliente;
import com.ifsp.cliente.service.ClienteService;
import com.ifsp.livro.entity.Livro;
import com.ifsp.livro.service.LivroService;
import org.springframework.stereotype.Service;

@Service
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepo;
    private final ClienteService clienteService;
    private final LivroService livroService;

    public CarrinhoService(CarrinhoRepository carrinhoRepo, ClienteService clienteService, LivroService livroService) {
        this.carrinhoRepo = carrinhoRepo;
        this.clienteService = clienteService;
        this.livroService = livroService;
    }

    public Carrinho getCarrinho(String idCliente) {
        Cliente cliente = clienteService.buscarPorId(idCliente);

        return carrinhoRepo.findByCliente(cliente)
                .orElseGet(() -> carrinhoRepo.save(new Carrinho(cliente)));
    }

    public Carrinho adicionarItem(String idCliente, String idLivro, int quantidade) {
        Carrinho carrinho = getCarrinho(idCliente);

        Livro livro = livroService.buscarPorId(idLivro);

        ItemCarrinho item = carrinho.getItens().stream()
                .filter(i -> i.getLivro().getId().equals(idLivro))
                .findFirst()
                .orElse(null);

        if (item == null) {
            item = new ItemCarrinho(livro, quantidade);
            carrinho.getItens().add(item);
        } else {
            item.setQuantidade(item.getQuantidade() + quantidade);
        }

        carrinho.recalcularTotal();
        return carrinhoRepo.save(carrinho);
    }

    public Carrinho removerItem(String idCliente, String idItem) {
        Carrinho carrinho = getCarrinho(idCliente);

        carrinho.getItens().removeIf(i -> i.getId().equals(idItem));

        carrinho.recalcularTotal();
        return carrinhoRepo.save(carrinho);
    }

    public Carrinho limparCarrinho(String idCliente) {
        Carrinho carrinho = getCarrinho(idCliente);

        carrinho.getItens().clear();
        carrinho.recalcularTotal();
        return carrinhoRepo.save(carrinho);
    }
}
