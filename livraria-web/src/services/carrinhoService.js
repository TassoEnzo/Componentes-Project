import axios from 'axios';

const API_URL = 'http://localhost:8083/carrinho';

const api = axios.create({
  baseURL: API_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
});

export async function obterCarrinho(idCliente) {
  try {
    const response = await api.get(`/${idCliente}`);
    return response.data;
  } catch (error) {
    console.error('Erro ao obter carrinho:', error);
    throw error.response?.data || error.message;
  }
}

export async function adicionarItem(idCliente, item) {
  try {
    const response = await api.post(
      `/${idCliente}/adicionar/${item.idLivro}`,
      null,  
      { params: { quantidade: item.quantidade } }
    );
    return response.data;
  } catch (error) {
    console.error('Erro ao adicionar item:', error);
    throw error.response?.data || error.message;
  }
}

export async function removerItem(idCliente, idItem) {
  try {
    await api.delete(`/${idCliente}/remover/${idItem}`);
  } catch (error) {
    console.error('Erro ao remover item:', error);
    throw error.response?.data || error.message;
  }
}

export async function atualizarQuantidade(idCliente, idItem, quantidade) {
  try {
    const carrinho = await obterCarrinho(idCliente);
    const item = carrinho.itens.find(i => i.id === idItem);
    
    if (!item) {
      throw new Error('Item não encontrado no carrinho');
    }
    
    await api.delete(`/${idCliente}/remover/${idItem}`);
    
    const response = await api.post(
      `/${idCliente}/adicionar/${item.livro.id}`,
      null,
      { params: { quantidade } }
    );
    
    return response.data;
  } catch (error) {
    console.error('Erro ao atualizar quantidade:', error);
    throw error.response?.data || error.message;
  }
}

export async function limparCarrinho(idCliente) {
  try {
    await api.delete(`/${idCliente}/limpar`);
  } catch (error) {
    console.error('Erro ao limpar carrinho:', error);
    throw error.response?.data || error.message;
  }
}