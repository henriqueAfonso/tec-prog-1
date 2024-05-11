import javax.swing.*;

public class MenuPrincipal extends JFrame {
    private JPanel mainPanel;
    private JButton cadastrarClienteBtn;
    private JButton fazerPedidoBtn;
    private JButton adcionarProdutoBtn;
    private JButton filaDePedidosBtn;
    private JLabel lbMenu;

    public MenuPrincipal(){
        setContentPane(mainPanel);
        setTitle("Cadastrar Usuario");
        setSize(650, 450);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
