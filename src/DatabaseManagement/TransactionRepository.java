package DatabaseManagement;

import Client.Client;
import Transaction.Transaction;
import org.javatuples.Octet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    private final String driver = "oracle.jdbc.driver.OracleDriver";
    private final String url = "jdbc:oracle:thin:@//0.0.0.0:32769/ORCLCDB.localdomain";
    private final String username = "sys as sysdba";
    private final String password = "Oradoc_db1";

    public void addTransaction(TransactionModel t) {
        String sql = "INSERT INTO transactions VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Class.forName(driver);

            Connection con = DriverManager.getConnection(url, username, password);

            PreparedStatement s = con.prepareStatement(sql);

            s.setString(1, t.getClient().getFirstName());
            s.setString(2, t.getClient().getLastName());
            s.setString(3, t.getClient().getCnp());
            s.setString(4, t.getFirstCurrency().getName());
            s.setString(5, t.getSecondCurrency().getName());
            s.setString(6, t.getTypeOfTransaction().toString());
            s.setString(7, Double.toString(t.getAmountFrom()));
            s.setString(8, Double.toString(t.getAmountTo()));
            s.setInt(9, 10);
            s.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) { // SQLException
            e.printStackTrace();
        }
    }

    public List<Octet<String, String, String, String, String, String, String, String>> extractTransactions() {
        List<Octet<String, String, String, String, String, String, String, String>> data = new ArrayList<>();

        try {
            // Load the oracle sql driver
            Class.forName(driver);
            // Making a connection
            Connection con = DriverManager.getConnection
                    (url, username, password);
            // Creating a Statement object for our DB Connection
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT first_name, last_name, CNP, first_currency, second_currency, transaction_type, amount_from, amount_to FROM transactions");
            while (rs.next()) {
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String CNP = rs.getString("CNP");
                String first_currency = rs.getString("first_currency");
                String second_currency = rs.getString("second_currency");
                String transaction_type = rs.getString("transaction_type");
                String amount_from = rs.getString("amount_from");
                String amount_to = rs.getString("amount_to");
                data.add(
                        new Octet<>(first_name,
                        last_name,
                        CNP,
                        first_currency,
                        second_currency,
                        transaction_type,
                        amount_from,
                        amount_to)
                );
            }
            statement.close();
            con.close();
        } catch (ClassNotFoundException
                | SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
}
