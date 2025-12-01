import axios from 'axios';

const API_URL = 'http://localhost:8082/clientes';

const api = axios.create({
  baseURL: API_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
});

export async function cadastrarCliente(dados) {
  try {
    const response = await api.post('', dados);
    return response.data;
  } catch (error) {
    console.error('Erro ao cadastrar cliente:', error);
    throw error.response?.data || error.message;
  }
}

export async function loginCliente(credentials) {
  try {
    const response = await api.post('/login', credentials);
    return response.data;
  } catch (error) {
    console.error('Erro ao fazer login:', error);
    throw error.response?.data || error.message;
  }
}

export async function buscarCliente(id) {
  try {
    const response = await api.get(`/${id}`);
    return response.data;
  } catch (error) {
    console.error('Erro ao buscar cliente:', error);
    throw error.response?.data || error.message;
  }
}

export async function atualizarCliente(id, dados) {
  try {
    const response = await api.put(`/${id}`, dados);
    return response.data;
  } catch (error) {
    console.error('Erro ao atualizar cliente:', error);
    throw error.response?.data || error.message;
  }
}

export async function listarClientes() {
  try {
    const response = await api.get('/');
    return response.data;
  } catch (error) {
    console.error('Erro ao listar clientes:', error);
    throw error.response?.data || error.message;
  }
}