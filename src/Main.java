import Exchange.CurrencyExchange;
import Transaction.Transaction;
import Transaction.TransactionManager;
import Transaction.TransactionManagerInterface;

import java.io.IOException;
import java.util.Scanner;
import java.util.SortedMap;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Bun venit la casa de schimb valutar Aurelu'!");
        CurrencyExchange currencyExchange = new CurrencyExchange();
        TransactionManagerInterface transaction = new TransactionManager();

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
                    break;
                case 2:
                    currencyExchange.printCurrencyExchange();
                    break;
                case 3:
                    Transaction t = transaction.createTransaction(currencyExchange.getManagerCurrency());
                    transaction.processTransaction(t, currencyExchange.getManagerCurrency(), currencyExchange.getCurrencyExchange(), currencyExchange.getAmountCurrencyExchange());
                    break;
                case 4:
                    transaction.showHistory();
                    break;
                case 5:
                    currencyExchange.updateCurrencyExchange();
                    break;
                case 6:
                    currencyExchange.updateConverter();
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Optiune invalida\n");
            }

        } while (option != 7);
    }
}
