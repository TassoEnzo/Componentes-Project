package com.ifsp.pagamento.service;

import org.springframework.stereotype.Service;

import com.ifsp.pagamento.entity.Pagamento;
import com.ifsp.pagamento.entity.PagamentoCartao;
import com.ifsp.pagamento.entity.PagamentoPix;
import com.ifsp.pagamento.repository.PagamentoCartaoRepository;
import com.ifsp.pagamento.repository.PagamentoPixRepository;
import com.ifsp.pedido.entity.Pedido;
import com.ifsp.pedido.service.PedidoService;

@Service
public class PagamentoService {

    private final PagamentoPixRepository pagamentoPixRepository;
    private final PagamentoCartaoRepository pagamentoCartaoRepository;
    private final PedidoService pedidoService;

    public PagamentoService(
            PagamentoPixRepository pagamentoPixRepository,
            PagamentoCartaoRepository pagamentoCartaoRepository,
            PedidoService pedidoService) {
        
        this.pagamentoPixRepository = pagamentoPixRepository;
        this.pagamentoCartaoRepository = pagamentoCartaoRepository;
        this.pedidoService = pedidoService;
    }

    public Pagamento pagarComPix(String idPedido, String chavePix) {

        Pedido pedido = pedidoService.buscarPorId(idPedido);

        double totalComDesconto = pedido.getValorTotal() * 0.95;

        PagamentoPix pagamento = new PagamentoPix(
                idPedido,
                totalComDesconto,
                chavePix
        );

        pedidoService.atualizarStatus(idPedido, Pedido.StatusPedido.PAGO);

        return pagamentoPixRepository.save(pagamento);  // ← Usa o repository correto
    }

    public Pagamento pagarComCartao(String idPedido,
                                    String numeroCartao,
                                    String nomeTitular,
                                    int parcelas) {

        Pedido pedido = pedidoService.buscarPorId(idPedido);

        double total = pedido.getValorTotal();

        PagamentoCartao pagamento = new PagamentoCartao(
                idPedido,
                total,
                numeroCartao,
                nomeTitular,
                parcelas
        );

        pedidoService.atualizarStatus(idPedido, Pedido.StatusPedido.PAGO);

        return pagamentoCartaoRepository.save(pagamento);
    }
}