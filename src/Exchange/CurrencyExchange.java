package Exchange;

import Currency.*;
import Currency.Currency;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.jetbrains.annotations.Contract;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CurrencyExchange {
    private static final Double ADDITION = 0.09d;
    private Map<Currency, Triplet<List<Pair<Integer, BankNote>>, List<Pair<Integer, Coin>>, Double>> amountCurrencyExchange;
    private Map<Pair<Currency, Currency>, Pair<Double, Double>> currencyExchange;
    private CurrencyManagerInterface managerCurrency;

    public CurrencyExchange() throws IOException {
        managerCurrency = initCurrencyExchange();
        amountCurrencyExchange = new HashMap<>();
        initInitialMoney();
        updateConverter();
        updateCurrencyExchange();
    }


    public CurrencyManagerInterface getManagerCurrency() {
        return managerCurrency;
    }

    public Map<Pair<Currency, Currency>, Pair<Double, Double>> getCurrencyExchange() {
        return currencyExchange;
    }

    @Contract(pure = true)
    public static Double getADDITION() {
        return ADDITION;
    }

    public Map<Currency, Triplet<List<Pair<Integer, BankNote>>, List<Pair<Integer, Coin>>, Double>> getAmountCurrencyExchange() {
        return amountCurrencyExchange;
    }

    // Internal methods to read from file
    private void initInitialMoney() throws IOException {
        File file = new File("InitialMoney");
        Scanner in = new Scanner(file);

        Integer nOfCurrencies = in.nextInt();
        in.nextLine();
        for (Integer i = 0; i < nOfCurrencies; ++i) {
            // currency name
            String currencyName = in.nextLine();
            Currency detectedCurrency = managerCurrency.getCurrencyByName(currencyName);

            List<Pair<Integer, BankNote>> bankNotes = new ArrayList<>();
            Integer nOfBankNotes = in.nextInt();
            Double sum = 0d;
            for (Integer j = 0; j < nOfBankNotes; ++j) {
                Integer n = in.nextInt();
                Integer value = in.nextInt();
                sum += n.doubleValue() * value.doubleValue();
                in.nextLine();
                String name = in.nextLine();
                bankNotes.add(new Pair<>(n, new BankNote(name, value)));
            }

            List<Pair<Integer, Coin>> coins = new ArrayList<>();
            Integer nOfCoins = in.nextInt();
            for (Integer j = 0; j < nOfCoins; ++j) {
                Integer n = in.nextInt();
                Integer value = in.nextInt();
                sum += n.doubleValue() * (value.doubleValue() / 100d);
                in.nextLine();
                String name = in.nextLine();
                coins.add(new Pair<>(n, new Coin(name, value)));
            }

            amountCurrencyExchange.put(detectedCurrency, new Triplet<>(bankNotes, coins, sum));
        }
    }

    private CurrencyManagerInterface initCurrencyExchange() {
        CurrencyManagerInterface managerCurrency = new CurrencyManager();
        managerCurrency.readCurrenciesFromFile();
        return managerCurrency;
    }

    // update converter
    public void updateConverter() throws IOException {
        managerCurrency.generateConverter();
    }

    // update currency exchange
    public void updateCurrencyExchange() {
        currencyExchange = new HashMap<>();

        Map<Pair<Currency, Currency>, Double> currencyConverter = managerCurrency.getCurrencyConverter();
        for (Map.Entry<Pair<Currency, Currency>, Double> it : currencyConverter.entrySet()) {
            currencyExchange.put(it.getKey(), new Pair<>(it.getValue() + ADDITION, it.getValue() - ADDITION));
        }
    }

    public List<Pair<String, String>> getCurrencyExchangePair() {
        List<Pair<String, String>> ExchangePairs = new ArrayList<>();
        currencyExchange.forEach((k, v) -> {
            ExchangePairs.add(new Pair<>
                    (
                        "Vanzare : 1 " + k.getValue0().getName() + " = " + v.getValue0() + " " + k.getValue1().getName() + "\n",
                        "Cumparare : 1 " + k.getValue0().getName() + " = " + v.getValue1() + " " + k.getValue1().getName() + "\n"
                    )
            );
        });

        return ExchangePairs;
    }

    // print currencyExchange
    public void printCurrencyExchange() {
        currencyExchange.forEach((k, v) -> {
            System.out.print("Vanzare: \n");
            System.out.print("1 " + k.getValue0().getName() + " = " + v.getValue0() + " " + k.getValue1().getName() + "\n");
            System.out.print("Cumparare: \n");
            System.out.print("1 " + k.getValue0().getName() + " = " + v.getValue1() + " " + k.getValue1().getName() + "\n");
        });
    }
}
