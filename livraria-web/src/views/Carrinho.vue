<template>
  <div class="page">
    <div class="container">
      <h1 class="title">
        <span class="icon">🛒</span>
        Meu Carrinho
      </h1>

      <div v-if="carrinhoStore.loading" class="loading">
        Carregando carrinho...
      </div>

      <div v-else-if="!carrinhoStore.temItens" class="empty-state">
        <div class="empty-icon">🛍️</div>
        <h2>Seu carrinho está vazio</h2>
        <p>Adicione produtos incríveis para começar suas compras!</p>
        <router-link to="/" class="btn-primary">
          Continuar Comprando
        </router-link>
      </div>

      <div v-else class="carrinho-content">
        <div class="itens-section">
          <div class="section-header">
            <h2>Itens ({{ carrinhoStore.totalItens }})</h2>
          </div>

          <div class="itens-list">
            <div 
              v-for="item in carrinhoStore.itens" 
              :key="item.id" 
              class="item-card"
            >
              <div class="item-image">
                <img 
                  :src="item.livro?.imagemUrl || 'https://via.placeholder.com/120x160?text=Livro'" 
                  :alt="item.livro?.titulo"
                />
              </div>

              <div class="item-info">
                <h3 class="item-title">{{ item.livro?.titulo }}</h3>
                <p class="item-author">{{ item.livro?.autor }}</p>
                <p class="item-price">R$ {{ Number(item.livro?.preco || 0).toFixed(2) }}</p>
              </div>

              <div class="item-quantity">
                <label>Quantidade:</label>
                <div class="quantity-controls">
                  <button 
                    @click="diminuirQuantidade(item)"
                    :disabled="item.quantidade <= 1"
                    class="qty-btn"
                  >
                    -
                  </button>
                  <span class="qty-value">{{ item.quantidade }}</span>
                  <button 
                    @click="aumentarQuantidade(item)"
                    class="qty-btn"
                  >
                    +
                  </button>
                </div>
              </div>

              <div class="item-subtotal">
                <p class="label">Subtotal</p>
                <p class="value">R$ {{ Number(item.subtotal || 0).toFixed(2) }}</p>
              </div>

              <button 
                @click="removerItem(item.id)"
                class="btn-remove"
                title="Remover item"
              >
                🗑️
              </button>
            </div>
          </div>
        </div>

        <div class="resumo-section">
          <div class="resumo-card">
            <h2>Resumo do Pedido</h2>

            <div class="resumo-linha">
              <span>Subtotal ({{ carrinhoStore.totalItens }} itens)</span>
              <span>R$ {{ carrinhoStore.subtotal.toFixed(2) }}</span>
            </div>

            <div class="frete-section">
              <label class="frete-label">Calcular Frete</label>
              <div class="frete-input-group">
                <input 
                  v-model="cepFrete"
                  type="text"
                  placeholder="00000-000"
                  maxlength="9"
                  class="input-cep"
                  @input="formatarCep"
                />
                <button 
                  @click="calcularFrete" 
                  class="btn-calcular-frete"
                  :disabled="carrinhoStore.loadingFrete || cepFrete.length < 8"
                >
                  {{ carrinhoStore.loadingFrete ? '...' : 'OK' }}
                </button>
              </div>
              
              <div v-if="carrinhoStore.frete" class="frete-resultado">
                <span class="frete-info">✓ CEP {{ carrinhoStore.cepFrete }}</span>
                <button @click="limparFrete" class="btn-limpar-frete">✕</button>
              </div>
            </div>

            <div class="resumo-linha">
              <span>Frete</span>
              <span v-if="carrinhoStore.frete" class="frete-valor">
                R$ {{ carrinhoStore.valorFrete.toFixed(2) }}
              </span>
              <span v-else class="frete-calcular">Calcular</span>
            </div>

            <div class="resumo-divider"></div>

            <div class="resumo-total">
              <span>Total</span>
              <span class="total-value">R$ {{ total.toFixed(2) }}</span>
            </div>

            <router-link to="/checkout" class="btn-checkout">
              Finalizar Compra
            </router-link>

            <button @click="continuarComprando" class="btn-continuar">
              Continuar Comprando
            </button>

            <button @click="limparCarrinho" class="btn-limpar">
              Limpar Carrinho
            </button>
          </div>

          <div class="info-card">
            <div class="info-item">
              <span class="info-icon">✓</span>
              <span>Frete grátis para todo Brasil</span>
            </div>
            <div class="info-item">
              <span class="info-icon">✓</span>
              <span>Entrega em até 7 dias úteis</span>
            </div>
            <div class="info-item">
              <span class="info-icon">✓</span>
              <span>Compra 100% segura</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useCarrinhoStore } from '../store/carrinho';

