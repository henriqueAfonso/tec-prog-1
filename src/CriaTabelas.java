import java.sql.*;
public class CriaTabelas {
    Connection con = null;
    public CriaTabelas(){
        try {
            this.criaTabelaCliente();
            this.criaTabelaProdutos();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void criaTabelaCliente() throws SQLException {
        try{
            Class.forName("org.hsql.jdbcDriver");
            con = DriverManager.getConnection("jdbc:HypersonicSQL:http://localhost", "sa", "");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("CREATE TABLE clientes(id INTEGER NOT NULL IDENTITY PRIMARY KEY," +
                    "nome VARCHAR(60) NOT NULL," +
                    "logradouro VARCHAR(60) NOT NULL," +
                    "num VARCHAR(10) NOT NULL," +
                    "cep VARCHAR(10) NOT NULL," +
                    "email VARCHAR(30)," +
                    "telefone VARCHAR(15) NOT NULL)");
        }catch(ClassNotFoundException ex){
            System.out.println("Driver do banco de dados não encontrado");
        }catch (SQLException ex){
            System.out.println("Erro ao criar a tabela clientes " + ex.getMessage());
        }finally{
            if(con != null){
                con.close();
            }
        }
    }
    public void criaTabelaProdutos() throws SQLException {
        try{
            Class.forName("org.hsql.jdbcDriver");
            con = DriverManager.getConnection("jdbc:HypersonicSQL:http://localhost", "sa", "");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("CREATE TABLE produtos(id INTEGER NOT NULL IDENTITY PRIMARY KEY," +
                    "nome VARCHAR(60) NOT NULL," +
                    "descricao VARCHAR(100) NOT NULL," +
                    "disponibilidade BIT NOT NULL ," +
                    "valor FLOAT NOT NULL)");
        }catch(ClassNotFoundException ex){
            System.out.println("Driver do banco de dados não encontrado");
        }catch (SQLException ex){
            System.out.println("Erro ao criar a tabela produtos " + ex.getMessage());
        }finally{
            if(con != null){
                con.close();
            }
        }
    }

}
