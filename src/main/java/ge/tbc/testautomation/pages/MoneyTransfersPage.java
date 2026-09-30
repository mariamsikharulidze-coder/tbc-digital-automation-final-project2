package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.constants.Constants;

public class MoneyTransfersPage {

    public Locator moneyTransfersButton;
    public Locator feeCalculationTab;
    public Locator amountInput;
    public Locator currencyDropdown;
    public Locator eurOption;
    public Locator countryDropdown;
    public Locator georgiaOption;
    public Locator commissionCards;

    public MoneyTransfersPage(Page page) {


        moneyTransfersButton = page.locator(
                "(//button[normalize-space()='" +
                        Constants.MONEY_TRANSFERS + "'])[1]"
        );


        feeCalculationTab = page.locator(
                "//button[normalize-space()='" +
                        Constants.FEE_CALCULATION + "']"
        );

        // Amount input
        amountInput = page.locator(
                "//tbcx-pw-money-transfer-fee-calculator//input"
        );


        currencyDropdown = page.locator(
                "//button[normalize-space()='" +
                        Constants.DEFAULT_CURRENCY + "']"
        );


        eurOption = page.locator(
                "//*[@class='tbcx-dropdown-popover-item__title'" +
                        " and normalize-space()='" +
                        Constants.CURRENCY + "']"
        );

        countryDropdown = page.locator(
                "//button[contains(normalize-space(),'" +
                        Constants.COUNTRY_DROPDOWN + "')]"
        );

        // Georgia option
        georgiaOption = page.locator(
                "//*[@class='tbcx-dropdown-popover-item__title'" +
                        " and normalize-space()='" +
                        Constants.COUNTRY + "']"
        );

        commissionCards = page.locator(
                "//tbcx-pw-money-transfer-system-card"
        );
    }
}