const router = useRouter();
const carrinhoStore = useCarrinhoStore();
const cepFrete = ref('');

const total = computed(() => carrinhoStore.total);

onMounted(() => {
  carrinhoStore.carregar();
});

function formatarCep(event) {
  let valor = event.target.value.replace(/\D/g, '');
  if (valor.length > 5) {
    valor = valor.substring(0, 5) + '-' + valor.substring(5, 8);
  }
  cepFrete.value = valor;
}

async function calcularFrete() {
  try {
    await carrinhoStore.calcularFrete(cepFrete.value);
  } catch (error) {
    alert('Erro ao calcular frete. Verifique o CEP.');
  }
}

function limparFrete() {
  carrinhoStore.limparFrete();
  cepFrete.value = '';
}

function aumentarQuantidade(item) {
  carrinhoStore.atualizarQuantidade(item.id, item.quantidade + 1);
}

function diminuirQuantidade(item) {
  if (item.quantidade > 1) {
    carrinhoStore.atualizarQuantidade(item.id, item.quantidade - 1);
  }
}

async function removerItem(idItem) {
  if (confirm('Deseja realmente remover este item do carrinho?')) {
    try {
      await carrinhoStore.remover(idItem);
    } catch (error) {
      alert('Erro ao remover item. Tente novamente.');
    }
  }
}

async function limparCarrinho() {
  if (confirm('Deseja realmente limpar todo o carrinho?')) {
    try {
      await carrinhoStore.limpar();
    } catch (error) {
      alert('Erro ao limpar carrinho. Tente novamente.');
    }
  }
}

function continuarComprando() {
  router.push('/');
}
</script>

<style scoped>
.page {
  min-height: 100vh;
  background: #f5f7fa;
  padding: 40px 20px;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
}

.title {
  font-size: 32px;
  font-weight: 700;
  margin-bottom: 30px;
  display: flex;
  align-items: center;
  gap: 12px;
}

.icon {
  font-size: 36px;
}

.loading {
  text-align: center;
  padding: 60px 20px;
  font-size: 18px;
  color: #6b7280;
}

.empty-state {
  text-align: center;
  padding: 80px 20px;
  background: white;
  border-radius: 16px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.06);
}

.empty-icon {
  font-size: 80px;
  margin-bottom: 20px;
}

.empty-state h2 {
  font-size: 24px;
  margin-bottom: 12px;
  color: #1f2937;
}

.empty-state p {
  color: #6b7280;
  margin-bottom: 30px;
}

.btn-primary {
  display: inline-block;
  padding: 14px 28px;
  background: #1f6feb;
  color: white;
  text-decoration: none;
  border-radius: 10px;
  font-weight: 600;
  transition: all 0.2s;
}

.btn-primary:hover {
  background: #0d4fa8;
  transform: translateY(-2px);
}

.carrinho-content {
  display: grid;
  grid-template-columns: 1fr 380px;
  gap: 30px;
}

@media (max-width: 968px) {
  .carrinho-content {
    grid-template-columns: 1fr;
  }
}

.itens-section {
  background: white;
  border-radius: 16px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.06);
}

.section-header h2 {
  font-size: 20px;
  margin-bottom: 20px;
}

.itens-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.item-card {
  display: grid;
  grid-template-columns: 100px 1fr auto auto auto;
  gap: 20px;
  align-items: center;
  padding: 20px;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  transition: all 0.2s;
}

.item-card:hover {
  border-color: #1f6feb;
  box-shadow: 0 4px 12px rgba(31, 111, 235, 0.1);
}

@media (max-width: 768px) {
  .item-card {
    grid-template-columns: 1fr;
    text-align: center;
  }
}

.item-image img {
  width: 100px;
  height: 130px;
  object-fit: cover;
  border-radius: 8px;
}

.item-info {
  flex: 1;
}

.item-title {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 6px;
  color: #1f2937;
}

.item-author {
  font-size: 14px;
  color: #6b7280;
  margin-bottom: 8px;
}

.item-price {
  font-size: 16px;
  font-weight: 600;
  color: #1f6feb;
}

.item-quantity label {
  display: block;
  font-size: 12px;
  color: #6b7280;
  margin-bottom: 8px;
}

.quantity-controls {
  display: flex;
  align-items: center;
  gap: 12px;
  background: #f3f4f6;
  padding: 6px 12px;
  border-radius: 8px;
}

