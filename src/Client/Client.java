package Client;

import org.jetbrains.annotations.Contract;

public class Client {
    private static final Integer cnpLength = 13;
    private String firstName;
    private String lastName;
    private String cnp;

    public Client() {
        // Nothing done yet
    }

    public Client(String firstName, String lastName, String cnp) {
        setFirstName(firstName);
        setLastName(lastName);
        setCnp(cnp);
    }

    // Function to check String for only Alphabets
    @Contract("null -> false")
    public static boolean isStringOnlyAlphabet(String str) {
        return ((str != null))
                && (!str.equals(""))
                && (str.chars().allMatch(Character::isLetter));
    }

    @Contract("null -> false")
    public static boolean isStringOnlyDigit(String str) {
        return ((str != null))
                && (!str.equals(""))
                && (str.chars().allMatch(Character::isDigit));
    }

    // Getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (isStringOnlyAlphabet(firstName))
            this.firstName = firstName;
        else {
            throw new Error("First Name must contain just letters!!");
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (isStringOnlyAlphabet(lastName))
            this.lastName = lastName;
        else {
            throw new Error("Last Name must contain just letters!");
        }
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        if (isStringOnlyDigit(cnp) && cnp.length() == cnpLength) {
            this.cnp = cnp;
        }
        else {
            throw new Error("CNP is in invalid format!");
        }
    }

    @Override
    public String toString() {
        return "Client: " + firstName + " " + lastName + "\nCNP: " + cnp;
    }
}
