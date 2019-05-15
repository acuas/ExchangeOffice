package DatabaseManagement;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

public class ReadDatabase<T> {
    private Map<String, Field> privateFields = new LinkedHashMap<>();
    private Map<String, Method> Methods = new LinkedHashMap<>();
    private Class<T> genericType;
    private boolean initCompleted;

    private String username = "sys as sysdba";
    private String password = "Oradoc_db1";

    public ReadDatabase(final Class<T> type) {
        this.genericType = type;
        initialize();
    }

    public List<T> readFromDatabase(String table_name) {
        List<T> data = new ArrayList<>();

        try {
            // Load the oracle sql driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Making a connection
            Connection con = DriverManager.getConnection
                    ("jdbc:oracle:thin:@//0.0.0.0:32769/ORCLCDB.localdomain", username, password);
            // Creating a Statement object for our DB Connection
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT name, value FROM " + table_name);
            while (rs.next()) {
                String name = rs.getString("name");
                Integer value = rs.getInt("value");
                T refObject = genericType.newInstance();

                callSetters(name, value, refObject);
                data.add(refObject);

            }
            statement.close();
            con.close();
        } catch (ClassNotFoundException
                | SQLException
                | InstantiationException
                | IllegalAccessException e) {
            e.printStackTrace();
        }

        return data;
    }

    private void callSetters(String name, Integer value, T refObject) throws IllegalAccessException {
        List<String> listOfMethodNames = new ArrayList<String>(Methods.keySet());

        for (String methodName : listOfMethodNames) {
            Method m = Methods.get(methodName);
            if (methodName.equals("setName")) {
                try {
                    m.setAccessible(true);
                    m.invoke(refObject, name);
                    m.setAccessible(false);
                } catch (InvocationTargetException x) {
                    x.printStackTrace();
                }
            }
            else if (methodName.equals("setValue")) {
                try {
                    m.setAccessible(true);
                    m.invoke(refObject, value);
                    m.setAccessible(false);
                } catch (InvocationTargetException x) {
                    x.printStackTrace();
                }
            }

        }
    }

    private void initialize() {
        if (!this.initCompleted) {
            Field[] allFields = genericType.getDeclaredFields();
            for (Field field : allFields) {
                if (Modifier.isPrivate(field.getModifiers())) {
                    privateFields.put(field.getName(), field);
                }
            }

            Method[] allMethods = genericType.getDeclaredMethods();
            for (Method method : allMethods) {
                Methods.put(method.getName(), method);
            }

            this.initCompleted = true;
        }
    }
}
