package CSV;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class GenericCsv<T> {
    private String separator;
    private String file;
    private Map<String, Field> privateFields = new LinkedHashMap<>();
    private Class<T> genericType;
    private List<T> data;
    private List<String> order;
    private List<String> headers;
    private boolean initCompleted;
    private boolean hasHeader;

    public List<String> getOrder() {
        return order;
    }

    public GenericCsv<T> setOrder(List<String> order) {
        this.order = order;
        return this;
    }

    public GenericCsv<T> read(List<String> order) {
        this.setOrder(order);
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

    public GenericCsv(final Class<T> type, String file, boolean hasHeader) {
        this.file = file;
        this.hasHeader = hasHeader;
        this.genericType = type;
        this.separator = ",";
    }

    public GenericCsv(final Class<T> type, String file, boolean hasHeader, String separator) {
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
            }
            this.initCompleted = true;
        }
    }

    private void readData() throws InstantiationException, IllegalAccessException {
        try {
            InputStream in = new FileInputStream(file);
            Reader reader = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                List<String> row = Arrays.asList(line.split(separator));
                if (hasHeader) {
                    setHeaders(row);
                    hasHeader = false;
                    continue;
                }

                T refObject = genericType.newInstance();
                int index = 0;

                List<String> listOfFieldNames = (null != getOrder()) ? getOrder() : new ArrayList<>(privateFields.keySet());
                for (String fieldName : listOfFieldNames) {
                    if (index >= row.size()) {
                        break;
                    }
                    assign(refObject, privateFields.get(fieldName), row.get(index++));
                }
                getData().add(refObject);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Field assign(T refObject, Field field, String value) throws IllegalArgumentException, IllegalAccessException {
        field.setAccessible(true);
        field.set(refObject, value);
        field.setAccessible(false);
        return field;
    }

    /*
    public GenericCSV<T> process(CsvProcessor<T> processsor) {
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
    */
}