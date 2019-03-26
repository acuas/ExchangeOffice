package Transaction;

import Client.Client;
import Currency.Currency;
import Currency.Coin;
import Currency.BankNote;
import Currency.CurrencyManagerInterface;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.*;

public class TransactionManager implements TransactionManagerInterface {
    private List<Triplet<Transaction, Double, Double> > historyTransaction;

    public TransactionManager() {
        historyTransaction = new ArrayList<>();
    }

    public Transaction createTransaction(CurrencyManagerInterface managerCurrency) {
        Scanner in = new Scanner(System.in);
        Client client = new Client();

        boolean flag;
        do {
            flag = true;
            System.out.print("First Name: ");
            String firstName = in.nextLine();
            try {
                client.setFirstName(firstName);
            }
            catch (Error e) {
                flag = false;
                e.printStackTrace();
            }
        } while (!flag);

        do {
            flag = true;
            System.out.print("Last Name: ");
            String lastName = in.nextLine();
            try {
                client.setLastName(lastName);
            }
            catch (Error e) {
                flag = false;
                e.printStackTrace();
            }
        } while (!flag);

        do {
            flag = true;
            System.out.print("CNP: ");
            String cnp = in.nextLine();
            try {
                client.setCnp(cnp);
            }
            catch (Error e) {
                flag = false;
                e.printStackTrace();
            }
        } while (!flag);

        Integer type = 0;
        do {
            flag = true;
            System.out.print("Type of transaction\n1.Buy\n2.Sell(Nefunctional)\nOption: ");
            try {
                type = in.nextInt();
            }
            catch (InputMismatchException e) {
                flag = false;
                e.printStackTrace();
            }
        } while(!flag);

        Double amount = 0d;
        System.out.print("First currency name: ");
        in.nextLine();
        String firstCurrencyName = in.nextLine();
        Currency detectedFirsCurrency = managerCurrency.getCurrencyByName(firstCurrencyName);
        List<Pair<Integer, BankNote>> bankNotes = new ArrayList<>();
        System.out.print("Number of banknotes: ");
        Integer nOfBankNotes = in.nextInt();
        for (Integer i = 0; i < nOfBankNotes; ++i) {
            System.out.print("n: ");
            Integer n = in.nextInt();
            System.out.print("value: ");
            Integer value = in.nextInt();
            amount += n.doubleValue() * value.doubleValue();
            System.out.print("name: ");
            in.nextLine();
            String name = in.nextLine();
            bankNotes.add(new Pair<>(n, new BankNote(name, value)));
        }

        List<Pair<Integer, Coin>> coins = new ArrayList<>();
        System.out.print("Number of coins: ");
        Integer nOfCoins = in.nextInt();
        for (Integer i = 0; i < nOfCoins; ++i) {
            System.out.print("n: ");
            Integer n = in.nextInt();
            System.out.print("value: ");
            Integer value = in.nextInt();
            amount += n.doubleValue() * (value.doubleValue() / 100);
            System.out.print("name: ");
            in.nextLine();
            String name = in.nextLine();
            coins.add(new Pair<>(n, new Coin(name, value)));
        }

        Boolean typeOfTransaction = false;
        if (type == 2) {
            typeOfTransaction = true;
        }

        if (nOfCoins == 0)
            in.nextLine();

        System.out.print("Second currency name: ");
        String secondCurrencyName = in.nextLine();
        Currency detectedSecondCurrency = managerCurrency.getCurrencyByName(secondCurrencyName);
        return new Transaction(client, detectedFirsCurrency, detectedSecondCurrency, typeOfTransaction, new Triplet<>(bankNotes, coins, amount));
    }

