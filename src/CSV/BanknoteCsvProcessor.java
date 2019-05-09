package CSV;

import Currency.BankNote;

public class BanknoteCsvProcessor implements CsvProcessor<BankNote> {
    @Override
    public BankNote process(BankNote inData) {
        return inData;
    }
}
