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
        /*
        System.out.println("Bun venit la casa de schimb valutar Aurelu'!");
        CurrencyExchange currencyExchange = new CurrencyExchange();
        TransactionManagerInterface transaction = new TransactionManager();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("CET"));

        Scanner in = new Scanner(System.in);

        Integer option = 0;
        do {
            System.out.println("\nMENU\n");
            System.out.println("1. Afisare valute");
            System.out.println("2. Afisare schimb valutar");
            System.out.println("3. Creare tranzactie noua");
            System.out.println("4. Istoric tranzactii");
            System.out.println("5. Actualizare curs valutar");
            System.out.println("6. Actuallizare convertor valutar(ar fi bine sa fie rulat dupa optiunea 5)");
            System.out.println("7. Exit");
            System.out.print("Optiune: ");
            option = in.nextInt();
            switch (option) {
                case 1:
                    System.out.println(currencyExchange.getManagerCurrency());
                    CsvWriterAudit.writeCsvFile("src/audit.csv","Afisare valute", sdf.format(new Date(System.currentTimeMillis())));
                    break;
                case 2:
                    currencyExchange.printCurrencyExchange();
                    CsvWriterAudit.writeCsvFile("src/audit.csv","Afisare schimb valutar", sdf.format(new Date(System.currentTimeMillis())));
                    break;
                case 3:
                    Transaction t = transaction.createTransaction(currencyExchange.getManagerCurrency());
                    transaction.processTransaction(t, currencyExchange.getManagerCurrency(), currencyExchange.getCurrencyExchange(), currencyExchange.getAmountCurrencyExchange());
                    CsvWriterAudit.writeCsvFile("src/audit.csv","Creare tranzactie noua", sdf.format(new Date(System.currentTimeMillis())));
                    break;
                case 4:
                    transaction.showHistory();
                    CsvWriterAudit.writeCsvFile("src/audit.csv","Istoric tranzactii", sdf.format(new Date(System.currentTimeMillis())));
                    break;
                case 5:
                    currencyExchange.updateCurrencyExchange();
                    CsvWriterAudit.writeCsvFile("src/audit.csv", "Actualizare curs valutar", sdf.format(new Date(System.currentTimeMillis())));
                    break;
                case 6:
                    currencyExchange.updateConverter();
                    CsvWriterAudit.writeCsvFile("src/audit.csv","Actualizare convertor valutar", sdf.format(new Date(System.currentTimeMillis())));
                    break;
                case 7:
                    CsvWriterAudit.writeCsvFile("src/audit.csv","Exit", sdf.format(new Date(System.currentTimeMillis())));
                    break;
                default:
                    System.out.println("Optiune invalida\n");
            }

        } while (option != 7);
        */
    }
}
