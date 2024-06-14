import javax.swing.*;
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
    private JPanel limparPanel;
    private JLabel lbEmail;
    private JLabel lbTelefone;
    private JTextField tfCep;


    public TelaCadastro() {
        setContentPane(mainPanel);
        setTitle("Cadastrar Usuario");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setVisible(true);

        salvarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(camposEValido()){
                    String nome = tfNome.getText();
                    String cep = tfCep.getText();
                    String num = tfNum.getText();
                    String email = tfEmail.getText();
                    String logradouro = tfLogradouro.getText();
                    String telefone = tfTelefone.getText();
                    Connection con = null;
                    try{
                        Class.forName("org.hsql.jdbcDriver");
                        con = DriverManager.getConnection("jdbc:HypersonicSQL:http://localhost", "sa", "");
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
                    }catch(ClassNotFoundException ex){
                        System.out.println("Driver do banco de dados não encontrado");
                    }catch (SQLException ex){
                        System.out.println(ex.getMessage());
                    }finally{
                        if(con != null){
                            try {
                                con.close();
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                }
            }
        });

        limparBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent b) {
                tfNome.setText("");
                tfCep.setText("");
                tfEmail.setText("");
                tfNum.setText("");
                tfTelefone.setText("");
                tfLogradouro.setText("");

            }
        });
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


