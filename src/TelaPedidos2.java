import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TelaPedidos2 extends JFrame {
    private JPanel mainPanel;
    private JPanel productsPanel;
    private JPanel customersPanel;
    private JPanel selectedProductsPanel;
    private JPanel finishOrderPanel;

    private JTable productsTable;
    private JTable selectedProductsTable;
    private JTable customersTable;

    private JButton orderButton;
    private JTextField totalAmountTextField;

    private JTextField searchedCustomerTextField;
    private JTextField searchedProductsTextField;

    private JLabel productsLabel;
    private JLabel customersLabel;
    private JLabel selectedProductsLabel;
    private JLabel totalAmountLabel;

    private JButton addProdcutsButton;

    private DefaultTableModel customersDataTable;
    private DefaultTableModel productsDataTable;
    private DefaultTableModel selectedProductsDataTable;

    private List<Cliente> allCustomers;
    private List<Produto> allProducts;

    public TelaPedidos2() {
        mainPanel = new JPanel(new GridLayout(2, 2));

        initializeProductsPanel();
        initializeCustomersPanel();
        initializeSelectedProductsPanel();
        initializeFinishOrderPanel();

        mainPanel.add(productsPanel);
        mainPanel.add(customersPanel);
        mainPanel.add(selectedProductsPanel);
        mainPanel.add(finishOrderPanel);

        setContentPane(mainPanel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(final WindowEvent e) {
                loadCustomersAndProducts();
            }
        });

        setSize(850, 550);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void initializeProductsPanel() {
        productsPanel = new JPanel();
        productsPanel.setLayout(new BoxLayout(productsPanel, BoxLayout.Y_AXIS));

        productsLabel = new JLabel("Produtos");

        searchedProductsTextField = new JTextField();
        searchedProductsTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent e) {
                filterProducts();
            }

            @Override
            public void removeUpdate(final DocumentEvent e) {
                filterProducts();
            }

            @Override
            public void changedUpdate(final DocumentEvent e) {
                filterProducts();
            }
        });

        productsDataTable = new DefaultTableModel(new Object[]{"ID", "Nome", "Descrição", "Valor"},0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productsTable = new JTable(productsDataTable);
        productsTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        addProdcutsButton = new JButton("Adicionar Produtos");
        addProdcutsButton.addActionListener(e -> addProdcuts());

        productsPanel.add(productsLabel);
        productsPanel.add(searchedProductsTextField);
        productsPanel.add(new JScrollPane(productsTable));
        productsPanel.add(addProdcutsButton);
    }

    private void initializeCustomersPanel() {
        customersPanel = new JPanel();
        customersPanel.setLayout(new BoxLayout(customersPanel, BoxLayout.Y_AXIS));

        customersLabel = new JLabel("Clientes");

        searchedCustomerTextField = new JTextField();
        searchedCustomerTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent e) {
                filterCustomers();
            }

            @Override
            public void removeUpdate(final DocumentEvent e) {
                filterCustomers();
            }

            @Override
            public void changedUpdate(final DocumentEvent e) {
                filterCustomers();
            }
        });

        customersDataTable = new DefaultTableModel(new Object[]{"ID", "Nome", "Telefone"},0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        customersTable = new JTable(customersDataTable);
        customersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        customersPanel.add(customersLabel);
        customersPanel.add(searchedCustomerTextField);
        customersPanel.add(new JScrollPane(customersTable));
    }

    private void initializeSelectedProductsPanel() {
        selectedProductsPanel = new JPanel();
        selectedProductsPanel.setLayout(new BoxLayout(selectedProductsPanel, BoxLayout.Y_AXIS));

        selectedProductsLabel = new JLabel("Produtos selecionados");

        selectedProductsDataTable = new DefaultTableModel(new Object[]{"ID", "Nome", "Valor", "Quantidade"},0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        selectedProductsTable = new JTable(selectedProductsDataTable);

        selectedProductsPanel.add(selectedProductsLabel);
        selectedProductsPanel.add(new JScrollPane(selectedProductsTable));
    }

    private void initializeFinishOrderPanel() {
        finishOrderPanel = new JPanel();
        finishOrderPanel.setLayout(new BoxLayout(finishOrderPanel, BoxLayout.Y_AXIS));

        totalAmountLabel = new JLabel("Valor total do pedido");

        orderButton = new JButton("Fazer pedido");
        orderButton.addActionListener(e -> createOrder());

        totalAmountTextField = new JTextField();
        totalAmountTextField.setEditable(false);

        finishOrderPanel.add(totalAmountLabel);
        finishOrderPanel.add(totalAmountTextField);
        finishOrderPanel.add(orderButton);

    }

    private void addProdcuts() {
        int[] selectedRows = productsTable.getSelectedRows();
        for (int row : selectedRows) {
            Object id = productsDataTable.getValueAt(row, 0);
            Object nome = productsDataTable.getValueAt(row, 1);
            Object valor = productsDataTable.getValueAt(row, 3);

            boolean alreadyAdded = false;
            for (int i = 0; i < selectedProductsDataTable.getRowCount(); i++) {
                if (selectedProductsDataTable.getValueAt(i, 0).equals(id)) {
                    alreadyAdded = true;
                    break;
                }
            }

            if (!alreadyAdded) {
                selectedProductsDataTable.addRow(new Object[]{id, nome, valor, 1}); // Adiciona o produto com quantidade 1
            }
        }
    }


    //TODO rever esse método e deixar ele bonito e bem escrito
    private void createOrder() {
        int selectedCustomerRow = customersTable.getSelectedRow();
        if (selectedCustomerRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um cliente.");
            return;
        }

        int clienteId = (int) customersDataTable.getValueAt(selectedCustomerRow, 0);
        StringBuilder detalhesBuilder = new StringBuilder();
        float valorTotal = 0;

        for (int i = 0; i < selectedProductsDataTable.getRowCount(); i++) {
            int quantidade = (int) selectedProductsDataTable.getValueAt(i, 3);
            String nomeProduto = (String) selectedProductsDataTable.getValueAt(i, 1);
            float valorProduto = (float) selectedProductsDataTable.getValueAt(i, 2);

            detalhesBuilder.append(quantidade)
                    .append(" X ")
                    .append(nomeProduto)
                    .append(", ");

            valorTotal += quantidade * valorProduto;
        }

        if (detalhesBuilder.length() > 0) {
            detalhesBuilder.setLength(detalhesBuilder.length() - 2); // Remove the trailing comma and space
        }

        String detalhes = detalhesBuilder.toString();





        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        String formattedDate = dateTime.format(formatter);





        PedidoDTO pedido = new PedidoDTO(detalhes, clienteId, formattedDate, valorTotal);

        // Exibindo os detalhes do pedido para verificação
        JOptionPane.showMessageDialog(this, "Pedido criado com sucesso:\n" +
                "Cliente ID: " + pedido.idCliente() + "\n" +
                "Detalhes: " + pedido.detalhesPedido() + "\n" +
                "Valor Total: " + pedido.valorTotal() + "\n" +
                "Data: " + pedido.dataVenda());
    }

    private void loadCustomersAndProducts() {
//        allProducts = DatabaseUtils.findAllProducts();
//        allCustomers = DatabaseUtils.findAllCustomers();


        allCustomers = List.of(new Cliente(1, "joao", "rua fulano", "3", "17047888", "joao@email.com", "40028922"),
                new Cliente(1, "pedro", "rua ciclano", "2", "17047888", "joao@email.com", "1999332"));
        allProducts = List.of(new Produto(2, "pizza de calabresa", "calabresa, mossarela e cebola", true, 40.0F),
                new Produto(1, "pizza de 4 queijos", "mossarela, provolone, parmesão e catupiry", true, 50.0F));

        updateCustomersTable(allCustomers);
        updateProductsTable(allProducts);
    }

    private void filterCustomers() {
        final String searchText = searchedCustomerTextField.getText().toLowerCase();
        List<Cliente> filteredCustomers = new ArrayList<>();
        for (final Cliente customer : allCustomers) {
            if (customer.nome().toLowerCase().contains(searchText) || customer.telefone().contains(searchText)) {
                filteredCustomers.add(customer);
            }
        }
        updateCustomersTable(filteredCustomers);
    }

    private void updateCustomersTable(final List<Cliente> customers) {
        customersDataTable.setRowCount(0);
        for (final Cliente customer : customers) {
            customersDataTable.addRow(new Object[]{customer.id(), customer.nome(), customer.telefone()});
        }
    }

    private void filterProducts() {
        final String searchText = searchedProductsTextField.getText().toLowerCase();
        List<Produto> filteredProducts = new ArrayList<>();
        for (final Produto product : allProducts) {
            if (product.nome().toLowerCase().contains(searchText) ||
                    product.descricao().contains(searchText) ||
                    product.valor().toString().contains(searchText)) {
                filteredProducts.add(product);
            }
        }
        updateProductsTable(filteredProducts);
    }

    private void updateProductsTable(final List<Produto> products) {
        productsDataTable.setRowCount(0);
        for (final Produto product : products) {
            productsDataTable.addRow(new Object[]{product.id(), product.nome(), product.descricao(), product.valor()});
        }
    }
}
