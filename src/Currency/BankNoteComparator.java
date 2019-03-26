package Currency;

import java.util.Comparator;

public class BankNoteComparator implements Comparator<BankNote> {
    @Override
    public int compare(BankNote a, BankNote b) {
        return a.getValue() < b.getValue() ? -1 : a.getValue() == b.getValue() ? 0 : 1;
    }
}