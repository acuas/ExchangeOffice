package GUI;

import Currency.CurrencyManager;
import Exchange.CurrencyExchange;
import Transaction.TransactionManager;
import Transaction.TransactionManagerInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainFrame extends JFrame {
    private java.util.List<String> LABEL_BUTTONS = Arrays.asList(
            "Afisare valute",
            "Afisare schimb valutar",
            "Creare tranzactie noua",
            "Istoric tranzactii",
            "Actualizare curs valutar",
            "Actualizare convertor valutar",
            "Exit"
    );

    private afisareValuteFrame valutaFrame;
    private SchimbValutarFrame schimbValutarFrame;
    private java.util.List<JButton> buttonsMainFrame;
    private JPanel listPane;
    private JLabel topLabel;
    private CurrencyExchange currencyExchange;
    private TransactionManagerInterface transaction;


    public MainFrame(String title) throws IOException {
        super(title);
        currencyExchange = new CurrencyExchange();
        transaction = new TransactionManager();
        // Set layout manager
        // BorderLayout() let me to add components to the frame
        setLayout(new BorderLayout());

        listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));

        // Create Swing component
        // Create title as JLable
        topLabel = new JLabel(
                "Bun venit la casa de schimb valutar Aurelu'!",
                JLabel.CENTER);

        buttonsMainFrame = generateMainFrameButtons();
        for (JButton button : buttonsMainFrame) {
            listPane.add(button, Component.CENTER_ALIGNMENT);
        }

        listPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Add Swing components to content pane
        Container contentPane = getContentPane();

        contentPane.add(topLabel, BorderLayout.NORTH);
        contentPane.add(listPane, BorderLayout.CENTER);

        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private java.util.List<JButton> generateMainFrameButtons() {
        java.util.List<JButton> buttons = new java.util.ArrayList<>();
        for (String labelForButton : LABEL_BUTTONS) {
            // Create a new JButton
            JButton button = new JButton(labelForButton);
            // Align the button in CENTER of JPanel
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            // Set the maximum size of button to fill the entire GUI
            button.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
            // Add event listener
            switch (labelForButton) {
                case "Afisare valute": {
                    button.addActionListener(event -> afisareValute());
                    break;
                }
                case "Afisare schimb valutar": {
                    button.addActionListener(event -> afisareSchimbValutar());
                    break;
                }
                case "Exit": {
                    button.addActionListener(event -> exitApp());
                    break;
                }
            }

            // Add the button created to the list of buttons
            buttons.add(button);
        }
        return buttons;
    }

    private void afisareValute() {
        this.setVisible(false);
        if (valutaFrame == null) {
            valutaFrame = new afisareValuteFrame(this, this.getTitle(), currencyExchange.getManagerCurrency());
        } else {
            valutaFrame.setVisible(true);
        }
    }

    private void afisareSchimbValutar() {
        this.setVisible(false);
        if (schimbValutarFrame == null) {
            schimbValutarFrame = new SchimbValutarFrame(this, currencyExchange, this.getTitle());
        }
        else {
            schimbValutarFrame.setVisible(true);
        }
    }

    private void exitApp() {
        System.exit(0);
    }
}
