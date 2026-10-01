package ge.tbc.testautomation.api.models;

import lombok.Data;

import java.util.List;

@Data
public class CalculatorInputs {

    private List<CurrencyConfiguration> currencyConfiguration;
}