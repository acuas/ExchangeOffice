package GUI;

import Currency.CurrencyManagerInterface;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;

class afisareValuteFrame extends JFrame {
    private JEditorPane currencyEditor;
    private CurrencyManagerInterface currencyManager;
    private MainFrame mainFrame;
    private Icon backButtonIcon = new ImageIcon("src/circled-left-filled.png");
    private String backButtonText = "Back";

    afisareValuteFrame(MainFrame mainFrame, String title, CurrencyManagerInterface currencyManager) {
        super(title);
        this.mainFrame = mainFrame;
        this.currencyManager = currencyManager;

        // Set layout manager
        // BorderLayout() let me to add components to the frame
        setLayout(new BorderLayout());

        // Create Swing component
        createLabelWithManagerCurrency();
        JButton backButton = new JButton(backButtonText, backButtonIcon);
        backButton.addActionListener(event -> goBackToMainFrame());

        // Add created components
        add(currencyEditor);
        add(backButton, BorderLayout.PAGE_START);

        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createLabelWithManagerCurrency() {
        currencyEditor = new JEditorPane("text/plain", currencyManager.toString());
        currencyEditor.setEditable(false);
        currencyEditor.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
    }

    private void goBackToMainFrame() {
        this.setVisible(false);
        mainFrame.setVisible(true);
    }
}

