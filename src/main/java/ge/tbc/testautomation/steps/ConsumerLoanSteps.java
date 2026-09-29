package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.components.NavigationComponent;
import ge.tbc.testautomation.pages.ConsumerLoanPage;

import java.util.regex.Pattern;

public class ConsumerLoanSteps {

    private final Page page;
    private final ConsumerLoanPage consumerLoanPage;
    private final NavigationComponent navigationComponent;

    public ConsumerLoanSteps(Page page) {
        this.page = page;
        this.consumerLoanPage = new ConsumerLoanPage(page);
        this.navigationComponent = new NavigationComponent(page);
    }

    public ConsumerLoanSteps openHomePage() {
        page.navigate("https://tbcbank.ge/en");
        return this;
    }

    public ConsumerLoanSteps openPersonalMenu() {
        navigationComponent.personalButton.hover();
        return this;
    }

    public ConsumerLoanSteps openConsumerLoan() {
        consumerLoanPage.consumerLoanButton.click();
        return this;
    }

    public ConsumerLoanSteps openTermsPage() {
        consumerLoanPage.termsButton.click();
        return this;
    }

    public ConsumerLoanSteps verifyLoanCalculatorIsDisplayed() {
        PlaywrightAssertions.assertThat(
                consumerLoanPage.loansCalculatorTab
        ).isVisible();

        PlaywrightAssertions.assertThat(
                consumerLoanPage.byAmountButton
        ).isVisible();

        return this;
    }

    public ConsumerLoanSteps enterLoanData(String amount, String period) {
        consumerLoanPage.amountInput.fill(amount);
        consumerLoanPage.periodInput.fill(period);

        return this;
    }

    public ConsumerLoanSteps verifyCalculationResults(
            String amount,
            String month,
            String monthlyContribution) {

        PlaywrightAssertions.assertThat(
                consumerLoanPage.calculatedAmount
        ).containsText(amount);

        PlaywrightAssertions.assertThat(
                consumerLoanPage.calculatedMonth
        ).hasText(month);

        PlaywrightAssertions.assertThat(
                consumerLoanPage.monthlyContribution
        ).containsText(monthlyContribution);

        PlaywrightAssertions.assertThat(
                consumerLoanPage.interestRate
        ).hasText(ConsumerLoanConstants.EXPECTED_INTEREST_RATE);

        PlaywrightAssertions.assertThat(
                consumerLoanPage.effectiveInterestRate
        ).hasText(ConsumerLoanConstants.EXPECTED_EFFECTIVE_INTEREST_RATE);

        return this;
    }

    public ConsumerLoanSteps clickApplyAndVerifyRedirect() {
        Page newPage = page.waitForPopup(
                () -> consumerLoanPage.applyButton.click()
        );

        newPage.waitForLoadState();

        PlaywrightAssertions.assertThat(newPage)
                .hasURL(Pattern.compile("^https://tbccredit\\.ge/.*"));

        return this;
    }

    public ConsumerLoanSteps verifyInterestRatesFromApi(
            String yearlyPercent,
            String effectivePercent) {

        PlaywrightAssertions.assertThat(
                consumerLoanPage.interestRate
        ).hasText("From " + yearlyPercent + "%");

        PlaywrightAssertions.assertThat(
                consumerLoanPage.effectiveInterestRate
        ).hasText("From " + effectivePercent + "%");

        return this;
    }
}