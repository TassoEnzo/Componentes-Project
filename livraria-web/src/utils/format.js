export function formatPrice(value) {
  if (value === null || value === undefined) return "R$ 0,00";
  return value.toLocaleString("pt-BR", {
    style: "currency",
    currency: "BRL"
  });
}

export function formatDate(date) {
  if (!date) return "";
  const d = new Date(date);
  return d.toLocaleDateString("pt-BR");
}

export function formatStatus(status) {
  if (!status) return "Indefinido";

  const map = {
    CRIADO: "Criado",
    PROCESSANDO: "Processando",
    AGUARDANDO_PAGAMENTO: "Aguardando Pagamento",
    PAGO: "Pago",
    ENVIADO: "Enviado",
    ENTREGUE: "Entregue",
    CANCELADO: "Cancelado"
  };

  return map[status] || status;
}

export function formatQty(qtd) {
  if (!qtd) return "0 unidades";
  return qtd === 1 ? "1 unidade" : `${qtd} unidades`;
}
