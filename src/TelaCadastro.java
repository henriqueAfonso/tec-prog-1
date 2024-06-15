import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

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
    private JButton limparBtn;
    private JLabel lbEmail;
    private JLabel lbTelefone;
    private JTextField tfCep;

    public TelaCadastro() {
        setTitle("Cadastrar Usuário");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        setVisible(true);

        salvarBtn.addActionListener(e -> {
            if(camposEValido()){
                String nome = tfNome.getText();
                String cep = tfCep.getText();
                String num = tfNum.getText();
                String email = tfEmail.getText();
                String logradouro = tfLogradouro.getText();
                String telefone = tfTelefone.getText();
                Connection con = null;
                try {
                    Class.forName("org.hsql.jdbcDriver");
                    con = DriverManager.getConnection("jdbc:HypersonicSQL:bd_teste", "sa", "");
                    String sql = "INSERT INTO clientes (nome, logradouro, num, cep, email, telefone) VALUES ( ?, ?, ?, ?, ?, ?)";
                    PreparedStatement pstmt = con.prepareStatement(sql);
                    pstmt.setString(1, nome);
                    pstmt.setString(2, logradouro);
                    pstmt.setString(3, num);
                    pstmt.setString(4, cep);
                    pstmt.setString(5, email);
                    pstmt.setString(6, telefone);

                    pstmt.execute();

                    JOptionPane.showMessageDialog(TelaCadastro.this,"Cliente cadastrado");
                    pstmt.close();
                } catch(ClassNotFoundException ex) {
                    System.out.println("Driver do banco de dados não encontrado");
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                } finally {
                    if(con != null) {
                        try {
                            con.close();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });

        limparBtn.addActionListener(b -> {
            tfNome.setText("");
            tfCep.setText("");
            tfEmail.setText("");
            tfNum.setText("");
            tfTelefone.setText("");
            tfLogradouro.setText("");

        });
    }

    private void initComponents() {
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;

        lbNome = new JLabel("Nome:");
        tfNome = new JTextField(20);
        tfNome.setPreferredSize(new Dimension(0, 30));

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(lbNome, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        mainPanel.add(tfNome, gbc);

        lbRua = new JLabel("Rua:");
        tfLogradouro = new JTextField(20);
        tfLogradouro.setPreferredSize(new Dimension(0, 30));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(lbRua, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        mainPanel.add(tfLogradouro, gbc);

        lbNumero = new JLabel("Número:");
        tfNum = new JTextField(5);
        tfNum.setPreferredSize(new Dimension(0, 30));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        mainPanel.add(lbNumero, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        mainPanel.add(tfNum, gbc);

        lbCep = new JLabel("CEP:");
        tfCep = new JTextField(10);
        tfCep.setPreferredSize(new Dimension(0, 30));

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        mainPanel.add(lbCep, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        mainPanel.add(tfCep, gbc);

        lbEmail = new JLabel("E-mail:");
        tfEmail = new JTextField(20);
        tfEmail.setPreferredSize(new Dimension(0, 30));

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        mainPanel.add(lbEmail, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        mainPanel.add(tfEmail, gbc);

        lbTelefone = new JLabel("Telefone:");
        tfTelefone = new JTextField(15);
        tfTelefone.setPreferredSize(new Dimension(0, 30));

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        mainPanel.add(lbTelefone, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        mainPanel.add(tfTelefone, gbc);

        limparBtn = new JButton("Limpar");
        limparBtn.setPreferredSize(new Dimension(100, 30));

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(limparBtn, gbc);

        salvarBtn = new JButton("Salvar");
        salvarBtn.setPreferredSize(new Dimension(100, 30));

        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        mainPanel.add(salvarBtn, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }
    private boolean camposEValido(){
        if(Objects.equals(tfNome.getText(), "")){
            JOptionPane.showMessageDialog(this, "O nome não é válido");
            return false;
        }
        if (Objects.equals(tfLogradouro.getText(), "")) {
            JOptionPane.showMessageDialog(this, "O logradouro não é valido");
            return false;
        }
        if (Objects.equals(tfCep.getText(), "")) {
            JOptionPane.showMessageDialog(this, "O logradouro não é valido");
            return false;
        }
        if (Objects.equals(tfNum.getText(), "")) {
            JOptionPane.showMessageDialog(this, "O logradouro não é valido");
            return false;
        }
        if (Objects.equals(tfTelefone.getText(), "")) {
            JOptionPane.showMessageDialog(this, "O telefone não é valido");
            return false;
        }
        if (Objects.equals(tfEmail.getText(), "")) {
            JOptionPane.showMessageDialog(this, "O Email não é valido");
            return false;
        }

        return true;
    }
}
