<template>
  <section class="box">
    <h2>Criar conta</h2>

    <div v-if="error" class="alert alert-error">
      {{ error }}
    </div>

    <div v-if="sucesso" class="alert alert-success">
      ✅ Conta criada com sucesso! Faça login para continuar.
    </div>

    <form @submit.prevent="handleCadastro" class="form">
      <input 
        v-model="form.nome" 
        placeholder="Nome completo" 
        required 
        :disabled="loading"
      />
      
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
        placeholder="Senha (mínimo 6 caracteres)" 
        required 
        minlength="6"
        :disabled="loading"
      />

      <input 
        v-model="form.cpf" 
        placeholder="CPF (opcional)" 
        :disabled="loading"
      />

      <input 
        v-model="form.telefone" 
        placeholder="Telefone (opcional)" 
        :disabled="loading"
      />

      <button type="submit" class="btn" :disabled="loading">
        {{ loading ? 'Cadastrando...' : 'Cadastrar' }}
      </button>
    </form>

    <p class="muted link">
      Já tem conta?
      <router-link to="/login">Entrar</router-link>
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
  nome: '',
  email: '',
  senha: '',
  cpf: '',
  telefone: ''
});

const loading = ref(false);
const error = ref(null);
const sucesso = ref(false);

async function handleCadastro() {
  loading.value = true;
  error.value = null;
  sucesso.value = false;

  try {
    const dados = {
      nome: form.value.nome.trim(),
      email: form.value.email.trim(),
      senha: form.value.senha
    };

    const cpfTrimmed = form.value.cpf ? form.value.cpf.trim() : '';
    if (cpfTrimmed) {
      dados.cpf = cpfTrimmed;
    }
    
    const telefoneTrimmed = form.value.telefone ? form.value.telefone.trim() : '';
    if (telefoneTrimmed) {
      dados.telefone = telefoneTrimmed;
    }

    console.log('Dados a serem enviados:', dados);

    await clienteStore.cadastrar(dados);

    sucesso.value = true;
    
    setTimeout(() => {
      router.push('/login');
    }, 2000);

  } catch (e) {
    console.error('Erro no cadastro:', e);
    error.value = typeof e === 'string' ? e : 'Erro ao criar conta. Tente novamente.';
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

.alert-success {
  background: #efe;
  color: #3a3;
  border: 1px solid #cfc;
}
</style>
