<template>
  <article class="card">
    <h3>{{ livro.titulo }}</h3>
    <p class="muted">{{ livro.autor }}</p>
    <div class="price">R$ {{ livro.preco.toFixed(2) }}</div>
    <div class="actions">
      <router-link :to="{ name:'livro', params:{ id: livro.id } }">Detalhes</router-link>
      <button @click="$emit('add', livro.id)">Adicionar</button>
    </div>
  </article>
</template>

<script>
export default { props: { livro: { type: Object, required: true } } }
</script>

<style scoped>
.card{ padding:16px;border-radius:12px;background:white;box-shadow:0 6px 18px rgba(20,30,50,0.06); }
.muted{ color:#6b7280;margin:6px 0; }
.price{ font-weight:600;margin-top:8px; }
.actions{ display:flex;gap:8px;margin-top:12px;align-items:center; }
button{ background:#1f6feb;color:white;border:none;padding:6px 10px;border-radius:8px;cursor:pointer; }
</style>
