package Currency;

public class Coin {
    private String name;
    private Double value;

    public Coin() {
        this.name = null;
        this.value = 0d;
    }

    public Coin(String name, Integer value) {
        this.name = name;
        setValue(value);
    }

    // Getters
    public String getName() {
        return name;
    }

    public Double getValue() {
        return value;
    }

    // Private setter for value
    private void setValue(Integer value) {
        if (value >= 1) {
            this.value = (double)value / 100F;
        }
        else {
            System.out.println("Throw exception in Coin.");
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Coin)) {
            return false;
        }

        Coin c = (Coin) o;

        return c.getValue().equals(getValue()) && c.getName().equals(getName());
    }

    @Override
    public String toString() {
        if (value < 1) {
            return value * 100 + " " + name;
        }

        return value + " " + name;
    }
}
