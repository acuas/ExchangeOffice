package Transaction;

import Client.Client;
import Currency.BankNote;
import Currency.Coin;

import Currency.Currency;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.List;

public class Transaction {
    private Client client;
    private Boolean typeOfTransaction; // false -> buy, true -> sell
    private Currency firstCurrency;
    private Currency secondCurrency;
    private Triplet<List<Pair<Integer, BankNote>>, List<Pair<Integer, Coin>>, Double> amountInBankNotesAndCoins;

    public Transaction() {

    }

    public Transaction(Client client,
                       Currency firstCurrency,
                       Currency secondCurrency,
                       Boolean typeOfTransaction,
                       Triplet<List<Pair<Integer, BankNote>>, List<Pair<Integer, Coin>>, Double> amountInBankNotesAndCoins) {
        this.client = client;
        this.typeOfTransaction = typeOfTransaction;
        this.firstCurrency = firstCurrency;
        this.secondCurrency = secondCurrency;
        this.amountInBankNotesAndCoins = amountInBankNotesAndCoins;
    }

    public Client getClient() {
        return client;
    }

    public Boolean getTypeOfTransaction() {
        return typeOfTransaction;
    }

    public Currency getFirstCurrency() {
        return firstCurrency;
    }

    public Currency getSecondCurrency() {
        return secondCurrency;
    }

    public Triplet<List<Pair<Integer, BankNote>>, List<Pair<Integer, Coin>>, Double> getAmountInBankNotesAndCoins() {
        return amountInBankNotesAndCoins;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setTypeOfTransaction(Boolean typeOfTransaction) {
        this.typeOfTransaction = typeOfTransaction;
    }

    public void setFirstCurrency(Currency firstCurrency) {
        this.firstCurrency = firstCurrency;
    }

    public void setSecondCurrency(Currency secondCurrency) {
        this.secondCurrency = secondCurrency;
    }

    public void setAmountInBankNotesAndCoins(Triplet<List<Pair<Integer, BankNote>>, List<Pair<Integer,Coin>>, Double> amountInBankNotesAndCoins) {
        this.amountInBankNotesAndCoins = amountInBankNotesAndCoins;
    }
}
