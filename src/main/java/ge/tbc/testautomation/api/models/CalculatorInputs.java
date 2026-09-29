package ge.tbc.testautomation.api.models;

import java.util.List;

public class CalculatorInputs {

    private List<CurrencyConfiguration> currencyConfiguration;

    public List<CurrencyConfiguration> getCurrencyConfiguration() {
        return currencyConfiguration;
    }

    public void setCurrencyConfiguration(
            List<CurrencyConfiguration> currencyConfiguration) {
        this.currencyConfiguration = currencyConfiguration;
    }
}