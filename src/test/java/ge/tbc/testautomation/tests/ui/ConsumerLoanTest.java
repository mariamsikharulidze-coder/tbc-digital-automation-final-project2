package ge.tbc.testautomation.tests.ui;

import ge.tbc.testautomation.api.models.ConsumerLoanResponse;
import ge.tbc.testautomation.api.models.CurrencyConfiguration;
import ge.tbc.testautomation.api.models.SectionComponent;
import ge.tbc.testautomation.constants.Constants;
import ge.tbc.testautomation.database.models.LoanData;
import ge.tbc.testautomation.steps.ConsumerLoanSteps;
import ge.tbc.testautomation.tests.Base.BaseTest;
import ge.tbc.testautomation.tests.data.LoanDataProvider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ConsumerLoanTest extends BaseTest {

    private ConsumerLoanSteps consumerLoanSteps;

    @BeforeMethod
    public void initializeSteps() {
        consumerLoanSteps = new ConsumerLoanSteps(page);
    }

    @Test(
            description = "SCRUM-T55 | Verify Consumer Loan Calculator Functionality"
    )
    public void verifyConsumerLoanCalculation() {

        consumerLoanSteps
                .openHomePage()
                .openPersonalMenu()
                .openConsumerLoan()
                .openTermsPage()
                .verifyLoanCalculatorIsDisplayed()
                .enterLoanData(
                        Constants.LOAN_AMOUNT,
                        Constants.LOAN_PERIOD
                )
                .verifyCalculationResults(
                        Constants.LOAN_AMOUNT,
                        Constants.LOAN_PERIOD,
                        Constants.MONTHLY_CONTRIBUTION
                )
                .clickApplyAndVerifyRedirect();
    }

    @Test(
            description = "SCRUM-T52 | Verify Consumer Loan Page Content Against API Response"
    )
    public void verifyConsumerLoanPageContentAgainstApiResponse() {

        ConsumerLoanResponse response = given()
                .baseUri(Constants.API_BASE_URL)
                .queryParam("locale", Constants.EN_US_LOCALE)
                .when()
                .get(Constants.CONSUMER_LOAN_PAGE_ENDPOINT)
                .then()
                .statusCode(Constants.OK_STATUS_CODE)
                .extract()
                .as(ConsumerLoanResponse.class);

        SectionComponent calculatorSection =
                response.getSectionComponents()
                        .stream()
                        .filter(section ->
                                Constants.TABS_SECTION.equals(
                                        section.getType()
                                )
                        )
                        .findFirst()
                        .orElseThrow();

        CurrencyConfiguration currencyConfiguration =
                calculatorSection
                        .getInputs()
                        .getTabs()
                        .get(0)
                        .getComponent()
                        .getInputs()
                        .getCurrencyConfiguration()
                        .get(0);

        double yearlyPercent =
                currencyConfiguration.getYearlyPercent();

        int effectivePercent =
                currencyConfiguration.getEffectivePercent();

        consumerLoanSteps
                .openHomePage()
                .openPersonalMenu()
                .openConsumerLoan()
                .openTermsPage()
                .verifyInterestRatesFromApi(
                        String.valueOf(yearlyPercent),
                        String.valueOf(effectivePercent)
                );
    }

    @Test(
            description = "SCRUM-T57 | Verify Consumer Loan Calculator with Database Test Data",
            dataProvider = "loanData",
            dataProviderClass = LoanDataProvider.class
    )
    public void verifyConsumerLoanCalculatorWithDatabaseData(
            LoanData loanData) {

        consumerLoanSteps
                .openHomePage()
                .openPersonalMenu()
                .openConsumerLoan()
                .openTermsPage()
                .verifyLoanCalculatorIsDisplayed()
                .enterLoanData(
                        String.valueOf(loanData.getAmount()),
                        String.valueOf(loanData.getPeriod())
                );
    }
}