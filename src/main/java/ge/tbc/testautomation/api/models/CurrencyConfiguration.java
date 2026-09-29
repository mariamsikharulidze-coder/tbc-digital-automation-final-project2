package ge.tbc.testautomation.api.models;

public class CurrencyConfiguration {

    private double yearlyPercent;
    private int effectivePercent;

    public double getYearlyPercent() {
        return yearlyPercent;
    }

    public void setYearlyPercent(double yearlyPercent) {
        this.yearlyPercent = yearlyPercent;
    }

    public int getEffectivePercent() {
        return effectivePercent;
    }

    public void setEffectivePercent(int effectivePercent) {
        this.effectivePercent = effectivePercent;
    }
}