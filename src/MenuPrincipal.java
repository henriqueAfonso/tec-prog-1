import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    private JPanel mainPanel;
    private JButton cadastrarClienteBtn;
    private JButton fazerPedidoBtn;
    private JButton adcionarProdutoBtn;
    private JButton filaDePedidosBtn;
    private JLabel lbMenu;

    public MenuPrincipal() {
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        lbMenu = new JLabel("Menu Principal");
        lbMenu.setFont(new Font(lbMenu.getFont().getName(), Font.PLAIN, 18));

        cadastrarClienteBtn = new JButton("Cadastrar Cliente");
        cadastrarClienteBtn.setMinimumSize(new Dimension(200, 50));
        cadastrarClienteBtn.setPreferredSize(new Dimension(300, 60));
        cadastrarClienteBtn.setMaximumSize(new Dimension(400, 70));

        fazerPedidoBtn = new JButton("Fazer Pedido");
        fazerPedidoBtn.setMinimumSize(new Dimension(200, 50));
        fazerPedidoBtn.setPreferredSize(new Dimension(300, 60));
        fazerPedidoBtn.setMaximumSize(new Dimension(400, 70));

        adcionarProdutoBtn = new JButton("Adcionar Produto");
        adcionarProdutoBtn.setMinimumSize(new Dimension(200, 50));
        adcionarProdutoBtn.setPreferredSize(new Dimension(300, 60));
        adcionarProdutoBtn.setMaximumSize(new Dimension(400, 70));

        filaDePedidosBtn = new JButton("Fila de Pedidos");
        filaDePedidosBtn.setMinimumSize(new Dimension(200, 50));
        filaDePedidosBtn.setPreferredSize(new Dimension(300, 60));
        filaDePedidosBtn.setMaximumSize(new Dimension(400, 70));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        mainPanel.add(lbMenu, gbc);

        gbc.gridy++;
        mainPanel.add(cadastrarClienteBtn, gbc);

        gbc.gridy++;
        mainPanel.add(fazerPedidoBtn, gbc);

        gbc.gridy++;
        mainPanel.add(adcionarProdutoBtn, gbc);

        gbc.gridy++;
        mainPanel.add(filaDePedidosBtn, gbc);

        setContentPane(mainPanel);
        setTitle("Cadastrar Usuario");
        setSize(650, 450);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        adcionarProdutoBtn.addActionListener(e -> SwingUtilities.invokeLater(TelaProdutos::new));
        fazerPedidoBtn.addActionListener(e -> SwingUtilities.invokeLater(TelaPedidos::new));
        cadastrarClienteBtn.addActionListener(e -> SwingUtilities.invokeLater(TelaCadastro::new));
        filaDePedidosBtn.addActionListener(e -> SwingUtilities.invokeLater(TelaFilaDePedidos::new));
    }
}