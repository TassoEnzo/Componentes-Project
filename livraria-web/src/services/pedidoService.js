import api from "./api";

export async function criarPedido(idCliente) {
  const res = await api.post(`/gateway/pedidos/criar/${idCliente}`);
  return res.data;
}

export async function listarPedidos(idCliente) {
  const res = await api.get(`/gateway/pedidos/cliente/${idCliente}`);
  return res.data;
}

export async function buscarPedido(idPedido) {
  const res = await api.get(`/gateway/pedidos/${idPedido}`);
  return res.data;
}

export async function atualizarStatus(idPedido, novoStatus) {
  const res = await api.put(`/gateway/pedidos/${idPedido}/status`, { novoStatus });
  return res.data;
}
