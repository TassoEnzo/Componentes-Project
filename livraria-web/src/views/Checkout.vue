<template>
  <div class="page">
    <div class="container">
      <h1 class="title">
        <span class="icon">💳</span>
        Finalizar Compra
      </h1>

      <div class="checkout-grid">
        <div class="form-section">
          <div class="card">
            <h2 class="card-title">📦 Dados de Entrega</h2>
            
            <div class="form-group">
              <label>CEP *</label>
              <div class="cep-input-group">
                <input 
                  v-model="form.cep" 
                  type="text" 
                  placeholder="00000-000"
                  maxlength="9"
                  @input="formatarCep"
                  @blur="buscarCep"
                />
                <span v-if="buscandoCep" class="loading-cep">🔄</span>
                <span v-if="cepEncontrado" class="cep-ok">✓</span>
              </div>
              <small v-if="erroCep" class="erro-msg">{{ erroCep }}</small>
            </div>

            <div class="form-row">
              <div class="form-group">
                <label>Estado *</label>
                <input 
                  v-model="form.estado" 
                  type="text" 
                  placeholder="SP" 
                  maxlength="2"
                  readonly
                  class="readonly-field"
                />
              </div>
              <div class="form-group">
                <label>Cidade *</label>
                <input 
                  v-model="form.cidade" 
                  type="text" 
                  placeholder="São Paulo"
                  readonly
                  class="readonly-field"
                />
              </div>
            </div>

            <div class="form-group">
              <label>Endereço *</label>
              <input 
                v-model="form.endereco" 
                type="text" 
                placeholder="Rua, Avenida..."
                :readonly="form.endereco && enderecoAutomatico"
                :class="{ 'readonly-field': form.endereco && enderecoAutomatico }"
              />
              <small v-if="!form.endereco && form.cep.length === 9" class="info-msg">
                Endereço não encontrado. Digite manualmente.
              </small>
            </div>

            <div class="form-row">
              <div class="form-group" style="flex: 0 0 30%">
                <label>Número *</label>
                <input v-model="form.numero" type="text" placeholder="123" />
              </div>
              <div class="form-group">
                <label>Complemento</label>
                <input v-model="form.complemento" type="text" placeholder="Apto, Bloco..." />
              </div>
            </div>

            <div class="form-group">
              <label>Bairro *</label>
              <input 
                v-model="form.bairro" 
                type="text" 
                placeholder="Centro"
                :readonly="form.bairro && enderecoAutomatico"
                :class="{ 'readonly-field': form.bairro && enderecoAutomatico }"
              />
            </div>

            <div v-if="carrinhoStore.frete && form.cep" class="frete-info-box">
              <p class="frete-calculado">
                ✓ Frete calculado: <strong>R$ {{ carrinhoStore.valorFrete.toFixed(2) }}</strong>
              </p>
            </div>
          </div>

          <div class="card">
            <h2 class="card-title">💳 Dados de Pagamento</h2>

            <div class="payment-methods">
              <label class="payment-option" :class="{ selected: form.metodoPagamento === 'credito' }">
                <input 
                  type="radio" 
                  v-model="form.metodoPagamento" 
                  value="credito"
                />
                <span class="payment-label">
                  <span class="payment-icon">💳</span>
                  Cartão de Crédito
                </span>
              </label>

              <label class="payment-option" :class="{ selected: form.metodoPagamento === 'debito' }">
                <input 
                  type="radio" 
                  v-model="form.metodoPagamento" 
                  value="debito"
                />
                <span class="payment-label">
                  <span class="payment-icon">💵</span>
                  Cartão de Débito
                </span>
              </label>

              <label class="payment-option" :class="{ selected: form.metodoPagamento === 'pix' }">
                <input 
                  type="radio" 
                  v-model="form.metodoPagamento" 
                  value="pix"
                />
                <span class="payment-label">
                  <span class="payment-icon">📱</span>
                  PIX
                </span>
              </label>

              <label class="payment-option" :class="{ selected: form.metodoPagamento === 'boleto' }">
                <input 
                  type="radio" 
                  v-model="form.metodoPagamento" 
                  value="boleto"
                />
                <span class="payment-label">
                  <span class="payment-icon">🧾</span>
                  Boleto
                </span>
              </label>
            </div>

            <div v-if="form.metodoPagamento === 'credito' || form.metodoPagamento === 'debito'" class="card-fields">
              <div class="form-group">
                <label>Número do Cartão *</label>
                <input 
                  v-model="form.numeroCartao" 
                  type="text" 
                  placeholder="0000 0000 0000 0000"
                  maxlength="19"
                  @input="formatarCartao"
                />
              </div>

              <div class="form-group">
                <label>Nome no Cartão *</label>
                <input 
                  v-model="form.nomeCartao" 
                  type="text" 
                  placeholder="Como está no cartão"
                  style="text-transform: uppercase"
                />
              </div>

              <div class="form-row">
                <div class="form-group">
                  <label>Validade *</label>
                  <input 
                    v-model="form.validade" 
                    type="text" 
                    placeholder="MM/AA"
                    maxlength="5"
                    @input="formatarValidade"
                  />
                </div>
                <div class="form-group">
                  <label>CVV *</label>
                  <input 
                    v-model="form.cvv" 
                    type="text" 
                    placeholder="000"
                    maxlength="3"
                  />
                </div>
              </div>

              <div v-if="form.metodoPagamento === 'credito'" class="form-group">
                <label>Parcelas *</label>
                <select v-model="form.parcelas">
                  <option value="1">1x de R$ {{ carrinhoStore.total.toFixed(2) }} sem juros</option>
                  <option value="2">2x de R$ {{ (carrinhoStore.total / 2).toFixed(2) }} sem juros</option>
                  <option value="3">3x de R$ {{ (carrinhoStore.total / 3).toFixed(2) }} sem juros</option>
                  <option value="4">4x de R$ {{ (carrinhoStore.total / 4).toFixed(2) }} sem juros</option>
                  <option value="5">5x de R$ {{ (carrinhoStore.total / 5).toFixed(2) }} sem juros</option>
                  <option value="6">6x de R$ {{ (carrinhoStore.total / 6).toFixed(2) }} sem juros</option>
                </select>
              </div>
            </div>

            <div v-if="form.metodoPagamento === 'pix'" class="pix-info">
              <p>📱 Após confirmar o pedido, você receberá um QR Code para pagamento via PIX.</p>
              <p class="small">O pedido será processado após a confirmação do pagamento.</p>
            </div>

            <div v-if="form.metodoPagamento === 'boleto'" class="boleto-info">
              <p>🧾 O boleto será gerado após a confirmação do pedido.</p>
              <p class="small">Prazo de pagamento: 3 dias úteis.</p>
            </div>
          </div>
        </div>

        <div class="resumo-section">
          <div class="card sticky">
            <h2 class="card-title">📋 Resumo do Pedido</h2>

            <div class="itens-resumo">
              <div v-for="item in carrinhoStore.itens" :key="item.id" class="item-resumo">
                <img 
                  :src="item.livro?.imagemUrl || 'https://via.placeholder.com/60x80?text=Livro'" 
                  :alt="item.livro?.titulo"
                />
                <div class="item-resumo-info">
                  <p class="item-resumo-title">{{ item.livro?.titulo }}</p>
                  <p class="item-resumo-qty">Qtd: {{ item.quantidade }}</p>
                </div>
                <p class="item-resumo-price">R$ {{ item.subtotal.toFixed(2) }}</p>
              </div>
            </div>

            <div class="resumo-divider"></div>

            <div class="resumo-linha">
              <span>Subtotal</span>
              <span>R$ {{ carrinhoStore.subtotal.toFixed(2) }}</span>
            </div>

            <div class="resumo-linha">
              <span>Frete</span>
              <span v-if="carrinhoStore.frete" class="frete-valor">
                R$ {{ carrinhoStore.valorFrete.toFixed(2) }}
              </span>
              <span v-else class="frete-pendente">Informe o CEP</span>
            </div>

            <div class="resumo-divider"></div>

            <div class="resumo-total">
              <span>Total</span>
              <span class="total-value">R$ {{ carrinhoStore.total.toFixed(2) }}</span>
            </div>

            <button 
              @click="finalizarCompra" 
              class="btn-finalizar"
              :disabled="processando || !formularioValido"
            >
              {{ processando ? 'Processando...' : 'Confirmar Pedido' }}
            </button>

            <router-link to="/carrinho" class="btn-voltar">
              ← Voltar ao Carrinho
            </router-link>

            <div class="seguranca-info">
              <p>🔒 Compra 100% segura e protegida</p>
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
import { useClienteStore } from '../store/cliente';

