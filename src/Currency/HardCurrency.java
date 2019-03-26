package Currency;

import java.util.List;

public class HardCurrency extends Currency {
    private Integer stabilityInValue; // like a score

    public HardCurrency() {
        // Nothing done yet
    }

    public HardCurrency(String name, List<Coin> coins, List<BankNote> bankNotes, Integer stabilityInValue) {
        super(name, coins, bankNotes);
        this.stabilityInValue = stabilityInValue;
    }

    public Integer getStabilityInValue() {
        return stabilityInValue;
    }

    @Override
    public String toString() {
        return super.toString() + "\nStability in value: " + this.getStabilityInValue();
    }
}
