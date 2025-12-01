import { defineStore } from "pinia";
import * as clienteService from "../services/clienteService";

export const useClienteStore = defineStore("cliente", {
  state: () => ({
    cliente: null,        
    token: null,          
    loading: false,
    error: null
  }),
  
  getters: {
    estaLogado: (state) => !!state.cliente,
    nomeCliente: (state) => state.cliente?.nome || ''
  },
  
  actions: {
    initialize() {
      const savedCliente = localStorage.getItem('cliente');
      const savedToken = localStorage.getItem('token');
      
      if (savedCliente) {
        try {
          this.cliente = JSON.parse(savedCliente);
        } catch (e) {
          console.error('Erro ao carregar cliente do localStorage:', e);
          localStorage.removeItem('cliente');
        }
      }
      
      if (savedToken) {
        this.token = savedToken;
      }
    },

    async cadastrar(dados) {
      this.loading = true;
      this.error = null;
      try {
        const res = await clienteService.cadastrarCliente(dados);
        
        return res;
      } catch (e) {
        this.error = e;
        throw e;
      } finally {
        this.loading = false;
      }
    },

    async login(credentials) {
      this.loading = true;
      this.error = null;
      try {
        const res = await clienteService.loginCliente(credentials);
        this.cliente = res;

        localStorage.setItem('cliente', JSON.stringify(res));
        
        return res;
      } catch (e) {
        this.error = e;
        throw e;
      } finally {
        this.loading = false;
      }
    },

    async carregarPorId(id) {
      this.loading = true;
      this.error = null;
      try {
        const res = await clienteService.buscarCliente(id);
        this.cliente = res;
        
        localStorage.setItem('cliente', JSON.stringify(res));
        
        return res;
      } catch (e) {
        this.error = e;
        throw e;
      } finally {
        this.loading = false;
      }
    },

    logout() {
      this.cliente = null;
      this.token = null;
      this.error = null;
      
      localStorage.removeItem('cliente');
      localStorage.removeItem('token');
    }
  }
});