package Currency;
import java.util.*;

// Define a type of currency
public class Currency {
    protected String name;
    protected List<Coin> coins;
    protected List<BankNote> bankNotes;

    Currency() {
        // Nothing done yet
    }

    Currency(String name, List<Coin> coins, List<BankNote> bankNotes) {
        this.name = name;
        this.coins = coins;
        this.bankNotes = bankNotes;
    }

    public String getName() {
        return name;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public List<BankNote> getBankNotes() {
        return bankNotes;
    }

    @Override
    public String toString() {
        return "Currency name: " + this.getName() +
                "\nCoins " + this.getCoins() +
                "\nBanknotes " + this.getBankNotes() + "\n";
    }
}
