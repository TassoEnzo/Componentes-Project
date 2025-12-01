import { defineStore } from "pinia";
import * as pedidoService from "../services/pedidoService";

export const usePedidoStore = defineStore("pedido", {
  state: () => ({
    pedidos: [],         
    pedidoAtual: null,   
    loading: false,
    error: null
  }),
  actions: {
    async criarPedido(idCliente) {
      this.loading = true;
      this.error = null;
      try {
        const res = await pedidoService.criarPedido(idCliente);
        this.pedidoAtual = res;
        return res;
      } catch (e) {
        this.error = e;
        throw e;
      } finally {
        this.loading = false;
      }
    },

    async listarPedidos(idCliente) {
      this.loading = true;
      this.error = null;
      try {
        const res = await pedidoService.listarPedidos(idCliente);
        this.pedidos = res || [];
        return this.pedidos;
      } catch (e) {
        this.error = e;
        throw e;
      } finally {
        this.loading = false;
      }
    },

    async buscarPedido(idPedido) {
      this.loading = true;
      this.error = null;
      try {
        const res = await pedidoService.buscarPedido(idPedido);
        this.pedidoAtual = res;
        return res;
      } catch (e) {
        this.error = e;
        throw e;
      } finally {
        this.loading = false;
      }
    },

    async atualizarStatus(idPedido, novoStatus) {
      this.loading = true;
      this.error = null;
      try {
        const res = await pedidoService.atualizarStatus(idPedido, novoStatus);
        if (this.pedidoAtual && this.pedidoAtual.id === idPedido) {
          this.pedidoAtual.status = res.status ?? novoStatus;
        }
        this.pedidos = this.pedidos.map(p => p.id === idPedido ? { ...p, status: res.status ?? novoStatus } : p);
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
