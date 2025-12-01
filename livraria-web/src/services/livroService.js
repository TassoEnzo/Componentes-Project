import axios from 'axios';

const API_URL = 'http://localhost:8081/livros';

const api = axios.create({
  baseURL: API_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
});

export async function listarLivros() {
  try {
    const response = await api.get('');
    return response.data;
  } catch (error) {
    console.error('Erro ao listar livros:', error);
    throw error.response?.data || error.message;
  }
}

export async function buscarLivro(id) {
  try {
    const response = await api.get(`/${id}`);
    return response.data;
  } catch (error) {
    console.error('Erro ao buscar livro:', error);
    throw error.response?.data || error.message;
  }
}

export async function cadastrarLivro(dados) {
  try {
    const response = await api.post('', dados);
    return response.data;
  } catch (error) {
    console.error('Erro ao cadastrar livro:', error);
    throw error.response?.data || error.message;
  }
}

export async function atualizarLivro(id, dados) {
  try {
    const response = await api.put(`/${id}`, dados);
    return response.data;
  } catch (error) {
    console.error('Erro ao atualizar livro:', error);
    throw error.response?.data || error.message;
  }
}

export async function deletarLivro(id) {
  try {
    await api.delete(`/${id}`);
  } catch (error) {
    console.error('Erro ao deletar livro:', error);
    throw error.response?.data || error.message;
  }
}
