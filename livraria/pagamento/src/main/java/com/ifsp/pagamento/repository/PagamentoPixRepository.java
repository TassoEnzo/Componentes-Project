package com.ifsp.pagamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifsp.pagamento.entity.PagamentoPix;

@Repository
public interface PagamentoPixRepository extends JpaRepository<PagamentoPix, String> {
}