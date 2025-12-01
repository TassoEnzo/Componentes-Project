<template>
  <section class="box">
    <h2>Pagamento</h2>

    <p class="muted">Escolha uma forma de pagamento</p>

    <div class="options">
      <button :class="{sel: metodo==='cartao'}" @click="metodo='cartao'">Cartão</button>
      <button :class="{sel: metodo==='pix'}" @click="metodo='pix'">Pix</button>
    </div>

    <div v-if="metodo==='cartao'" class="form">
      <input placeholder="Número do cartão" />
      <input placeholder="Nome no cartão" />
      <input placeholder="Validade" />
      <input placeholder="CVV" />
    </div>

    <div v-if="metodo==='pix'" class="pix-box">
      <p>Use o QR code ou chave para pagar</p>
      <div class="qr">QR CODE</div>
    </div>

    <router-link to="/pedido-confirmado" class="btn">Confirmar pagamento</router-link>
  </section>
</template>

<script>
export default {
  data() {
    return { metodo: "pix" };
  }
}
</script>

<style scoped>
.box {
  background: white;
  padding: 28px;
  border-radius: 14px;
  box-shadow: var(--shadow);
}

.options {
  display: flex;
  gap: 10px;
  margin: 15px 0;
}

.options button {
  flex: 1;
  padding: 10px;
  border-radius: 10px;
  border: 1px solid #ddd;
  background: #f3f4f6;
  cursor: pointer;
}

.sel {
  background: var(--primary) !important;
  color: white;
  border-color: var(--primary);
}

.form {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

input {
  padding: 10px;
  border-radius: 10px;
  border: 1px solid #ddd;
}

.pix-box {
  margin: 20px 0;
}

.qr {
  width: 160px;
  height: 160px;
  background: #eee;
  display: grid;
  place-items: center;
  border-radius: 8px;
  margin-top: 10px;
}

.btn {
  display: inline-block;
  background: var(--primary);
  color: white;
  padding: 10px 18px;
  border-radius: 10px;
  text-decoration: none;
  margin-top: 20px;
}
</style>

