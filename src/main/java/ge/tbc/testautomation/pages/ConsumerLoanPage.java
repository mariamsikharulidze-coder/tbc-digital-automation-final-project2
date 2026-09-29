package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import ge.tbc.testautomation.constants.Constants;

public class ConsumerLoanPage {

    public Locator consumerLoanButton;
    public Locator termsButton;
    public Locator loansCalculatorTab;
    public Locator byAmountButton;
    public Locator amountInput;
    public Locator periodInput;
    public Locator monthlyContribution;
    public Locator calculatedAmount;
    public Locator calculatedMonth;
    public Locator interestRate;
    public Locator effectiveInterestRate;
    public Locator applyButton;

    public ConsumerLoanPage(Page page) {

        consumerLoanButton = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions()
                        .setName(Constants.CONSUMER)
        );

        termsButton = page.locator(
                "a[href='/en/loans/consumer-loan/digital'] button"
        );

        loansCalculatorTab = page.getByText(
                Constants.LOANS_CALCULATOR,
                new Page.GetByTextOptions().setExact(true)
        );

        byAmountButton = page.getByText(
                Constants.BY_AMOUNT,
                new Page.GetByTextOptions().setExact(true)
        );

        amountInput = page.locator(
                "input[type='number'][min='200'][max='80000']"
        );

        periodInput = page.locator(
                "input[type='number'][min='3'][max='48']"
        );

        monthlyContribution = page.locator(
                ".tbcx-pw-calculated-info__number--new"
        );

        calculatedAmount = page
                .getByText(
                        Constants.AMOUNT,
                        new Page.GetByTextOptions().setExact(true)
                )
                .locator("..")
                .locator(".tbcx-pw-calculated-info__rows-item-info");

        calculatedMonth = page
                .getByText(
                        Constants.MONTH,
                        new Page.GetByTextOptions().setExact(true)
                )
                .locator("..")
                .locator(".tbcx-pw-calculated-info__rows-item-info");

        interestRate = page
                .getByText(
                        Constants.INTEREST_RATE,
                        new Page.GetByTextOptions().setExact(true)
                )
                .locator("..")
                .locator(".tbcx-pw-calculated-info__rows-item-info");

        effectiveInterestRate = page
                .getByText(
                        Constants.EFFECTIVE_INTEREST_RATE,
                        new Page.GetByTextOptions().setExact(true)
                )
                .locator("..")
                .locator(".tbcx-pw-calculated-info__rows-item-info");

        applyButton = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions()
                        .setName(Constants.APPLY)
                        .setExact(true)
        );
    }
}