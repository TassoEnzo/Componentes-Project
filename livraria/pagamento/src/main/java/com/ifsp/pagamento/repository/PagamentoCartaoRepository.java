package com.ifsp.pagamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifsp.pagamento.entity.PagamentoCartao;

@Repository
public interface PagamentoCartaoRepository extends JpaRepository<PagamentoCartao, String> {
}