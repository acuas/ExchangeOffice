package GUI;

import Currency.Coin;
import Currency.Currency;
import Currency.BankNote;
import Currency.CurrencyManagerInterface;
import DatabaseManagement.TransactionModel;
import DatabaseManagement.TransactionRepository;
import Exchange.CurrencyExchange;
import Transaction.Transaction;
import org.javatuples.Pair;
import java.text.DecimalFormat;
import org.javatuples.Triplet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


class CalcularePanel extends JPanel {
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private CurrencyManagerInterface currencyManager;
    private String CurrencyFrom, CurrencyTo;
    private java.util.List<JLabel> labelsCurrencyFrom, labelsCurrencyTo;
    private java.util.List<JTextField> textFieldsCurrencyFrom, textFieldsCurrencyTo;
    private JTextField textFieldTotalFrom, textFieldTotalTo;
    private DetailsPanel detailsPanel;
    private GridBagConstraints gc;
    private Transaction transaction;
    private Currency firstCurrency;
    private Currency secondCurrency;
    private CurrencyExchange currencyExchange;

    public CurrencyExchange getCurrencyExchange() {
        return currencyExchange;
    }

    public void setCurrencyExchange(CurrencyExchange currencyExchange) {
        this.currencyExchange = currencyExchange;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    String getCurrencyFrom() {
        return CurrencyFrom;
    }

    void setCurrencyFrom(String currencyFrom) {
        CurrencyFrom = currencyFrom;
    }

    String getCurrencyTo() {
        return CurrencyTo;
    }

    void setCurrencyTo(String currencyTo) {
        CurrencyTo = currencyTo;
    }

    CalcularePanel(CurrencyManagerInterface currencyManager, DetailsPanel detailsPanel) {
        this.currencyManager = currencyManager;
        this.detailsPanel = detailsPanel;
        Dimension size = getPreferredSize();
        size.width = 450;
        setPreferredSize(size);

        // set title of JPanel
        setBorder(BorderFactory.createTitledBorder("Calculare tranzactie"));

        // set layout
        setLayout(new GridBagLayout());
        gc = new GridBagConstraints();
    }

    private java.util.List<JLabel> generateLabelsForCurrencies(Currency currency) {
        java.util.List<JLabel> labelsForCurrencies = new ArrayList<>();
        for (Coin coin : currency.getCoins()) {
            labelsForCurrencies.add(new JLabel(coin.getValue() + " " + coin.getName()));
        }

        for (BankNote bankNote : currency.getBankNotes()) {
            labelsForCurrencies.add(new JLabel(bankNote.getValue().toString() + " " + bankNote.getName()));
        }
        //java.util.List<Coin> coins
        return labelsForCurrencies;
    }

    void addLabels() {
        //// First column ////
        Integer gridyAux = 0, gridxAux = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.weightx = 0.5;
        gc.weighty = 0.5;
        gc.gridx = 0;
        gc.gridy = gridyAux;
        firstCurrency = currencyManager.getCurrencyByName(getCurrencyFrom());
        transaction.setFirstCurrency(firstCurrency);
        utilLabels(gridyAux, gridxAux, firstCurrency, 1);
        JButton buttonCalculeaza = new JButton("Converteste");
        buttonCalculeaza.addActionListener(event -> clickButton());
        this.add(buttonCalculeaza, gc);

        this.add(buttonCalculeaza, gc);
        gridyAux = 0;
        gridxAux = 2;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = gridxAux;
        gc.gridy = gridyAux;
        secondCurrency = currencyManager.getCurrencyByName(getCurrencyTo());
        transaction.setSecondCurrency(secondCurrency);
        utilLabels(gridyAux, gridxAux, secondCurrency, 2);

        this.validate();
        this.repaint();
    }

    private void utilLabels(Integer gridyAux, Integer gridxAux, Currency currency, Integer option) {
        java.util.List<JLabel> labels = generateLabelsForCurrencies(currency);
        java.util.List<JTextField> textFields = new ArrayList<>();
        for (JLabel label : labels) {
            this.add(label, gc);
            gc.gridx = gridxAux + 1;
            gc.anchor = GridBagConstraints.LINE_START;
            JTextField auxTextField = new JTextField(6);
            if (option != 1) {
                auxTextField.setEditable(false);
            }
            auxTextField.addKeyListener(
                    new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent keyEvent) {

                        }

                        @Override
                        public void keyPressed(KeyEvent e) {
                            if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                                Double total = 0.0d;
                                for (int i = 0; i < labelsCurrencyFrom.size(); ++i) {
                                    Double value1 =  Double.parseDouble(labelsCurrencyFrom.get(i).getText().split(" ", 2)[0]);
                                    Double value2 = 0.0d;
                                    try {
                                        value2 = Double.parseDouble(textFieldsCurrencyFrom.get(i).getText());
                                    } catch (NumberFormatException err) {
                                        //err.printStackTrace();
                                    }
                                    total += value1 * value2;
                                }

                                textFieldTotalFrom.setText(total.toString());
                            }
                        }

                        @Override
                        public void keyReleased(KeyEvent keyEvent) {

                        }
                    }
            );
            textFields.add(auxTextField);
            this.add(auxTextField, gc);
            gc.anchor = GridBagConstraints.LINE_END;
            gc.gridx = gridxAux;
            gc.gridy = ++gridyAux;
        }

        this.add(new JLabel("Total: "), gc);
        gc.gridx = gridxAux + 1;
        gc.anchor = GridBagConstraints.LINE_START;
        JTextField auxTextField = new JTextField(6);
        auxTextField.setEditable(false);
        auxTextField.setText("0");
        this.add(auxTextField, gc);
        gc.gridy++;

        if (option == 1) {
            labelsCurrencyFrom = labels;
            textFieldsCurrencyFrom = textFields;
            textFieldTotalFrom = auxTextField;
        }
        else {
            labelsCurrencyTo = labels;
            textFieldsCurrencyTo = textFields;
            textFieldTotalTo = auxTextField;
        }
    }

    private void clickButton() {
        Double valueToConvert = Double.parseDouble(textFieldTotalFrom.getText());

        Map<Pair<Currency, Currency>, Pair<Double, Double>> currencyExchangeTmp;
        currencyExchangeTmp = currencyExchange.getCurrencyExchange();
        Double amountConverted = valueToConvert * currencyExchangeTmp.get(new Pair<>(firstCurrency, secondCurrency)).getValue1();
        textFieldTotalTo.setText(df2.format(amountConverted));
        TransactionModel t = new TransactionModel();
        t.setClient(transaction.getClient());
        t.setFirstCurrency(firstCurrency);
        t.setSecondCurrency(secondCurrency);
        t.setTypeOfTransaction(transaction.getTypeOfTransaction());
        t.setAmountFrom(valueToConvert);
        t.setAmountTo(amountConverted);
        TransactionRepository transactionRepository = new TransactionRepository();
        transactionRepository.addTransaction(t);
    }

}
