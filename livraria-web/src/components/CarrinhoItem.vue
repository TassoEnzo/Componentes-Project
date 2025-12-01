<template>
  <div class="card">
    <div class="info">
      <h3>{{ item.titulo }}</h3>
      <p class="muted">Qtd: {{ item.quantidade }}</p>
      <p class="price">Subtotal: R$ {{ (item.preco * item.quantidade).toFixed(2) }}</p>
    </div>

    <div class="actions">
      <button class="btn-small" @click="$emit('remover', item.idLivro)">Remover</button>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    item: { type: Object, required: true }
  }
};
</script>

<style scoped>
.card {
  background: white;
  padding: 18px;
  border-radius: 14px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: var(--shadow);
}

.muted {
  color: var(--text-muted);
  margin: 4px 0;
}

.price {
  margin-top: 6px;
  font-weight: 600;
}

.actions {
  display: flex;
  align-items: center;
}

.btn-small {
  background: #ef4444;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 8px;
  cursor: pointer;
  transition: 0.2s;
}

.btn-small:hover {
  background: #dc2626;
}
</style>
