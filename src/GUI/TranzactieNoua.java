package GUI;

import Exchange.CurrencyExchange;
import Transaction.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class TranzactieNoua extends JFrame {
    private MainFrame mainFrame;
    private CurrencyExchange currencyExchange;
    private DetailsPanel detailsPanel;
    private CalcularePanel calcularePanel;
    private Icon backButtonIcon = new ImageIcon("src/circled-left-filled.png");
    private String backButtonText = "Back";

    public TranzactieNoua(MainFrame mainFrame, CurrencyExchange currencyExchange, String title) {
        super(title);
        this.mainFrame = mainFrame;
        this.currencyExchange = currencyExchange;

        // set layout
        setLayout(new BorderLayout());

        // Add swing component
        detailsPanel = new DetailsPanel();
        calcularePanel = new CalcularePanel(currencyExchange.getManagerCurrency(), detailsPanel);
        detailsPanel.setCalcularePanel(calcularePanel);
        calcularePanel.setCurrencyExchange(currencyExchange);
        JButton backButton = getBackButton(mainFrame);

        add(detailsPanel, BorderLayout.WEST);
        add(calcularePanel, BorderLayout.EAST);
        add(backButton, BorderLayout.SOUTH);

        // window settings
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @NotNull
    private JButton getBackButton(MainFrame mainFrame) {
        JButton backButton = new JButton(backButtonText, backButtonIcon);
        backButton.addActionListener(event -> {
            this.setVisible(false);
            mainFrame.setVisible(true);
        });

        return backButton;
    }
}
