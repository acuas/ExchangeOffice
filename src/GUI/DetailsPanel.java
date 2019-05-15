package GUI;

import Client.Client;
import Transaction.Transaction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

class DetailsPanel extends JPanel {
    private java.util.List<String> FORM_LABELS_TEXT = Arrays.asList(
            "Nume:",
            "Prenume:",
            "CNP:",
            "Tipul tranzactiei:",
            "Converteste din:",
            "in:"
    );

    private java.util.List<String> TYPE_OF_TRANSACTION = Arrays.asList(
            "Buy",
            "Sell"
    );

    private java.util.List<String> OPTIONS_CURRENCY = Arrays.asList(
            "Dolar american",
            "Euro",
            "Lira sterlină",
            "RON"
    );

    private java.util.List<JTextField> textFields;

    private JComboBox optionCurrency1, optionCurrency2, typeOfTransaction;
    private CalcularePanel calcularePanel;

    JPanel getCalcularePanel() {
        return calcularePanel;
    }

    void setCalcularePanel(CalcularePanel calcularePanel) {
        this.calcularePanel = calcularePanel;
    }

    DetailsPanel() {
        Dimension size = getPreferredSize();
        size.width = 350;
        setPreferredSize(size);

        // set title of JPanel
        setBorder(BorderFactory.createTitledBorder("Detalii tranzactie"));

        // set layout
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        //// First column ////
        Integer gridyAux = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.weightx = 0.5;
        gc.weighty = 0.5;
        gc.gridx = 0;
        gc.gridy = gridyAux;
        java.util.List<JLabel> labels = generateLabels();
        for (JLabel label : labels) {
            add(label, gc);
            gc.gridy = ++gridyAux;
        }

        //// Second column ////
        gridyAux = 0;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 1;
        gc.gridy = gridyAux;
        java.util.List<JTextField> textFields = generateTextFields();
        for (JTextField textField : textFields) {
            add(textField, gc);
            gc.gridy = ++gridyAux;
        }

        typeOfTransaction = new JComboBox(TYPE_OF_TRANSACTION.toArray());
        add(typeOfTransaction, gc);
        gc.gridy = ++gridyAux;

        optionCurrency1 = new JComboBox(OPTIONS_CURRENCY.toArray());
        add(optionCurrency1, gc);
        gc.gridy = ++gridyAux;
        optionCurrency2 = new JComboBox(OPTIONS_CURRENCY.toArray());
        optionCurrency2.setSelectedIndex(1);
        add(optionCurrency2, gc);

        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 0;
        gc.gridy = 7;
        JButton blocheazaDate = new JButton("Blocheaza date");
        blocheazaDate.addActionListener(event -> blockDetailsPanel());
        add(blocheazaDate, gc);

    }

    private void blockDetailsPanel() {
        Component[] com = this.getComponents();
        for (int i = 0; i < com.length; ++i) {
            com[i].setEnabled(false);
        }

        String currency1 = OPTIONS_CURRENCY.get(optionCurrency1.getSelectedIndex());
        String currency2 = OPTIONS_CURRENCY.get(optionCurrency2.getSelectedIndex());
        Client client = new Client();
        client.setFirstName(textFields.get(0).getText());
        client.setLastName(textFields.get(1).getText());
        client.setCnp(textFields.get(2).getText());
        Boolean type = typeOfTransaction.getSelectedIndex() != 0 ? true : false;
        Transaction t = new Transaction();
        t.setClient(client);
        t.setTypeOfTransaction(type);
        calcularePanel.setTransaction(t);
        calcularePanel.setCurrencyFrom(currency1);
        calcularePanel.setCurrencyTo(currency2);
        calcularePanel.addLabels();
    }

    private java.util.List<JLabel> generateLabels() {
        java.util.List<JLabel> labels = new ArrayList<>();
        for (String s : FORM_LABELS_TEXT) {
            labels.add(new JLabel(s));
        }

        return labels;
    }

    private java.util.List<JTextField> generateTextFields() {
        textFields = new ArrayList<>();
        for (int i = 0; i < FORM_LABELS_TEXT.size() - 3; ++i) {
            textFields.add(new JTextField(15));
        }

        return textFields;
    }
}
