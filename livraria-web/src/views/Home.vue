<template>
  <div class="home">
    <h1>Descubra livros</h1>

    <div v-if="loading" class="loading">
      Carregando livros...
    </div>

    <div v-if="error" class="alert alert-error">
      {{ error }}
    </div>

    <div v-if="!loading && livros.length === 0" class="empty">
      <p>Nenhum livro disponível no momento.</p>
    </div>

    <div v-if="!loading && livros.length > 0" class="livros-grid">
      <div 
        v-for="livro in livros" 
        :key="livro.id" 
        class="livro-card"
        @click="abrirDetalhes(livro)"
      >
        <div class="livro-imagem">
          <img 
            :src="livro.imagemUrl || 'https://via.placeholder.com/200x300?text=Sem+Imagem'" 
            :alt="livro.titulo"
            @error="substituirImagemPadrao"
          />
        </div>
        <div class="livro-info">
          <h3>{{ livro.titulo }}</h3>
          <p class="autor">{{ livro.autor }}</p>
          <p class="preco">R$ {{ livro.preco?.toFixed(2) }}</p>
        </div>
      </div>
    </div>

    <div v-if="livroSelecionado" class="modal-overlay" @click="fecharDetalhes">
      <div class="modal-content" @click.stop>
        <button class="btn-fechar" @click="fecharDetalhes">✕</button>
        
        <div class="modal-body">
          <div class="modal-imagem">
            <img 
              :src="livroSelecionado.imagemUrl || 'https://via.placeholder.com/300x450?text=Sem+Imagem'" 
              :alt="livroSelecionado.titulo"
              @error="substituirImagemPadrao"
            />
          </div>
          
          <div class="modal-info">
            <h2>{{ livroSelecionado.titulo }}</h2>
            <p class="autor-modal">Por {{ livroSelecionado.autor }}</p>
            
            <div class="detalhes">
              <p v-if="livroSelecionado.categoria" class="categoria">
                <strong>Categoria:</strong> {{ livroSelecionado.categoria }}
              </p>
              <p v-if="livroSelecionado.isbn" class="isbn">
                <strong>ISBN:</strong> {{ livroSelecionado.isbn }}
              </p>
              <p class="estoque">
                <strong>Estoque:</strong> 
                <span :class="livroSelecionado.estoque > 0 ? 'disponivel' : 'indisponivel'">
                  {{ livroSelecionado.estoque > 0 ? `${livroSelecionado.estoque} unidades` : 'Indisponível' }}
                </span>
              </p>
            </div>

            <p class="descricao-completa">{{ livroSelecionado.descricao }}</p>
            
            <div class="modal-footer">
              <p class="preco-modal">R$ {{ livroSelecionado.preco?.toFixed(2) }}</p>
              <button 
                class="btn btn-primary" 
                @click="adicionarAoCarrinho(livroSelecionado)"
                :disabled="livroSelecionado.estoque <= 0 || adicionandoAoCarrinho"
              >
                <span v-if="adicionandoAoCarrinho">Adicionando...</span>
                <span v-else-if="livroSelecionado.estoque > 0">Adicionar ao Carrinho</span>
                <span v-else>Indisponível</span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import * as livroService from '../services/livroService';
import { useCarrinhoStore } from '../store/carrinho';
import { useClienteStore } from '../store/cliente';

const router = useRouter();
const carrinhoStore = useCarrinhoStore();
const clienteStore = useClienteStore();

const livros = ref([]);
const livroSelecionado = ref(null);
const loading = ref(false);
const error = ref(null);
const adicionandoAoCarrinho = ref(false);

async function carregarLivros() {
  loading.value = true;
  error.value = null;
  
  try {
    livros.value = await livroService.listarLivros();
  } catch (e) {
    console.error('Erro ao carregar livros:', e);
    error.value = 'Erro ao carregar livros. Tente novamente.';
  } finally {
    loading.value = false;
  }
}

function abrirDetalhes(livro) {
  livroSelecionado.value = livro;
  document.body.style.overflow = 'hidden';
}

function fecharDetalhes() {
  livroSelecionado.value = null;
  document.body.style.overflow = 'auto';
}

async function adicionarAoCarrinho(livro) {
  if (!clienteStore.cliente) {
    alert('Você precisa fazer login para adicionar itens ao carrinho!');
    router.push('/login');
    return;
  }

  adicionandoAoCarrinho.value = true;

  try {
    await carrinhoStore.adicionar(livro, 1);
    
    alert(`✅ ${livro.titulo} foi adicionado ao carrinho!`);
    
    fecharDetalhes();
  } catch (e) {
    console.error('Erro ao adicionar ao carrinho:', e);
    alert('❌ Erro ao adicionar ao carrinho. Tente novamente.');
  } finally {
    adicionandoAoCarrinho.value = false;
  }
}