    public void processTransaction(Transaction transaction, CurrencyManagerInterface managerCurrency,
                                   Map<Pair<Currency, Currency>, Pair<Double, Double>> currencyExchange,
                                   Map<Currency, Triplet<List<Pair<Integer, BankNote>>, List<Pair<Integer, Coin>>, Double>> amountCurrencyExchange) {

        Currency firstCurrency = transaction.getFirstCurrency();
        Currency secondCurrency = transaction.getSecondCurrency();
        Triplet<List<Pair<Integer, BankNote>>, List<Pair<Integer, Coin>>, Double> amountInBankNotesAndCoins;
        amountInBankNotesAndCoins = transaction.getAmountInBankNotesAndCoins();

        if (!transaction.getTypeOfTransaction()) {
            Double A = amountCurrencyExchange.get(secondCurrency).getValue2();
            Double valueToConvert = transaction.getAmountInBankNotesAndCoins().getValue2();
            Double amountConverted = valueToConvert * currencyExchange.get(new Pair<>(firstCurrency, secondCurrency)).getValue1();

            if (A < amountConverted) {
                throw new Error("Transaction can't be made!");
            }

            updateTransaction(transaction, valueToConvert, amountConverted);

            System.out.println("Transaction details: ");
            System.out.println(valueToConvert + " " + transaction.getFirstCurrency().getName() + " converted in " +
                    amountConverted + " " + transaction.getSecondCurrency().getName());

            List<Pair<Integer, BankNote>> bankNotes = amountCurrencyExchange.get(secondCurrency).getValue0();
            List<Pair<Integer, Coin>> coins = amountCurrencyExchange.get(secondCurrency).getValue1();
            for (Pair<Integer, BankNote> banknote : bankNotes) {
                Double value = banknote.getValue1().getValue().doubleValue();
                Integer n = banknote.getValue0();
                while (n > 0 && amountConverted > value) {
                    amountConverted -= value;
                    --n;
                }

                Integer indexBanknote = bankNotes.indexOf(banknote);
                bankNotes.set(indexBanknote, new Pair<>(n, banknote.getValue1()));
            }

            for (Pair<Integer, Coin> coin : coins) {
                Double value = coin.getValue1().getValue();
                Integer n = coin.getValue0();
                while (n > 0 && amountConverted > value) {
                    amountConverted -= value;
                    --n;
                }

                Integer indexBanknote = coins.indexOf(coin);
                coins.set(indexBanknote, new Pair<>(n, coin.getValue1()));
            }

            List<Pair<Integer, BankNote>> bankNotesAmountCurrencyExchange = null;
            List<Pair<Integer, Coin>> coinsAmountCurrencyExchange = null;
            boolean exist;
            try {
                bankNotesAmountCurrencyExchange = amountCurrencyExchange.get(firstCurrency).getValue0();
                coinsAmountCurrencyExchange = amountCurrencyExchange.get(firstCurrency).getValue1();
                exist = true;
            }
            catch (NullPointerException e) {
                exist = false;
            }

            bankNotes = amountInBankNotesAndCoins.getValue0();
            coins = amountInBankNotesAndCoins.getValue1();
            Double amount = amountInBankNotesAndCoins.getValue2();

            if (!exist) {
                amountCurrencyExchange.put(firstCurrency, new Triplet<>(bankNotes, coins, amount));
            }
            else {
                List<Pair<Integer, BankNote>> auxiliaryBankNotes = new ArrayList<>();
                List<Pair<Integer, Coin>> auxiliaryCoins = new ArrayList<>();
                Double totalAmount = 0d;
                for (Pair<Integer, BankNote> banknote1 : bankNotes) {
                    Integer n = banknote1.getValue0();
                    for (Pair<Integer, BankNote> banknote2 : bankNotesAmountCurrencyExchange) {
                        if (banknote1.getValue1().equals(banknote2.getValue1())) {
                            n += banknote2.getValue0();
                            break;
                        }
                    }

                    totalAmount += n.doubleValue() * banknote1.getValue1().getValue().doubleValue();
                    auxiliaryBankNotes.add(new Pair<>(n, banknote1.getValue1()));
                }

                for (Pair<Integer, BankNote> banknote1 : bankNotesAmountCurrencyExchange) {
                    boolean flag = false;
                    for (Pair<Integer, BankNote> banknote2 : bankNotes) {
                        if (banknote1.getValue1().equals(banknote2.getValue1())) {
                            flag = true;
                            break;
                        }
                    }

                    if (!flag) {
                        auxiliaryBankNotes.add(banknote1);
                    }
                }

                for (Pair<Integer, Coin> coin1 : coins) {
                    Integer n = coin1.getValue0();
                    for (Pair<Integer, Coin> coin2 : coinsAmountCurrencyExchange) {
                        if (coin1.getValue1().equals(coin2.getValue1())) {
                            n += coin2.getValue0();
                            break;
                        }
                    }

                    totalAmount += n.doubleValue() * coin1.getValue1().getValue();
                    auxiliaryCoins.add(new Pair<>(n, coin1.getValue1()));
                }

                for (Pair<Integer, Coin> coin1 : coinsAmountCurrencyExchange) {
                    boolean flag = false;
                    for (Pair<Integer, Coin> coin2 : coins) {
                        if (coin1.getValue1().equals(coin2.getValue1())) {
                            flag = true;
                            break;
                        }
                    }

                    if (!flag) {
                        auxiliaryCoins.add(coin1);
                    }
                }

                amountCurrencyExchange.put(firstCurrency, new Triplet<>(auxiliaryBankNotes, auxiliaryCoins, totalAmount));
            }
        }
    }

    private void updateTransaction(Transaction transaction, Double firstAmount, Double secondAmount) {
        historyTransaction.add(new Triplet<>(transaction, firstAmount, secondAmount));
    }

    public void showHistory() {
        historyTransaction.forEach(k -> {
            System.out.println(k.getValue0().getClient());
            System.out.println("Transaction details: ");
            System.out.println(k.getValue1() + " " + k.getValue0().getFirstCurrency().getName() + " converted in " + k.getValue2() + " " + k.getValue0().getSecondCurrency().getName());
        });
    }
}
