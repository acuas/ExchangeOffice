package Currency;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.*;

import org.javatuples.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// Service Implementation
public class CurrencyManager implements CurrencyManagerInterface {
    private Set<Currency> currencies;
    private Map<Pair<Currency, Currency>, Double> currencyConverter;

    public CurrencyManager() {
        currencies = new HashSet<>();
    }

    public Set<Currency> getCurrencies() {
        return currencies;
    }

    public Map<Pair<Currency, Currency>, Double> getCurrencyConverter() {
        return currencyConverter;
    }

    public Currency getCurrencyByName(String currencyName) {
        for (Currency iterator : currencies) {
            if (iterator.getName().equals(currencyName))
                return iterator;
        }

        throw new Error("Currency not found!");
    }

    public void readCurrenciesFromFile() {
        File file = new File("currencies.in");
        // Using Scanner for getting input from file

        Scanner in = null;
        boolean opened = true;
        try {
            in = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            opened = false;
            e.printStackTrace();
        }
        finally {
            if (opened) {
                Integer nOfCurrencies = in.nextInt();
                for (Integer j = 0; j < nOfCurrencies; ++j) {
                    Integer option = in.nextInt();
                    in.nextLine();
                    String currencyName = in.nextLine();

                    Integer stabilityInValue = 0;
                    if (option == 2) {
                        stabilityInValue = in.nextInt();
                    } else if (option != 1) {
                        System.out.println("Throw exception here");
                    }

                    Integer nOfCoins = in.nextInt();
                    in.nextLine();
                    List<Coin> coins = new ArrayList<>(nOfCoins);

                    for (Integer i = 0; i < nOfCoins; ++i) {
                        String coinsName = in.nextLine();
                        Integer value = in.nextInt();
                        in.nextLine();
                        coins.add(new Coin(coinsName, value));
                    }

                    coins.sort(new CoinComparator());

                    Integer nOfBankNotes = in.nextInt();
                    in.nextLine();
                    List<BankNote> bankNotes = new ArrayList<>(nOfBankNotes);

                    for (Integer i = 0; i < nOfBankNotes; ++i) {
                        String bankNotesName = in.nextLine();
                        Integer value = in.nextInt();
                        in.nextLine();
                        bankNotes.add(new BankNote(bankNotesName, value));
                    }

                    bankNotes.sort(new BankNoteComparator());

                    if (option == 2) {
                        currencies.add(new HardCurrency(currencyName, coins, bankNotes, stabilityInValue));
                    } else {
                        currencies.add(new Currency(currencyName, coins, bankNotes));
                    }
                }
                in.close();
            }
        }
    }

    public void createCurrency() {
        // Using Scanner for getting input from user
        Scanner in = new Scanner(System.in);

        System.out.print("Select one of the options bellow: ");
        System.out.print("1. Normal Currency");
        System.out.print("2. Hard Currency");
        Integer option = in.nextInt();
        in.nextLine();
        System.out.print("Currency name: ");
        String currencyName = in.nextLine();

        Integer stabilityInValue = 0;
        if (option == 2) {
            System.out.print("Stability in value: ");
            stabilityInValue = in.nextInt();
            in.nextLine();
        }
        else if (option != 1) {
            System.out.println("Throw exception here");
        }
        // Create coins for current currency
        System.out.print("The number of coins belonging to the currency: ");
        Integer nOfCoins = in.nextInt();
        in.nextLine();
        List<Coin> coins = new ArrayList<>(nOfCoins);

        for (Integer i = 0; i < nOfCoins; ++i) {
            System.out.print("The name of coin: ");
            String coinsName = in.nextLine();
            System.out.print("Coin " + i + " has value: ");
            Integer value = in.nextInt();
            in.nextLine();
            coins.add(new Coin(coinsName, value));
        }

        coins.sort(new CoinComparator());

        // Create banknotes for current currency
        System.out.print("The number of banknotes belonging to the currency: ");
        Integer nOfBankNotes = in.nextInt();
        in.nextLine();
        List<BankNote> bankNotes = new ArrayList<>(nOfBankNotes);

        for (Integer i = 0; i < nOfBankNotes; ++i) {
            System.out.print("The name of banknote: ");
            String bankNotesName = in.nextLine();
            System.out.print("Banknote " + i + " has value: ");
            Integer value = in.nextInt();
            in.nextLine();
            bankNotes.add(new BankNote(bankNotesName, value));
        }

        bankNotes.sort(new BankNoteComparator());

        if (option == 2) {
            currencies.add(new HardCurrency(currencyName, coins, bankNotes, stabilityInValue));
        }
        else {
            currencies.add(new Currency(currencyName, coins, bankNotes));
        }

        in.close();
    }

    public void generateConverter() throws IOException {
        currencyConverter = new HashMap<>();
        Document doc = Jsoup.connect("https://www.cursvalutar.ro/convertor-valutar/").get();

        Elements monedaLeft = doc.getElementsByClass("monedaLeft");
        Element moneda = monedaLeft.first();
        Elements options = moneda.getElementsByTag("option");

        Map<String, Double> hashMapConverter = new HashMap<>();
        for (Integer i = 0; i < 4; ++i) {
            Element option = options.get(i);
            hashMapConverter.put(option.text(), Double.parseDouble(option.attr("value")));
        }

        currencies.forEach(currency -> {
            if (currency.getName().equals("RON")) {
                currencies.forEach(auxCurrency -> {
                    if (!auxCurrency.getName().equals("RON")) {
                        Pair<Currency, Currency> __tmpPair = new Pair<Currency, Currency>(currency, auxCurrency);
                        currencyConverter.put(__tmpPair, 1 / hashMapConverter.get(auxCurrency.getName()));
                    }
                });
            }
            else {
                currencies.forEach(auxCurrency -> {
                    if (!auxCurrency.getName().equals(currency.getName())) {
                        Pair<Currency, Currency> __tmpPair = new Pair<Currency, Currency>(currency, auxCurrency);
                        currencyConverter.put(__tmpPair,
                                hashMapConverter.get(currency.getName()) / hashMapConverter.get(auxCurrency.getName()));

                    }
                });
            }
        });
    }

    public Double convert(Currency A, Currency B, Double value) {
        return value * currencyConverter.get(new Pair<>(A, B));
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        currencies.forEach(res::append);
        return res.toString();
    }
}
