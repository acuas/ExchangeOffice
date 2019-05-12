package GUI;

import Exchange.CurrencyExchange;
import org.javatuples.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SchimbValutarFrame extends JFrame {
    private MainFrame mainFrame;
    private CurrencyExchange currencyExchange;
    private JPanel listLabel;
    private Icon backButtonIcon = new ImageIcon("src/circled-left-filled.png");
    private String backButtonText = "Back";

    SchimbValutarFrame(MainFrame mainFrame, CurrencyExchange currencyExchange, String title) {
        super(title);
        this.mainFrame = mainFrame;
        this.currencyExchange = currencyExchange;

        // Set layout manager
        setLayout(new BorderLayout());
        listLabel = new JPanel();
        listLabel.setLayout(new BoxLayout(listLabel, BoxLayout.PAGE_AXIS));

        // Create Swing component
        JButton backButton = new JButton(backButtonText, backButtonIcon);
        backButton.addActionListener(event -> goBackToMainFrame());

        java.util.List<JLabel> labels = generateLabels();
        labels.forEach(label -> {
            label.setFont(new Font("Serif", Font.BOLD, 14));
            listLabel.add(label, Component.CENTER_ALIGNMENT);
        });

        listLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(backButton, BorderLayout.NORTH);
        add(listLabel, BorderLayout.CENTER);

        // window setup
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private java.util.List<JLabel> generateLabels() {
        java.util.List<Pair<String, String>> currencyExchangePair= currencyExchange.getCurrencyExchangePair();
        java.util.List<JLabel> labels = new ArrayList<>();
        currencyExchangePair.forEach((pair) -> {
            labels.add(new JLabel(pair.getValue0()));
            labels.add(new JLabel(pair.getValue1()));
        });

        return labels;
    }

    private void goBackToMainFrame() {
        this.setVisible(false);
        mainFrame.setVisible(true);
    }
}
