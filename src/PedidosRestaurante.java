import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PedidosRestaurante {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tela de Pedidos");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            // Painel principal
            JPanel panel = new JPanel(new BorderLayout());

            // Tabela de Produtos
            String[] colunas = {"ID", "Nome do Produto", "Preço"};
            Object[][] dados = {
                    {"1", "Produto A", "10.00"},
                    {"2", "Produto B", "20.00"},
                    {"3", "Produto C", "30.00"}
                    // Os dados podem ser recuperados do banco de dados
            };

            DefaultTableModel model = new DefaultTableModel(dados, colunas);
            JTable tabelaProdutos = new JTable(model);
            tabelaProdutos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            // ScrollPane para tabela
            JScrollPane scrollPane = new JScrollPane(tabelaProdutos);
            panel.add(scrollPane, BorderLayout.CENTER);

            // ComboBox para seleção de clientes
            JPanel panelClientes = new JPanel();
            panelClientes.add(new JLabel("Cliente:"));
            String[] clientes = {"Cliente 1", "Cliente 2", "Cliente 3"}; // Dados recuperados do banco de dados
            JComboBox<String> comboBoxClientes = new JComboBox<>(clientes);
            panelClientes.add(comboBoxClientes);
            panel.add(panelClientes, BorderLayout.NORTH);

            // Botão para efetuar pedido
            JButton btnEfetuarPedido = new JButton("Efetuar Pedido");
            btnEfetuarPedido.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Lógica para efetuar o pedido
                    int[] linhasSelecionadas = tabelaProdutos.getSelectedRows();
                    String clienteSelecionado = (String) comboBoxClientes.getSelectedItem();

                    // Exemplo de como obter os dados selecionados
                    for (int i : linhasSelecionadas) {
                        String idProduto = (String) tabelaProdutos.getValueAt(i, 0);
                        String nomeProduto = (String) tabelaProdutos.getValueAt(i, 1);
                        String precoProduto = (String) tabelaProdutos.getValueAt(i, 2);
                        System.out.println("Produto Selecionado: " + nomeProduto + " - " + precoProduto);
                    }
                    System.out.println("Cliente Selecionado: " + clienteSelecionado);
                }
            });
            panel.add(btnEfetuarPedido, BorderLayout.SOUTH);

            frame.add(panel);
            frame.setVisible(true);
        });
    }
}