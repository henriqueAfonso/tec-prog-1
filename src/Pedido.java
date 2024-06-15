public record Pedido(int id,
                     String detalhesPedido,
                     int idCliente,
                     String dataVenda,
                     Boolean ativo,
                     float valorTotal) {
}
