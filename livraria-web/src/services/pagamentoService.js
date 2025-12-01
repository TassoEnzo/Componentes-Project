import api from "./api";

export async function pagarViaPix(idPedido, chavePix) {
  const res = await api.post(`/gateway/pagamento/pix`, {
    idPedido,
    chavePix
  });
  return res.data;
}

export async function pagarViaCartao(idPedido, dadosCartao) {
  const res = await api.post(`/gateway/pagamento/cartao`, {
    idPedido,
    ...dadosCartao
  });
  return res.data;
}

export async function buscarPagamento(idPagamento) {
  const res = await api.get(`/gateway/pagamento/${idPagamento}`);
  return res.data;
}
