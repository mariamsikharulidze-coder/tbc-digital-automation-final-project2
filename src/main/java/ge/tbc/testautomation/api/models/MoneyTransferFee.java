package ge.tbc.testautomation.api.models;

public class MoneyTransferFee {

    private String mtSystem;
    private double fee;

    public String getMtSystem() {
        return mtSystem;
    }

    public void setMtSystem(String mtSystem) {
        this.mtSystem = mtSystem;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}