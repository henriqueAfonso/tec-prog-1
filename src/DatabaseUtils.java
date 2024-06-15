import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {
    public static List<Produto> findAllProducts() {
        Connection con = null;
        List<Produto> allProducts = new ArrayList<>();

        try {
            con = DriverManager.getConnection("jdbc:HypersonicSQL:bd_teste", "sa", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM produtos");

            while (rs.next()) {
                final Produto product = new Produto(rs.getInt("id"),
                                                rs.getString("nome"),
                                                rs.getString("descricao"),
                                                rs.getBoolean("disponibilidade"),
                                                rs.getFloat("valor"));
                allProducts.add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch(SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return allProducts;
    }

    public static List<Cliente> findAllCustomers() {
        Connection con = null;
        List<Cliente> allCustomers = new ArrayList<>();

        try {
            con = DriverManager.getConnection("jdbc:HypersonicSQL:bd_teste", "sa", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM clientes");

            while (rs.next()) {
                final Cliente customer = new Cliente(rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("logradouro"),
                        rs.getString("num"),
                        rs.getString("cep"),
                        rs.getString("email"),
                        rs.getString("telefone"));
                allCustomers.add(customer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch(SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return allCustomers;
    }

    public static List<PedidoWithId> findAllActiveOrders() {
        Connection con = null;
        List<PedidoWithId> allOrders = new ArrayList<>();

        try {
            con = DriverManager.getConnection("jdbc:HypersonicSQL:bd_teste", "sa", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pedidos WHERE ativo = true");

            while (rs.next()) {
                final PedidoWithId customer = new PedidoWithId(rs.getInt("id"),
                        rs.getString("detalhesPedido"),
                        rs.getInt("idCliente"),
                        rs.getString("dataVenda"),
                        rs.getFloat("valorTotal"));
                allOrders.add(customer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch(SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return allOrders;
    }

    public static void createOrder(final Pedido order) {
        Connection con = null;

        try {
            Class.forName("org.hsql.jdbcDriver");
            con = DriverManager.getConnection("jdbc:HypersonicSQL:bd_teste", "sa", "");
            String sql = "INSERT INTO pedidos (detalhesPedido, idCliente, dataVenda, ativo, valorTotal) " +
                    "VALUES ( ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, order.detalhesPedido());
            pstmt.setInt(2, order.idCliente());
            pstmt.setString(3, order.dataVenda());
            pstmt.setBoolean(4, true);
            pstmt.setFloat(5, order.valorTotal());

            pstmt.execute();

        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch(SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void completeOrder(final int orderId) {
        Connection con = null;

        try {
            Class.forName("org.hsql.jdbcDriver");
            con = DriverManager.getConnection("jdbc:HypersonicSQL:bd_teste", "sa", "");
            String sql = "UPDATE pedidos SET ativo = ? WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setBoolean(1, false);
            pstmt.setInt(2, orderId);

            pstmt.execute();

        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch(SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
