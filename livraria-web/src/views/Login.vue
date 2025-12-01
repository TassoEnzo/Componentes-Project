<template>
  <section class="box">
    <h2>Entrar</h2>

    <!-- Mensagem de erro -->
    <div v-if="error" class="alert alert-error">
      {{ error }}
    </div>

    <form @submit.prevent="handleLogin" class="form">
      <input 
        v-model="form.email" 
        type="email" 
        placeholder="Email" 
        required 
        :disabled="loading"
      />
      
      <input 
        v-model="form.senha" 
        type="password" 
        placeholder="Senha" 
        required 
        :disabled="loading"
      />

      <button type="submit" class="btn" :disabled="loading">
        {{ loading ? 'Entrando...' : 'Entrar' }}
      </button>
    </form>

    <p class="muted link">
      Não tem conta?
      <router-link to="/cadastro">Criar conta</router-link>
    </p>
  </section>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useClienteStore } from '../store/cliente';

const router = useRouter();
const clienteStore = useClienteStore();

const form = ref({
  email: '',
  senha: ''
});

const loading = ref(false);
const error = ref(null);

async function handleLogin() {
  loading.value = true;
  error.value = null;

  try {
    await clienteStore.login(form.value);
    
    router.push('/');
    
  } catch (e) {
    console.error('Erro no login:', e);
    error.value = 'Email ou senha incorretos. Tente novamente.';
  } finally {
    loading.value = false;
  }
}
</script>

<style scoped>
.box {
  background: white;
  padding: 28px;
  border-radius: 14px;
  box-shadow: var(--shadow);
  max-width: 400px;
  margin: auto;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 12px;
  margin: 20px 0;
}

.form input {
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 14px;
}

.form input:focus {
  outline: none;
  border-color: var(--primary);
}

.form input:disabled {
  background: #f5f5f5;
  cursor: not-allowed;
}

.btn {
  width: 100%;
  padding: 12px;
  background: var(--primary);
  color: white;
  border-radius: 10px;
  border: none;
  cursor: pointer;
  font-weight: 600;
  font-size: 15px;
  transition: all 0.2s;
}

.btn:hover:not(:disabled) {
  opacity: 0.9;
  transform: translateY(-1px);
}

.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.link {
  text-align: center;
  margin-top: 10px;
}

.link a {
  color: var(--primary);
  text-decoration: none;
  font-weight: 600;
}

.link a:hover {
  text-decoration: underline;
}

.alert {
  padding: 12px;
  border-radius: 8px;
  margin-bottom: 16px;
  font-size: 14px;
}

.alert-error {
  background: #fee;
  color: #c33;
  border: 1px solid #fcc;
}
</style>