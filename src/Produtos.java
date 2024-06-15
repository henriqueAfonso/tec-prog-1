
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Produtos extends JFrame{
    private JPanel mainPanel;
    private JTextField productName;
    private JTextArea productDescription;
    private JComboBox<String> isAvailableOptions;
    private JTextField productPrice;
    private JButton registerProductButton=new JButton("Cadastrar produto");

    public Produtos() {
        // Configurar JFrame
        setTitle("Adicionar Produto");
        setPreferredSize(new Dimension(650,450));

        // mainPanel
        mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // panel1
        JPanel panel1 = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(panel1, gbc);

        GridBagConstraints panel1Gbc = new GridBagConstraints();
        panel1Gbc.insets = new Insets(5, 5, 5, 5);

        // Nome do produto Label
        JLabel labelProductName = new JLabel("Nome do produto");
        panel1Gbc.gridx = 1;
        panel1Gbc.gridy = 0;
        panel1Gbc.anchor = GridBagConstraints.WEST;
        panel1.add(labelProductName, panel1Gbc);

        // Nome do produto TextField
        productName = new JTextField();
        panel1Gbc.gridx = 1;
        panel1Gbc.gridy = 1;
        panel1Gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1Gbc.weightx = 1.0;
        panel1.add(productName, panel1Gbc);

        // panel2
        JPanel panel2 = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(panel2, gbc);

        GridBagConstraints panel2Gbc = new GridBagConstraints();
        panel2Gbc.insets = new Insets(5, 5, 5, 5);

        // Descrição do produto Label
        JLabel labelProductDescription = new JLabel("Descrição do produto");
        panel2Gbc.gridx = 0;
        panel2Gbc.gridy = 0;
        panel2Gbc.gridwidth = 2;
        panel2Gbc.anchor = GridBagConstraints.WEST;
        panel2.add(labelProductDescription, panel2Gbc);

        // Descrição do produto TextArea
        productDescription = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(productDescription);
        scrollPane.setPreferredSize(new Dimension(150,50));
        panel2Gbc.gridx = 0;
        panel2Gbc.gridy = 1;
        panel2Gbc.gridwidth = 1;
        panel2Gbc.fill = GridBagConstraints.BOTH;
        panel2Gbc.weightx = 1.0;
        panel2Gbc.weighty = 1.0;
        panel2.add(scrollPane, panel2Gbc);

        // panel3
        JPanel panel3 = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(panel3, gbc);

        GridBagConstraints panel3Gbc = new GridBagConstraints();
        panel3Gbc.insets = new Insets(5, 5, 5, 5);

        // Disponibilidade Label
        JLabel labelIsAvailable = new JLabel("Disponibilidade");
        panel3Gbc.gridx = 0;
        panel3Gbc.gridy = 0;
        panel3.add(labelIsAvailable, panel3Gbc);

        // Disponibilidade ComboBox
        isAvailableOptions = new JComboBox<>();
        panel3Gbc.gridx = 0;
        panel3Gbc.gridy = 1;
        panel3Gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3Gbc.weightx = 1.0;
        panel3.add(isAvailableOptions, panel3Gbc);

        // Valor do produto Label
        JLabel labelProductPrice = new JLabel("Valor do produto");
        panel3Gbc.gridx = 1;
        panel3Gbc.gridy = 0;
        panel3.add(labelProductPrice, panel3Gbc);

        // Valor do produto TextField
        productPrice = new JTextField();
        panel3Gbc.gridx = 1;
        panel3Gbc.gridy = 1;
        panel3Gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3Gbc.weightx = 1.0;
        panel3.add(productPrice, panel3Gbc);

        // panel4 (para o botão)
        JPanel panel4 = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(panel4, gbc);

        GridBagConstraints panel4Gbc = new GridBagConstraints();
        panel4Gbc.insets = new Insets(5, 5, 5, 5);

        // Cadastrar produto Button
        panel4Gbc.gridx = 0;
        panel4Gbc.gridy = 0;
        panel4Gbc.fill = GridBagConstraints.HORIZONTAL;
        panel4.add(registerProductButton, panel4Gbc);

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
                    String sql = "INSERT INTO produtos (nome, descricao, disponibilidade,valor) VALUES ( ?, ?, ?, ?)";
                    PreparedStatement pstmt = con.prepareStatement(sql);
                    pstmt.setString(1,name);
                    pstmt.setString(2,description);
                    pstmt.setBoolean(3, availability);
                    pstmt.setFloat(4,price);

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

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Configurar JComboBox
        isAvailableOptions.addItem("1");
        isAvailableOptions.addItem("0");
    }

}