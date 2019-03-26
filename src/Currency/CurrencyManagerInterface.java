package Currency;

import org.javatuples.Pair;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

// Service Interface
public interface CurrencyManagerInterface {
    Set<Currency> getCurrencies();
    Map<Pair<Currency, Currency>, Double> getCurrencyConverter();
    Currency getCurrencyByName(String currencyName);
    void readCurrenciesFromFile();
    void createCurrency();
    void generateConverter() throws IOException;
    Double convert(Currency A, Currency B, Double value);
    @Override
    String toString();
}