const router = useRouter();
const carrinhoStore = useCarrinhoStore();
const clienteStore = useClienteStore();

const form = ref({
  cep: '',
  estado: '',
  cidade: '',
  endereco: '',
  numero: '',
  complemento: '',
  bairro: '',
  metodoPagamento: 'credito',
  numeroCartao: '',
  nomeCartao: '',
  validade: '',
  cvv: '',
  parcelas: '1'
});

const processando = ref(false);
const buscandoCep = ref(false);
const cepEncontrado = ref(false);
const erroCep = ref('');
const enderecoAutomatico = ref(false);

const formularioValido = computed(() => {
  const enderecoValido = 
    form.value.cep && 
    form.value.estado && 
    form.value.cidade && 
    form.value.endereco && 
    form.value.numero && 
    form.value.bairro;

  if (!enderecoValido) return false;

  if (!carrinhoStore.frete) return false;

  if (form.value.metodoPagamento === 'credito' || form.value.metodoPagamento === 'debito') {
    return form.value.numeroCartao && form.value.nomeCartao && form.value.validade && form.value.cvv;
  }

  return true;
});

onMounted(async () => {
  if (!clienteStore.estaLogado) {
    alert('Você precisa estar logado para finalizar a compra');
    router.push('/login');
    return;
  }

  if (!carrinhoStore.temItens) {
    alert('Seu carrinho está vazio');
    router.push('/');
    return;
  }

  await carrinhoStore.carregar();

  if (carrinhoStore.cepFrete) {
    form.value.cep = carrinhoStore.cepFrete;
    await buscarCep();
  }
});

