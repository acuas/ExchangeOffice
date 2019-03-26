package Transaction;

import Currency.Currency;
import Currency.BankNote;
import Currency.Coin;
import Currency.CurrencyManagerInterface;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.List;
import java.util.Map;

public interface TransactionManagerInterface {
    Transaction createTransaction(CurrencyManagerInterface managerCurrency);
    void processTransaction(Transaction transaction,
                            CurrencyManagerInterface managerCurrency,
                            Map<Pair<Currency, Currency>, Pair<Double, Double>> currencyExchange,
                            Map<Currency, Triplet<List<Pair<Integer, BankNote>>, List<Pair<Integer, Coin>>, Double>> amountCurrencyExchange);

    void showHistory();
}