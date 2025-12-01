<template>
  <header class="nav">
    <div class="nav-inner">
      <router-link to="/" class="brand">Livraria</router-link>
      <nav class="links">
        <router-link to="/" class="link">Livros</router-link>
        <router-link to="/carrinho" class="link">Carrinho ({{ qtd }})</router-link>
        <router-link to="/pedidos" class="link">Pedidos</router-link>
      </nav>
    </div>
  </header>
</template>

<script>
import { computed } from 'vue';
import { useCartStore } from '../store/carrinho';
export default {
  setup() {
    const cart = useCartStore();
    const qtd = computed(() => cart.itens.reduce((s,i)=>s + (i.quantidade||0), 0));
    return { qtd };
  }
}
</script>

<style scoped>
.nav { background: #f8f9fb; border-bottom: 1px solid #e6e9ee; }
.nav-inner{ max-width:1100px;margin:0 auto;display:flex;align-items:center;justify-content:space-between;padding:14px; }
.brand{ font-weight:700;color:#111;text-decoration:none;font-size:20px; }
.links .link{ margin-left:18px;color:#435; text-decoration:none; }
</style>
