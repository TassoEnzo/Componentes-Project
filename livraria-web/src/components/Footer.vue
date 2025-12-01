<template>
  <footer class="footer">
    <p>© 2025 Livraria — Todos os direitos reservados.</p>

    <nav class="links">
      <router-link to="/">Home</router-link>
      <router-link to="/livros">Livros</router-link>
      <router-link to="/carrinho">Carrinho</router-link>
      <router-link to="/login">Login</router-link>
    </nav>
  </footer>
</template>

<style scoped>
.footer {
  margin-top: 60px;
  padding: 30px 20px;
  text-align: center;
  background: white;
  border-top: 1px solid #e5e7eb;
  color: #6b7280;
  box-shadow: 0 -2px 12px rgba(20, 30, 50, 0.05);
}

.links {
  margin-top: 12px;
  display: flex;
  justify-content: center;
  gap: 18px;
}

a {
  text-decoration: none;
  color: var(--primary);
  font-weight: 500;
  transition: 0.2s ease;
}

a:hover {
  opacity: 0.6;
}
</style>
