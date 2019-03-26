package Currency;

import java.util.Comparator;

public class CoinComparator implements Comparator<Coin> {
    @Override
    public int compare(Coin a, Coin b) {
        return a.getValue() < b.getValue() ? -1 : a.getValue() == b.getValue() ? 0 : 1;
    }
}