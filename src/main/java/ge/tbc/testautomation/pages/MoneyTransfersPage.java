package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

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

        moneyTransfersButton = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions()
                        .setName("Money Transfers")
                        .setExact(true)
        );

        feeCalculationTab = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions()
                        .setName("Remittance Fee Calculation")
                        .setExact(true)
        );

        amountInput = page
                .locator("tbcx-pw-money-transfer-fee-calculator")
                .locator("input");

        currencyDropdown = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions()
                        .setName("GEL")
                        .setExact(true)
        );

        eurOption = page
                .locator(".tbcx-dropdown-popover-item__title")
                .filter(new Locator.FilterOptions().setHasText("EUR"));

        countryDropdown = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions()
                        .setName("Choose a country")
        );

        georgiaOption = page.getByText(
                "Georgia",
                new Page.GetByTextOptions().setExact(true)
        );

        commissionCards = page.locator(
                "tbcx-pw-money-transfer-system-card"
        );
    }
}