function substituirImagemPadrao(event) {
  event.target.src = 'https://via.placeholder.com/200x300?text=Sem+Imagem';
}

onMounted(() => {
  carregarLivros();
  
  if (clienteStore.cliente) {
    carrinhoStore.carregar();
  }
});
</script>

<style scoped>
.home {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

h1 {
  font-size: 32px;
  margin-bottom: 30px;
  color: #333;
}

.loading {
  text-align: center;
  padding: 40px;
  color: #666;
  font-size: 18px;
}

.empty {
  text-align: center;
  padding: 60px 20px;
  color: #999;
}

.livros-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 24px;
  margin-top: 20px;
}

.livro-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s, box-shadow 0.2s;
  cursor: pointer;
}

.livro-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
}

.livro-imagem {
  width: 100%;
  height: 300px;
  overflow: hidden;
  background: #f5f5f5;
}

.livro-imagem img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s;
}

.livro-card:hover .livro-imagem img {
  transform: scale(1.05);
}

.livro-info {
  padding: 16px;
}

.livro-card h3 {
  font-size: 18px;
  margin-bottom: 6px;
  color: #333;
  line-height: 1.3;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.autor {
  color: #666;
  font-size: 14px;
  margin-bottom: 12px;
  font-style: italic;
}

.preco {
  font-size: 22px;
  font-weight: bold;
  color: var(--primary, #007bff);
  margin-bottom: 0;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.7);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  padding: 20px;
  animation: fadeIn 0.2s;
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

.modal-content {
  background: white;
  border-radius: 16px;
  max-width: 900px;
  width: 100%;
  max-height: 90vh;
  overflow-y: auto;
  position: relative;
  animation: slideUp 0.3s;
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.btn-fechar {
  position: absolute;
  top: 16px;
  right: 16px;
  background: rgba(0, 0, 0, 0.5);
  color: white;
  border: none;
  width: 36px;
  height: 36px;
  border-radius: 50%;
  font-size: 20px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 10;
  transition: background 0.2s;
}

.btn-fechar:hover {
  background: rgba(0, 0, 0, 0.7);
}

.modal-body {
  display: grid;
  grid-template-columns: 350px 1fr;
  gap: 30px;
  padding: 30px;
}

.modal-imagem {
  width: 100%;
  height: 500px;
  border-radius: 12px;
  overflow: hidden;
  background: #f5f5f5;
}

.modal-imagem img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.modal-info {
  display: flex;
  flex-direction: column;
}

.modal-info h2 {
  font-size: 28px;
  margin-bottom: 8px;
  color: #333;
  line-height: 1.3;
}

.autor-modal {
  font-size: 18px;
  color: #666;
  font-style: italic;
  margin-bottom: 24px;
}

.detalhes {
  background: #f8f9fa;
  padding: 16px;
  border-radius: 8px;
  margin-bottom: 20px;
}

.detalhes p {
  margin: 8px 0;
  font-size: 14px;
  color: #555;
}

.disponivel {
  color: #28a745;
  font-weight: 600;
}

.indisponivel {
  color: #dc3545;
  font-weight: 600;
}

.descricao-completa {
  flex: 1;
  font-size: 15px;
  line-height: 1.7;
  color: #555;
  margin-bottom: 24px;
}

.modal-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 20px;
  padding-top: 20px;
  border-top: 1px solid #eee;
}

.preco-modal {
  font-size: 32px;
  font-weight: bold;
  color: var(--primary, #007bff);
  margin: 0;
}

.btn {
  padding: 14px 28px;
  border: none;
  border-radius: 8px;
  font-weight: 600;
  font-size: 16px;
  cursor: pointer;
  transition: all 0.2s;
}

.btn-primary {
  background: var(--primary, #007bff);
  color: white;
  min-width: 200px;
}

.btn-primary:hover:not(:disabled) {
  opacity: 0.9;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 123, 255, 0.3);
}

.btn-primary:disabled {
  background: #ccc;
  cursor: not-allowed;
  opacity: 0.6;
}

.alert {
  padding: 12px;
  border-radius: 8px;
  margin-bottom: 16px;
}

.alert-error {
  background: #fee;
  color: #c33;
  border: 1px solid #fcc;
}

@media (max-width: 768px) {
  .modal-body {
    grid-template-columns: 1fr;
  }

  .modal-imagem {
    height: 400px;
  }

  .modal-footer {
    flex-direction: column;
    align-items: stretch;
  }

  .btn-primary {
    width: 100%;
  }
}
</style>
