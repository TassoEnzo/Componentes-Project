<template>
  <div id="app">
    <header class="topbar">
      <nav>
        <h1 class="logo">📚 Livraria</h1>
        <ul>
          <li><router-link to="/">Home</router-link></li>
          <li><router-link to="/carrinho">Carrinho ({{ carrinhoStore.totalItens }})</router-link></li>
          <li v-if="!clienteStore.estaLogado"><router-link to="/login">Login</router-link></li>
          <li v-if="clienteStore.estaLogado" class="user-info">
            <span>Olá, {{ clienteStore.nomeCliente }}</span>
            <button @click="handleLogout" class="btn-logout">Sair</button>
          </li>
        </ul>
      </nav>
    </header>

    <main class="content">
      <router-view />
    </main>
  </div>
</template>

<script setup>
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useClienteStore } from './store/cliente';
import { useCarrinhoStore } from './store/carrinho';

const router = useRouter();
const clienteStore = useClienteStore();
const carrinhoStore = useCarrinhoStore();

onMounted(() => {
  clienteStore.initialize();
  
  if (clienteStore.estaLogado) {
    carrinhoStore.carregar();
  }
});

function handleLogout() {
  clienteStore.logout();
  carrinhoStore.limpar();
  router.push('/login');
}
</script>

<style>
:root {
  --bg: #f8f9fa;
  --card-bg: #ffffff;
  --text: #222;
  --text-muted: #666;
  --primary: #1f6feb;
  --primary-hover: #1a5ecc;
  --radius: 14px;
  --shadow: 0 4px 18px rgba(0,0,0,0.06);
  --max-width: 960px;
  --spacing: 20px;
}

body {
  background: var(--bg);
  font-family: "Inter", sans-serif;
  margin: 0;
  color: var(--text);
}

.topbar {
  background: white;
  border-bottom: 1px solid #e5e7eb;
  padding: 14px 0;
  box-shadow: var(--shadow);
}

.logo {
  font-size: 22px;
  font-weight: 600;
}

nav {
  max-width: var(--max-width);
  margin: auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

nav ul {
  list-style: none;
  display: flex;
  gap: 20px;
  align-items: center;
  margin: 0;
  padding: 0;
}

nav a {
  text-decoration: none;
  color: var(--text-muted);
  font-weight: 500;
  transition: color 0.2s;
}

nav a:hover {
  color: var(--primary);
}

nav a.router-link-active {
  color: var(--primary);
}

.user-info {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-info span {
  color: var(--text);
  font-weight: 500;
}

.btn-logout {
  background: #dc3545;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 6px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
}

.btn-logout:hover {
  background: #c82333;
  transform: translateY(-1px);
}

.content {
  max-width: var(--max-width);
  margin: var(--spacing) auto;
  padding: 0 var(--spacing);
}
</style>
