package CSV;

import Currency.Coin;

public class CoinCsvProcessor implements CsvProcessor<Coin> {
    @Override
    public Coin process(Coin inData) {
        return inData;
    }
}
