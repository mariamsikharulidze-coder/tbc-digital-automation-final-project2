package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.components.NavigationComponent;
import ge.tbc.testautomation.pages.MoneyTransfersPage;

public class MoneyTransfersSteps {

    Page page;
    MoneyTransfersPage moneyTransfersPage;
    NavigationComponent navigationComponent;

    public MoneyTransfersSteps(Page page) {
        this.page = page;
        this.moneyTransfersPage = new MoneyTransfersPage(page);
        this.navigationComponent = new NavigationComponent(page);
    }

    public MoneyTransfersSteps openHomePage() {
        page.navigate("https://tbcbank.ge/en");
        return this;
    }

    public MoneyTransfersSteps openPersonalMenu() {
        navigationComponent.personalButton.hover();
        return this;
    }

    public MoneyTransfersSteps openMoneyTransfersPage() {
        moneyTransfersPage.moneyTransfersButton.click();
        return this;
    }

    public MoneyTransfersSteps openFeeCalculation() {
        moneyTransfersPage.feeCalculationTab.click();
        return this;
    }

    public MoneyTransfersSteps enterAmount(String amount) {
        moneyTransfersPage.amountInput.fill(amount);
        return this;
    }

    public MoneyTransfersSteps selectCurrency() {
        moneyTransfersPage.currencyDropdown.click();

        moneyTransfersPage.eurOption.evaluate(
                "element => element.click()"
        );

        return this;
    }

    public MoneyTransfersSteps selectCountry() {
        moneyTransfersPage.countryDropdown.click();
        moneyTransfersPage.georgiaOption.click();
        return this;
    }


    public Response selectCountryAndCaptureNetworkResponse() {

        moneyTransfersPage.countryDropdown.click();

        return page.waitForResponse(
                response ->
                        response.url()
                                .contains("/api/v1/moneyTransfer/fees")
                                && response.request()
                                .method()
                                .equals("GET"),
                () -> moneyTransfersPage.georgiaOption.click()
        );
    }

    public MoneyTransfersSteps verifyCommissionResults() {
        PlaywrightAssertions.assertThat(
                moneyTransfersPage.commissionCards.first()
        ).isVisible();

        return this;
    }
}