package CSV;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import Currency.*;
import java.util.*;

public class GenericCsvReader<T> {
    private String separator;
    private String file;
    private Map<String, Field> privateFields = new LinkedHashMap<>();
    private Class<T> genericType;
    private List<T> data;
    private List<String> order;
    private List<String> headers;
    private boolean initCompleted;
    private boolean hasHeader;

    public List<String> getHeaders() {
        return headers;
    }

    public List<String> getOrder() {
        return order;
    }

    public GenericCsvReader<T> setOrder(List<String> order) {
        this.order = order;
        return this;
    }

    public GenericCsvReader<T> read(List<String> order) {
        this.setOrder(order);
        initialize();
        return this;
    }

    public GenericCsvReader<T> read() {
        initialize();
        return this;
    }


    public List<T> getData() {
        if (data == null) {
            data = new ArrayList<>();
        }
        return data;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public void setHasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    public GenericCsvReader(final Class<T> type, String file, boolean hasHeader) {
        this.file = file;
        this.hasHeader = hasHeader;
        this.genericType = type;
        this.separator = ",";
    }

    public GenericCsvReader(final Class<T> type, String file, boolean hasHeader, String separator) {
        this.file = file;
        this.hasHeader = hasHeader;
        this.genericType = type;
        this.separator = separator;
    }

    private void initialize() {
        if (!this.initCompleted) {
            Field[] allFields = genericType.getDeclaredFields();
            for (Field field : allFields) {
                if (Modifier.isPrivate(field.getModifiers())) {
                    privateFields.put(field.getName(), field);
                }
            }

            try {
                readData();
            } catch (InstantiationException | IllegalAccessException e) {
                this.initCompleted = false;
                e.printStackTrace();
            }
            this.initCompleted = true;
        }
    }

    private void readData() throws InstantiationException, IllegalAccessException {

        BufferedReader  reader = null;
        String line =null;
        try{
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {

                List<String> row = Arrays.asList(line.split(separator));

                if (this.hasHeader){
                    setHeaders(row);;
                    this.hasHeader = false;
                    continue;
                }

                T refObject = genericType.newInstance();
                int index = 0;

                List<String> listOfFieldNames = (null != getOrder()) ? getOrder() : new ArrayList<String>(privateFields.keySet());

                for(String fieldName : listOfFieldNames) {
                    if( index >= row.size()) {
                        break;
                    }
                    assign(refObject, privateFields.get(fieldName), row.get(index++));
                }
                getData().add(refObject);
            }

            reader.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
        finally{

            try {
                reader.close();
            }
            catch ( Exception e ) {

            }
        }
    }

    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Field assign(T refObject, Field field, String value) throws IllegalArgumentException, IllegalAccessException {
        field.setAccessible(true);
        if (genericType == Coin.class && isDouble(value)) {
            field.set(refObject, Double.parseDouble(value) / 100F);
        }
        else if (genericType == BankNote.class && isInteger(value)) {
            field.set(refObject, Integer.parseInt(value));
        }
        else {
            field.set(refObject, value);
        }
        field.setAccessible(false);
        return field;
    }

    public GenericCsvReader<T> process(CsvProcessor<T> processsor) {
        if(!this.initCompleted) {
            initialize();
        }

        if (null != processsor) {
            List<T> list = getData();
            for (T obj : list) {
                obj = processsor.process(obj);
            }
        }
        return this;
    }

}