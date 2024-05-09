import javax.swing.*;

public class TelaCadastro extends JFrame {

    private JPanel mainPanel;
    private JTextField tfNome;
    private JTextField tfLogradouro;
    private JTextField tfNum;
    private JLabel lbNome;
    private JLabel lbRua;
    private JLabel lbNumero;
    private JLabel lbCep;
    private JTextField tfEmail;
    private JTextField tfTelefone;
    private JButton salvarBtn;
    private JButton limparButton;
    private JPanel limparBtn;
    private JLabel lbEmail;
    private JLabel lbTelefone;
    private JTextField tfCep;

    public TelaCadastro(){
        setContentPane(mainPanel);
        setTitle("Cadastrar Usuario");
        setSize(650, 450);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}


