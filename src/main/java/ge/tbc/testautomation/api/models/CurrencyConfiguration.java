package ge.tbc.testautomation.api.models;

import lombok.Data;

@Data
public class CurrencyConfiguration {

    private double yearlyPercent;
    private int effectivePercent;
}