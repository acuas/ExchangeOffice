package CSV;

public interface CsvProcessor<T> {
    public T process(T inData);
}
