import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaFilaDePedidos extends JFrame {
    private JTable activeOrdersTable;
    private JButton completeOrderButton;
    private Timer timer;

    public TelaFilaDePedidos() {
        setTitle("Fila de Pedidos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        initializeActiveOrdersTable();

        completeOrderButton = new JButton("Completar Pedido");
        completeOrderButton.addActionListener(e -> completeSelectedOrder());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(new JScrollPane(activeOrdersTable), BorderLayout.CENTER);
        mainPanel.add(completeOrderButton, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        timer = new Timer(5000, e -> fillActiveOrdersTable());
        timer.start();

        fillActiveOrdersTable();
    }

    private void initializeActiveOrdersTable() {
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"ID", "Detalhes", "Valor Total", "Data do Pedido", "ID Cliente"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        activeOrdersTable = new JTable(tableModel);
        activeOrdersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void fillActiveOrdersTable() {
        DefaultTableModel tableModel = (DefaultTableModel) activeOrdersTable.getModel();
        tableModel.setRowCount(0);
        final List<PedidoWithId> activeOrders = DatabaseUtils.findAllActiveOrders();

        activeOrders.forEach(order ->
                tableModel.addRow(new Object[]{
                        order.id(),
                        order.detalhesPedido(),
                        order.valorTotal(),
                        order.dataVenda(),
                        order.idCliente()}));
    }

    private void completeSelectedOrder() {
        int selectedRow = activeOrdersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, selecione um pedido para completar.",
                    "Nenhum Pedido Selecionado",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            int pedidoId = Integer.parseInt(activeOrdersTable.getValueAt(selectedRow, 0).toString());
            String detalhesPedido = activeOrdersTable.getValueAt(selectedRow, 1).toString();
            float valorTotal = Float.parseFloat(activeOrdersTable.getValueAt(selectedRow, 2).toString());
            String dataPedido = activeOrdersTable.getValueAt(selectedRow, 3).toString();
            int clienteId = Integer.parseInt(activeOrdersTable.getValueAt(selectedRow, 4).toString());

            DatabaseUtils.completeOrder(pedidoId);

            JOptionPane.showMessageDialog(this,
                    "Pedido Selecionado:\n" +
                            "ID: " + pedidoId + "\n" +
                            "Detalhes: " + detalhesPedido + "\n" +
                            "Valor Total: " + valorTotal + "\n" +
                            "Data do Pedido: " + dataPedido + "\n" +
                            "ID Cliente: " + clienteId,
                    "Pedido Selecionado",
                    JOptionPane.INFORMATION_MESSAGE);

            DefaultTableModel tableModel = (DefaultTableModel) activeOrdersTable.getModel();
            tableModel.removeRow(selectedRow);
        }
    }
}
