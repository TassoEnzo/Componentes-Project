import { defineStore } from 'pinia';
import * as carrinhoService from '../services/carrinhoService';
import * as freteService from '../services/freteService';
import { useClienteStore } from './cliente';

export const useCarrinhoStore = defineStore('carrinho', {
  state: () => ({
    itens: [],
    frete: null,
    cepFrete: '',
    loading: false,
    loadingFrete: false,
    error: null
  }),

  getters: {
    totalItens: (state) => {
      return state.itens.reduce((total, item) => total + item.quantidade, 0);
    },

    subtotal: (state) => {
      return state.itens.reduce((total, item) => {
        const valorItem = item.subtotal || (item.livro?.preco * item.quantidade) || 0;
        return total + valorItem;
      }, 0);
    },

    valorFrete: (state) => {
      return state.frete?.valor || 0;
    },

    total: (state) => {
      const subtotalValue = state.itens.reduce((total, item) => {
        const valorItem = item.subtotal || (item.livro?.preco * item.quantidade) || 0;
        return total + valorItem;
      }, 0);
      const freteValue = state.frete?.valor || 0;
      return subtotalValue + freteValue;
    },

    temItens: (state) => state.itens.length > 0
  },

  actions: {
    async carregar() {
      const clienteStore = useClienteStore();
      
      if (!clienteStore.cliente?.id) {
        console.warn('Cliente não está logado');
        return;
      }

      this.loading = true;
      this.error = null;

      try {
        const carrinho = await carrinhoService.obterCarrinho(clienteStore.cliente.id);
        console.log('Carrinho recebido:', carrinho);
        this.itens = carrinho.itens || [];
      } catch (e) {
        console.error('Erro ao carregar carrinho:', e);
        this.error = e;
        this.itens = [];
      } finally {
        this.loading = false;
      }
    },

    async calcularFrete(cep) {
      if (!cep || cep.length < 8) {
        this.error = 'CEP inválido';
        return;
      }

      this.loadingFrete = true;
      this.error = null;

      try {
        const freteCalculado = await freteService.calcularFrete(cep);
        this.frete = freteCalculado;
        this.cepFrete = cep;
        return freteCalculado;
      } catch (e) {
        console.error('Erro ao calcular frete:', e);
        this.error = 'Erro ao calcular frete';
        this.frete = null;
        throw e;
      } finally {
        this.loadingFrete = false;
      }
    },

    limparFrete() {
      this.frete = null;
      this.cepFrete = '';
    },

    async adicionar(livro, quantidade = 1) {
      const clienteStore = useClienteStore();
      
      if (!clienteStore.cliente?.id) {
        throw new Error('Você precisa estar logado para adicionar ao carrinho');
      }

      this.loading = true;
      this.error = null;

      try {
        const item = {
          idLivro: livro.id,
          titulo: livro.titulo,
          autor: livro.autor,
          preco: livro.preco,
          imagemUrl: livro.imagemUrl,
          quantidade
        };

        await carrinhoService.adicionarItem(clienteStore.cliente.id, item);
        await this.carregar();
        
        return true;
      } catch (e) {
        console.error('Erro ao adicionar item:', e);
        this.error = e;
        throw e;
      } finally {
        this.loading = false;
      }
    },

    async remover(idItem) {
      const clienteStore = useClienteStore();
      
      if (!clienteStore.cliente?.id) return;

      this.loading = true;
      this.error = null;

      try {
        await carrinhoService.removerItem(clienteStore.cliente.id, idItem);
        await this.carregar();
      } catch (e) {
        console.error('Erro ao remover item:', e);
        this.error = e;
        throw e;
      } finally {
        this.loading = false;
      }
    },

    async atualizarQuantidade(idLivro, quantidade) {
      const clienteStore = useClienteStore();
      
      if (!clienteStore.cliente?.id) return;

      this.loading = true;
      this.error = null;

      try {
        await carrinhoService.atualizarQuantidade(clienteStore.cliente.id, idLivro, quantidade);
        await this.carregar();
      } catch (e) {
        console.error('Erro ao atualizar quantidade:', e);
        this.error = e;
        throw e;
      } finally {
        this.loading = false;
      }
    },

    async limpar() {
      const clienteStore = useClienteStore();
      
      if (!clienteStore.cliente?.id) return;

      this.loading = true;
      this.error = null;

      try {
        await carrinhoService.limparCarrinho(clienteStore.cliente.id);
        this.itens = [];
      } catch (e) {
        console.error('Erro ao limpar carrinho:', e);
        this.error = e;
        throw e;
      } finally {
        this.loading = false;
      }
    }
  }
});