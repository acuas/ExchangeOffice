package Currency;

public class BankNote {
    private String name;
    private Integer value;

    public BankNote() {
        this.name = null;
        this.value = 0;
    }

    public BankNote(String name, Integer value) {
        this.name = name;
        setValue(value);
    }

    // Getters
    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    // private setter
    private void setValue(Integer value) {
        if (value >= 1) {
            this.value = value;
        }
        else {
            System.out.print("Throw Exception");
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof BankNote)) {
            return false;
        }

        BankNote c = (BankNote) o;

        return c.getValue().equals(getValue()) && c.getName().equals(getName());
    }

    @Override
    public String toString() {
        return value + " " + name;
    }
}