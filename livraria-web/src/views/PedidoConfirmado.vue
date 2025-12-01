<template>
  <section class="box center">
    <h2>Pedido confirmado! 🎉</h2>
    <p class="muted">Obrigado pela sua compra.</p>

    <router-link to="/" class="btn">Voltar para Home</router-link>
  </section>
</template>

<style scoped>
.box {
  background: white;
  padding: 40px;
  border-radius: 14px;
  box-shadow: var(--shadow);
  text-align: center;
}

.btn {
  margin-top: 20px;
  padding: 10px 18px;
  background: var(--primary);
  color: white;
  text-decoration: none;
  border-radius: 10px;
}
</style>
