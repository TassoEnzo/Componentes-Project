import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import LivroDetalhes from '../views/LivroDetalhes.vue';
import Carrinho from '../views/Carrinho.vue';
import Checkout from '../views/Checkout.vue';
import Pagamento from '../views/Pagamento.vue';
import PedidoConfirmado from '../views/PedidoConfirmado.vue';
import Login from '../views/Login.vue';
import Cadastro from '../views/Cadastro.vue';

const routes = [
  { path: '/', name: 'home', component: Home },
  { path: '/livro/:id', name: 'livro', component: LivroDetalhes, props: true },
  { path: '/carrinho', name: 'carrinho', component: Carrinho },
  { path: '/checkout', name: 'checkout', component: Checkout },
  { path: '/pagamento', name: 'pagamento', component: Pagamento },
  { path: '/pedido-confirmado', name: 'pedido-confirmado', component: PedidoConfirmado },
  { path: '/login', name: 'login', component: Login },
  { path: '/cadastro', name: 'cadastro', component: Cadastro }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