function formatarCep(event) {
  let valor = event.target.value.replace(/\D/g, '');
  if (valor.length > 5) {
    valor = valor.substring(0, 5) + '-' + valor.substring(5, 8);
  }
  form.value.cep = valor;
  
  if (erroCep.value) {
    erroCep.value = '';
    cepEncontrado.value = false;
  }
}

function formatarCartao(event) {
  let valor = event.target.value.replace(/\D/g, '');
  if (valor.length > 0) {
    valor = valor.match(/.{1,4}/g)?.join(' ') || valor;
  }
  form.value.numeroCartao = valor;
}

function formatarValidade(event) {
  let valor = event.target.value.replace(/\D/g, '');
  if (valor.length > 2) {
    valor = valor.substring(0, 2) + '/' + valor.substring(2, 4);
  }
  form.value.validade = valor;
}

async function buscarCep() {
  const cep = form.value.cep.replace(/\D/g, '');
  
  if (cep.length !== 8) {
    erroCep.value = 'CEP deve ter 8 dígitos';
    return;
  }

  buscandoCep.value = true;
  erroCep.value = '';
  cepEncontrado.value = false;
  enderecoAutomatico.value = false;

  try {
    const response = await fetch(`https://viacep.com.br/ws/${cep}/json/`);
    const data = await response.json();

    if (data.erro) {
      erroCep.value = 'CEP não encontrado';
      return;
    }

    form.value.estado = data.uf;
    form.value.cidade = data.localidade;
    form.value.bairro = data.bairro || '';
    form.value.endereco = data.logradouro || '';
    
    enderecoAutomatico.value = !!data.logradouro;
    cepEncontrado.value = true;

    await carrinhoStore.calcularFrete(form.value.cep);

  } catch (error) {
    console.error('Erro ao buscar CEP:', error);
    erroCep.value = 'Erro ao buscar CEP. Tente novamente.';
  } finally {
    buscandoCep.value = false;
  }
}

async function finalizarCompra() {
  if (!formularioValido.value) {
    alert('Preencha todos os campos obrigatórios');
    return;
  }

  processando.value = true;

  try {
    await new Promise(resolve => setTimeout(resolve, 2000));

    await carrinhoStore.limpar();

    alert('🎉 Pedido realizado com sucesso! Você receberá um email com os detalhes.');
    router.push('/');
  } catch (error) {
    console.error('Erro ao processar pedido:', error);
    alert('Erro ao processar pedido. Tente novamente.');
  } finally {
    processando.value = false;
  }
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

.checkout-grid {
  display: grid;
  grid-template-columns: 1fr 400px;
  gap: 30px;
}

@media (max-width: 968px) {
  .checkout-grid {
    grid-template-columns: 1fr;
  }
}

.card {
  background: white;
  border-radius: 16px;
  padding: 28px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.06);
  margin-bottom: 20px;
}

.card.sticky {
  position: sticky;
  top: 20px;
}

.card-title {
  font-size: 20px;
  font-weight: 600;
  margin-bottom: 24px;
  display: flex;
  align-items: center;
  gap: 8px;
}

