import { defineStore } from "pinia";
import { listarLivros, buscarLivro } from "../services/livroService";

export const useLivroStore = defineStore("livros", {
  state: () => ({
    lista: [],
    livroAtual: null,
    carregando: false,
  }),

  actions: {
    async carregarLivros() {
      this.carregando = true;
      try {
        this.lista = await listarLivros();
      } finally {
        this.carregando = false;
      }
    },

    async carregarLivro(id) {
      this.carregando = true;
      try {
        this.livroAtual = await buscarLivro(id);
      } finally {
        this.carregando = false;
      }
    }
  }
});
