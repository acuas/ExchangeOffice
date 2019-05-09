package CSV;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvWriterAudit {
    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    //CSV file header
    private static final String FILE_HEADER = "nume_actiune,timestamp";

    public static void writeCsvFile(String fileName, String nume_actiune, String date) {
        File file = new File(fileName);

        FileWriter fileWriter = null;

        try {
            if (file.length() == 0) {
                fileWriter = new FileWriter(fileName);
                //Write the CSV file header
                fileWriter.append(FILE_HEADER.toString());
            }
            else {
                fileWriter = new FileWriter(fileName, true);
            }
            //Add a new line separator
            fileWriter.append(NEW_LINE_SEPARATOR);

            //Write a new info to file
            fileWriter.append(nume_actiune);
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(date);
        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {

            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }

        }
    }
}