.form-group {
  margin-bottom: 20px;
  flex: 1;
}

.form-group label {
  display: block;
  font-size: 14px;
  font-weight: 500;
  color: #374151;
  margin-bottom: 8px;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 12px;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  font-size: 15px;
  transition: all 0.2s;
}

.form-group input:focus,
.form-group select:focus {
  outline: none;
  border-color: #1f6feb;
  box-shadow: 0 0 0 3px rgba(31, 111, 235, 0.1);
}

.readonly-field {
  background: #f9fafb !important;
  cursor: not-allowed;
}

.cep-input-group {
  position: relative;
}

.loading-cep,
.cep-ok {
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 16px;
}

.cep-ok {
  color: #10b981;
}

.erro-msg {
  color: #dc2626;
  font-size: 12px;
  margin-top: 4px;
  display: block;
}

.info-msg {
  color: #6b7280;
  font-size: 12px;
  margin-top: 4px;
  display: block;
}

.frete-info-box {
  background: #ecfdf5;
  padding: 12px;
  border-radius: 8px;
  margin-top: 16px;
}

.frete-calculado {
  color: #059669;
  font-size: 14px;
  margin: 0;
}

.form-row {
  display: flex;
  gap: 16px;
}

.payment-methods {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 12px;
  margin-bottom: 24px;
}

.payment-option {
  display: flex;
  align-items: center;
  padding: 16px;
  border: 2px solid #e5e7eb;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.2s;
}

.payment-option:hover {
  border-color: #1f6feb;
  background: #f0f7ff;
}

.payment-option.selected {
  border-color: #1f6feb;
  background: #f0f7ff;
}

.payment-option input[type="radio"] {
  margin-right: 12px;
}

.payment-label {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 15px;
}

.payment-icon {
  font-size: 20px;
}

.card-fields {
  margin-top: 20px;
}

.pix-info,
.boleto-info {
  background: #f0f7ff;
  padding: 16px;
  border-radius: 10px;
  margin-top: 20px;
}

.pix-info p,
.boleto-info p {
  margin-bottom: 8px;
}

.small {
  font-size: 13px;
  color: #6b7280;
}

.itens-resumo {
  max-height: 300px;
  overflow-y: auto;
  margin-bottom: 20px;
}

.item-resumo {
  display: flex;
  gap: 12px;
  padding: 12px 0;
  border-bottom: 1px solid #f3f4f6;
}

.item-resumo:last-child {
  border-bottom: none;
}

.item-resumo img {
  width: 60px;
  height: 80px;
  object-fit: cover;
  border-radius: 6px;
}

.item-resumo-info {
  flex: 1;
}

.item-resumo-title {
  font-size: 14px;
  font-weight: 500;
  margin-bottom: 4px;
  color: #1f2937;
}

.item-resumo-qty {
  font-size: 12px;
  color: #6b7280;
}

.item-resumo-price {
  font-size: 15px;
  font-weight: 600;
  color: #1f2937;
}

.resumo-divider {
  height: 1px;
  background: #e5e7eb;
  margin: 16px 0;
}

.resumo-linha {
  display: flex;
  justify-content: space-between;
  padding: 10px 0;
  font-size: 15px;
}

.frete-valor {
  color: #1f6feb;
  font-weight: 600;
}

.frete-pendente {
  color: #f59e0b;
  font-size: 13px;
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
  font-size: 26px;
}

.btn-finalizar {
  width: 100%;
  padding: 16px;
  background: #10b981;
  color: white;
  border: none;
  border-radius: 10px;
  font-weight: 600;
  font-size: 16px;
  cursor: pointer;
  margin-top: 20px;
  transition: all 0.2s;
}

.btn-finalizar:hover:not(:disabled) {
  background: #059669;
  transform: translateY(-2px);
}

.btn-finalizar:disabled {
  background: #9ca3af;
  cursor: not-allowed;
  transform: none;
}

.btn-voltar {
  display: block;
  text-align: center;
  padding: 12px;
  color: #6b7280;
  text-decoration: none;
  margin-top: 12px;
  border-radius: 8px;
  transition: all 0.2s;
}

.btn-voltar:hover {
  background: #f3f4f6;
  color: #1f2937;
}

.seguranca-info {
  text-align: center;
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid #e5e7eb;
}

.seguranca-info p {
  font-size: 13px;
  color: #10b981;
  font-weight: 500;
}
</style>