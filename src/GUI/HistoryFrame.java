package GUI;

import DatabaseManagement.TransactionRepository;
import org.javatuples.Octet;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HistoryFrame extends JFrame {
    private MainFrame mainFrame;
    private Icon backButtonIcon = new ImageIcon("src/circled-left-filled.png");
    private String backButtonText = "Back";
    private JPanel listLabel;

    public HistoryFrame(String title, MainFrame mainFrame) {
        super(title);
        this.mainFrame = mainFrame;
        // Set layout manager
        // BorderLayout() let me to add components to the frame
        // Set layout manager
        setLayout(new BorderLayout());

        listLabel = new JPanel();
        listLabel.setLayout(new BoxLayout(listLabel, BoxLayout.PAGE_AXIS));

        // Create Swing component
        JButton backButton = new JButton(backButtonText, backButtonIcon);
        backButton.addActionListener(event -> goBackToMainFrame());

        // Add created components
        java.util.List<JLabel> labels = generateLabels();
        labels.forEach(label -> {
            label.setFont(new Font("Serif", Font.BOLD, 14));
            listLabel.add(label, Component.CENTER_ALIGNMENT);
        });

        listLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(backButton, BorderLayout.NORTH);
        add(listLabel, BorderLayout.CENTER);

        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private java.util.List<JLabel> generateLabels() {
        TransactionRepository t = new TransactionRepository();
        java.util.List<Octet<String, String, String, String, String, String, String, String>> texts = t.extractTransactions();
        java.util.List<JLabel> jLabels = new ArrayList<>();
        for (int i = 0; i < texts.size(); ++i) {
            jLabels.add(new JLabel(texts.get(i).toString()));
        }
        return jLabels;
    }

    private void goBackToMainFrame() {
        this.setVisible(false);
        mainFrame.setVisible(true);
    }
}
