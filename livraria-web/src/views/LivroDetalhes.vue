<template>
  <main class="page">

    <div v-if="carregando" class="loading">Carregando...</div>

    <div v-else-if="livro" class="livro-detalhes">

      <div class="imagem">
        <img
          :src="livro.imagem || 'https://via.placeholder.com/400x500?text=Livro'"
          alt="Capa do Livro"
        />
      </div>

      <div class="info">
        <h1>{{ livro.titulo }}</h1>
        <p class="autor">Autor: {{ livro.autor }}</p>
        <p class="editora">Editora: {{ livro.editora }}</p>
        <p class="categoria">Categoria: {{ livro.categoria }}</p>

        <p class="descricao">
          {{ livro.descricao || "Nenhuma descrição fornecida." }}
        </p>

        <div class="preco-area">
          <span class="preco">R$ {{ livro.preco.toFixed(2) }}</span>

          <button class="btn" @click="adicionarAoCarrinho">
            Adicionar ao Carrinho
          </button>
        </div>

        <router-link class="voltar" to="/livros">← Voltar</router-link>

      </div>
    </div>

    <div v-else class="erro">
      Livro não encontrado.
    </div>

  </main>
</template>

<script>
import { useRoute } from "vue-router";
import { useLivroStore } from "../store/livro";
import { useCarrinhoStore } from "../store/carrinho";

export default {
  setup() {
    const route = useRoute();
    const livroStore = useLivroStore();
    const carrinhoStore = useCarrinhoStore();

    const id = route.params.id;

    livroStore.carregarLivro(id);

    function adicionarAoCarrinho() {
      carrinhoStore.adicionar(livroStore.livroAtual.id);
      alert("Livro adicionado ao carrinho!");
    }

    return {
      livro: livroStore.livroAtual,
      carregando: livroStore.carregando,
      adicionarAoCarrinho
    };
  }
};
</script>

<style scoped>
.page {
  padding: 40px;
  display: flex;
  justify-content: center;
}

.loading {
  font-size: 20px;
}

.livro-detalhes {
  display: flex;
  gap: 40px;
  max-width: 1000px;
  width: 100%;
}

.imagem img {
  width: 330px;
  height: 440px;
  object-fit: cover;
  border-radius: 10px;
}

.info {
  display: flex;
  flex-direction: column;
  gap: 12px;
  max-width: 550px;
}

h1 {
  margin: 0;
  font-size: 32px;
}

.autor,
.editora,
.categoria {
  opacity: 0.8;
}

.descricao {
  margin-top: 10px;
  line-height: 1.5;
}

.preco-area {
  display: flex;
  align-items: center;
  gap: 20px;
}

.preco {
  font-size: 26px;
  font-weight: bold;
  color: #1f6feb;
}

.btn {
  background: #1f6feb;
  color: white;
  border: none;
  padding: 10px 16px;
  border-radius: 8px;
  font-weight: bold;
  cursor: pointer;
}

.btn:hover {
  background: #0d4fa8;
}

.voltar {
  margin-top: 20px;
  display: inline-block;
  opacity: 0.7;
  text-decoration: none;
}
</style>
