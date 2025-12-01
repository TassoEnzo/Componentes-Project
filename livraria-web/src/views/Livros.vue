<template>
  <div class="page fade">
    <h1>Livros</h1>

    <div class="grid grid-3 livros-grid">
      <LivroCard 
        v-for="livro in livros"
        :key="livro.id"
        :livro="livro"
        @ver-detalhes="abrirDetalhes"
      />
    </div>
  </div>
</template>

<script>
import { useLivroStore } from "../store/livros";
import LivroCard from "../components/LivroCard.vue";

export default {
  name: "Livros",

  components: { LivroCard },

  setup() {
    const store = useLivroStore();

    store.carregarLivros();

    return {
      livros: store.livros,
      abrirDetalhes: (id) => {
        window.location.href = `/livro/${id}`;
      }
    };
  }
};
</script>

<style scoped>
h1 {
  margin-bottom: 25px;
  font-size: 28px;
}

.livros-grid {
  margin-top: 20px;
}
</style>
