package test;

/**
 * Example class for demo of ObjectShell
 * 
 * @author deuscs01
 * 
 */
public class Sample {

    // some default values for testing
    private String name = "John Doe";

    private Integer income = 85000;

    private double alpha = 1e-9d;

    public Sample() {
        super();
        this.name = this.name;
        this.income = this.income;
        this.alpha = this.alpha;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getIncome() {
        return this.income;
    }

    public void setIncome(final Integer income) {
        this.income = income;
    }

    public double getAlpha() {
        return this.alpha;
    }

    public void setAlpha(final double alpha) {
        this.alpha = alpha;
    }

    @Override
    public String toString() {
        return "Sample [name=" + this.name + ", income=" + this.income + ", alpha=" + this.alpha + "]";
    }

}
