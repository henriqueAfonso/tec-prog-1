import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Produtos extends JFrame {
    private JPanel mainPanel;
    private JLabel labelProductName;
    private JTextField productName;
    private JLabel labelProductDescription;
    private JTextArea productDescription;
    private JPanel panel2;
    private JPanel panel1;
    private JPanel panel3;
    private JLabel labelIsAvailable;
    private JComboBox<String> isAvailableOptions;
    private JLabel labelProductPrice;
    private JTextField productPrice;
    private JButton registerProductButton;

    public Produtos(){
        setContentPane(mainPanel);
        setTitle("Adicionar produto");

        isAvailableOptions.addItem("1");
        isAvailableOptions.addItem("0");

        setSize(650, 450);
        setLocationRelativeTo(null);
        setVisible(true);
        registerProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con = null;

                try{

                    String name = productName.getText();
                    String description = productDescription.getText();
                    String[] selectedItem = new String[1];
                    selectedItem[0]=(String)isAvailableOptions.getSelectedItem();
                    Boolean availability = Boolean.parseBoolean(selectedItem[0]);
                    float price = Float.parseFloat(productPrice.getText());

                    Class.forName("org.hsql.jdbcDriver");
                    con = DriverManager.getConnection("jdbc:HypersonicSQL:http://localhost", "sa", "");
                    String sql = "INSERT INTO produtos (nome, descricao, ativo,valor) VALUES ( ?, ?, ?, ?)";
                    PreparedStatement pstmt = con.prepareStatement(sql);
                    pstmt.setString(1,name);
                    pstmt.setString(2,description);
                    pstmt.setBoolean(3, availability);
                    pstmt.setFloat(4,price);
                    pstmt.execute();

                    pstmt.execute();

                    JOptionPane.showMessageDialog(Produtos.this,"Produto cadastrado");
                    pstmt.close();

                }catch (NumberFormatException e1){
                    JOptionPane.showMessageDialog(Produtos.this, "Invalid number format");
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(Produtos.this,ex.getMessage());
                }finally {
                    try{
                        if (con!=null){
                            con.close();
                        }
                    }catch (java.sql.SQLException e2){
                        JOptionPane.showMessageDialog(Produtos.this,e2.getMessage());
                    }
                }
            }
        });
    }

}
