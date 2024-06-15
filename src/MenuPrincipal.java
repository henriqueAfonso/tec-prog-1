import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame {
    private JPanel mainPanel;
    private JButton cadastrarClienteBtn;
    private JButton fazerPedidoBtn;
    private JButton adcionarProdutoBtn;
    private JButton filaDePedidosBtn;
    private JLabel lbMenu;

    public MenuPrincipal() {
        setContentPane(mainPanel);
        setTitle("Cadastrar Usuario");
        setSize(650, 450);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        adcionarProdutoBtn.addActionListener(e -> new Produtos());

        fazerPedidoBtn.addActionListener(e -> new TelaPedidos2());

        cadastrarClienteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent b) {
                new TelaCadastro();
            }
        });
    }
}
