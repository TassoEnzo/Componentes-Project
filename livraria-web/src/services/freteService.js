import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8086/frete',
  timeout: 10000
});

export const calcularFrete = async (cep) => {
  try {
    const cepLimpo = cep.replace(/\D/g, '');
    const response = await api.get(`/${cepLimpo}`);
    return response.data;
  } catch (error) {
    console.error('Erro ao calcular frete:', error);
    throw error;
  }
};