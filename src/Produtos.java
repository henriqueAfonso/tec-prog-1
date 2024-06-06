import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Produtos extends JFrame {
    private JPanel mainPanel;
    private JLabel labelProductID;
    private JTextField productID;
    private JLabel labelProductName;
    private JTextField productName;
    private JLabel labelProductDescription;
    private JTextArea productDescription;
    private JPanel panel2;
    private JPanel panel1;
    private JPanel panel3;
    private JLabel labelIsAvailable;
    private JComboBox isAvailableOptions;
    private JLabel labelProductPrice;
    private JTextField productPrice;
    private JButton registerProductButton;

    public Produtos(){
        setContentPane(mainPanel);
        setTitle("Adicionar produto");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        isAvailableOptions.addItem("1");
        isAvailableOptions.addItem("0");

        setSize(650, 450);
        setLocationRelativeTo(null);
        setVisible(true);
        registerProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                JOptionPane.showMessageDialog(Produtos.this,"Produto cadastrado");
            }
        });
    }

}
