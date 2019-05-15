package DatabaseManagement;

import Client.Client;
import Currency.Currency;

public class TransactionModel {
    private Client client;
    private Boolean typeOfTransaction; // false -> buy, true -> sell
    private Currency firstCurrency;
    private Currency secondCurrency;
    private double amountFrom;
    private double amountTo;

    public TransactionModel() {

    }

    public TransactionModel(Client client, Boolean typeOfTransaction, Currency firstCurrency, Currency secondCurrency, double amountFrom, double amountTo) {
        this.client = client;
        this.typeOfTransaction = typeOfTransaction;
        this.firstCurrency = firstCurrency;
        this.secondCurrency = secondCurrency;
        this.amountFrom = amountFrom;
        this.amountTo = amountTo;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Boolean getTypeOfTransaction() {
        return typeOfTransaction;
    }

    public void setTypeOfTransaction(Boolean typeOfTransaction) {
        this.typeOfTransaction = typeOfTransaction;
    }

    public Currency getFirstCurrency() {
        return firstCurrency;
    }

    public void setFirstCurrency(Currency firstCurrency) {
        this.firstCurrency = firstCurrency;
    }

    public Currency getSecondCurrency() {
        return secondCurrency;
    }

    public void setSecondCurrency(Currency secondCurrency) {
        this.secondCurrency = secondCurrency;
    }

    public double getAmountFrom() {
        return amountFrom;
    }

    public void setAmountFrom(double amountFrom) {
        this.amountFrom = amountFrom;
    }

    public double getAmountTo() {
        return amountTo;
    }

    public void setAmountTo(double amountTo) {
        this.amountTo = amountTo;
    }
}
