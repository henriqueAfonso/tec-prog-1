import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RestauranteGUI extends JFrame {
    private JTextField searchField;
    private JTable clientTable;
    private DefaultTableModel tableModel;
    private List<Client> allClients;

    public RestauranteGUI() {
        // Configurações da janela principal
        setTitle("Tela de Pedidos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializa a lista de todos os clientes (exemplo)
        allClients = new ArrayList<>();
        allClients.add(new Client("João Silva", "123-456-789"));
        allClients.add(new Client("Maria Oliveira", "987-654-321"));
        allClients.add(new Client("Carlos Pereira", "555-555-555"));
        allClients.add(new Client("Ana Souza", "111-222-333"));
        allClients.add(new Client("Pedro Santos", "444-333-222"));

        // Cria a barra de busca
        searchField = new JTextField(20);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                filterClients();
            }
            public void removeUpdate(DocumentEvent e) {
                filterClients();
            }
            public void insertUpdate(DocumentEvent e) {
                filterClients();
            }
        });

        // Cria o modelo de tabela não editável
        tableModel = new DefaultTableModel(new Object[]{"Nome", "Telefone"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Cria a tabela de clientes
        clientTable = new JTable(tableModel);

        // Configura a seleção única
        clientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Preenche a tabela com todos os clientes inicialmente
        updateClientTable(allClients);

        // Adiciona componentes ao painel principal
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Buscar Cliente:"), BorderLayout.NORTH);
        panel.add(searchField, BorderLayout.CENTER);
        panel.add(new JScrollPane(clientTable), BorderLayout.SOUTH);

        add(panel);
    }

    private void filterClients() {
        String searchText = searchField.getText().toLowerCase();
        List<Client> filteredClients = new ArrayList<>();
        for (Client client : allClients) {
            if (client.getName().toLowerCase().contains(searchText) || client.getPhone().contains(searchText)) {
                filteredClients.add(client);
            }
        }
        updateClientTable(filteredClients);
    }

    private void updateClientTable(List<Client> clients) {
        tableModel.setRowCount(0);
        for (Client client : clients) {
            tableModel.addRow(new Object[]{client.getName(), client.getPhone()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RestauranteGUI gui = new RestauranteGUI();
            gui.setVisible(true);
        });
    }
}

class Client {
    private String name;
    private String phone;

    public Client(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}