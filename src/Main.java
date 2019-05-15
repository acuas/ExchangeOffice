import CSV.CsvWriterAudit;
import Currency.Coin;
import DatabaseManagement.ReadDatabase;
import Exchange.CurrencyExchange;
import GUI.MainFrame;
import Transaction.Transaction;
import Transaction.TransactionManager;
import Transaction.TransactionManagerInterface;

import javax.swing.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

public class Main {

    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(() -> {
            try {
                JFrame frame = new MainFrame("Exchange Office");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Bun venit la casa de schimb valutar Aurelu'!");
        CurrencyExchange currencyExchange = new CurrencyExchange();
        TransactionManagerInterface transaction = new TransactionManager();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("CET"));
    }
}