.qty-btn {
  width: 28px;
  height: 28px;
  border: none;
  background: white;
  border-radius: 6px;
  cursor: pointer;
  font-size: 16px;
  font-weight: 600;
  transition: all 0.2s;
}

.qty-btn:hover:not(:disabled) {
  background: #1f6feb;
  color: white;
}

.qty-btn:disabled {
  opacity: 0.4;
  cursor: not-allowed;
}

.qty-value {
  font-weight: 600;
  min-width: 30px;
  text-align: center;
}

.item-subtotal .label {
  font-size: 12px;
  color: #6b7280;
  margin-bottom: 4px;
}

.item-subtotal .value {
  font-size: 18px;
  font-weight: 700;
  color: #1f2937;
}

.btn-remove {
  width: 40px;
  height: 40px;
  border: none;
  background: #fee;
  border-radius: 8px;
  cursor: pointer;
  font-size: 18px;
  transition: all 0.2s;
}

.btn-remove:hover {
  background: #e63946;
  transform: scale(1.1);
}

.resumo-section {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.resumo-card {
  background: white;
  border-radius: 16px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.06);
  position: sticky;
  top: 20px;
}

.resumo-card h2 {
  font-size: 20px;
  margin-bottom: 20px;
}

.resumo-linha {
  display: flex;
  justify-content: space-between;
  padding: 12px 0;
  font-size: 15px;
}

.frete-gratis {
  color: #10b981;
  font-weight: 600;
}

.resumo-divider {
  height: 1px;
  background: #e5e7eb;
  margin: 16px 0;
}

.resumo-total {
  display: flex;
  justify-content: space-between;
  padding: 16px 0;
  font-size: 18px;
  font-weight: 700;
}

.total-value {
  color: #1f6feb;
  font-size: 24px;
}

.btn-checkout {
  display: block;
  width: 100%;
  padding: 16px;
  background: #1f6feb;
  color: white;
  text-align: center;
  text-decoration: none;
  border-radius: 10px;
  font-weight: 600;
  font-size: 16px;
  margin-top: 20px;
  transition: all 0.2s;
}

.btn-checkout:hover {
  background: #0d4fa8;
  transform: translateY(-2px);
}

.btn-continuar {
  display: block;
  width: 100%;
  padding: 12px;
  background: white;
  color: #1f6feb;
  border: 2px solid #1f6feb;
  border-radius: 10px;
  font-weight: 600;
  margin-top: 12px;
  cursor: pointer;
  transition: all 0.2s;
}

.btn-continuar:hover {
  background: #f0f7ff;
}

.btn-limpar {
  display: block;
  width: 100%;
  padding: 10px;
  background: transparent;
  color: #6b7280;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  margin-top: 12px;
  cursor: pointer;
  transition: all 0.2s;
}

.btn-limpar:hover {
  color: #e63946;
  background: #fee;
}

.info-card {
  background: #f0f7ff;
  border-radius: 12px;
  padding: 20px;
}

.info-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px 0;
  font-size: 14px;
  color: #1f2937;
}

.info-icon {
  color: #10b981;
  font-weight: 700;
}

.frete-section {
  margin: 16px 0;
  padding: 16px;
  background: #f9fafb;
  border-radius: 10px;
}

.frete-label {
  display: block;
  font-size: 13px;
  font-weight: 500;
  color: #6b7280;
  margin-bottom: 8px;
}

.frete-input-group {
  display: flex;
  gap: 8px;
}

.input-cep {
  flex: 1;
  padding: 10px 12px;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  font-size: 14px;
}

.input-cep:focus {
  outline: none;
  border-color: #1f6feb;
}

.btn-calcular-frete {
  padding: 10px 20px;
  background: #1f6feb;
  color: white;
  border: none;
  border-radius: 8px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s;
}

.btn-calcular-frete:hover:not(:disabled) {
  background: #0d4fa8;
}

.btn-calcular-frete:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.frete-resultado {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 8px;
  padding: 8px;
  background: #ecfdf5;
  border-radius: 6px;
}

.frete-info {
  font-size: 12px;
  color: #059669;
  font-weight: 500;
}

.btn-limpar-frete {
  background: transparent;
  border: none;
  color: #6b7280;
  cursor: pointer;
  font-size: 14px;
  padding: 4px;
}

.btn-limpar-frete:hover {
  color: #e63946;
}

.frete-valor {
  color: #1f6feb;
  font-weight: 600;
}

.frete-calcular {
  color: #6b7280;
  font-size: 13px;
}
</style>