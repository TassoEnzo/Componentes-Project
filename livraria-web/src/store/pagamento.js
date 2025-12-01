import { defineStore } from "pinia";
import * as pagamentoService from "../services/pagamentoService";

export const usePagamentoStore = defineStore("pagamento", {
  state: () => ({
    ultimoPagamento: null,
    loading: false,
    error: null
  }),
  actions: {
    async pagarPix(idPedido, chavePix) {
      this.loading = true;
      this.error = null;
      try {
        const res = await pagamentoService.pagarViaPix(idPedido, chavePix);
        this.ultimoPagamento = res;
        return res;
      } catch (e) {
        this.error = e;
        throw e;
      } finally {
        this.loading = false;
      }
    },

    async pagarCartao(idPedido, dadosCartao) {
      this.loading = true;
      this.error = null;
      try {
        const res = await pagamentoService.pagarViaCartao(idPedido, dadosCartao);
        this.ultimoPagamento = res;
        return res;
      } catch (e) {
        this.error = e;
        throw e;
      } finally {
        this.loading = false;
      }
    },

    async buscarPagamento(idPagamento) {
      this.loading = true;
      this.error = null;
      try {
        const res = await pagamentoService.buscarPagamento(idPagamento);
        this.ultimoPagamento = res;
        return res;
      } catch (e) {
        this.error = e;
        throw e;
      } finally {
        this.loading = false;
      }
    }
  }
});
