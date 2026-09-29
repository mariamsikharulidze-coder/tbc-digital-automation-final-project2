package ge.tbc.testautomation.tests.ui;

import com.microsoft.playwright.Response;
import ge.tbc.testautomation.constants.Constants;
import ge.tbc.testautomation.tests.Base.BaseTest;
import ge.tbc.testautomation.steps.ConsumerLoanSteps;
import ge.tbc.testautomation.steps.InstallmentSteps;
import ge.tbc.testautomation.steps.MoneyTransfersSteps;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ge.tbc.testautomation.steps.PosTerminalSteps;
import ge.tbc.testautomation.database.models.LoanData;
import ge.tbc.testautomation.tests.data.LoanDataProvider;
import ge.tbc.testautomation.api.models.ConsumerLoanResponse;
import ge.tbc.testautomation.api.models.CurrencyConfiguration;
import ge.tbc.testautomation.api.models.SectionComponent;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;


public class Tests extends BaseTest {

    ConsumerLoanSteps consumerLoanSteps;
    MoneyTransfersSteps moneyTransfersSteps;
    InstallmentSteps installmentSteps;
    PosTerminalSteps posTerminalSteps;

    @BeforeMethod
    public void initializeSteps() {

        consumerLoanSteps = new ConsumerLoanSteps(page);
        moneyTransfersSteps = new MoneyTransfersSteps(page);
        installmentSteps = new InstallmentSteps(page);
        posTerminalSteps = new PosTerminalSteps(page);
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
            description = "SCRUM-T47 | Verify Remittance Fee Calculation"
    )
    public void verifyRemittanceFeeCalculation() {

        moneyTransfersSteps
                .openHomePage()
                .openPersonalMenu()
                .openMoneyTransfersPage()
                .openFeeCalculation()
                .enterAmount("200")
                .selectCurrency()
                .selectCountry()
                .verifyCommissionResults();
    }


    @Test(
            description = "SCRUM-T52 | Verify POS Terminal Application Form"
    )
    public void verifyConsumerLoanPageContentAgainstApiResponse() {

        ConsumerLoanResponse response = given()
                .baseUri("https://apigw.tbcbank.ge")
                .queryParam("locale", "en-US")
                .when()
                .get("/api/v1/sites/pages/VL9d8DnAnqAGWv84sUJvZ")
                .then()
                .statusCode(200)
                .extract()
                .as(ConsumerLoanResponse.class);

        SectionComponent calculatorSection =
                response.getSectionComponents()
                        .stream()
                        .filter(section ->
                                "tabsSection".equals(section.getType()))
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
            description = "SCRUM-T51 | Verify Online Installment Terms"
    )
    public void verifyOnlineInstallmentTerms() {

        installmentSteps
                .openHomePage()
                .openPersonalMenu()
                .openInstallmentsPage()
                .openInstallmentTermsPage()
                .openTermsTab()
                .verifyLoanLimitIsDisplayed();
    }

    @Test(
            description = "SCRUM-T42 | Verify POS Terminal Application Form"
    )
    public void verifyPosTerminalApplicationForm() {

        posTerminalSteps
                .openHomePage()
                .openForBusinessMenu()
                .openPosTerminalsPage()
                .openAndroidTerminalForm();
    }


    @Test(
            description = "SCRUM-T57 | Verify Consumer Loan Calculator with Database Test Data",
            dataProvider = "loanData",
            dataProviderClass = LoanDataProvider.class
    )

    public void verifyConsumerLoanCalculatorWithDatabaseData(LoanData loanData) {

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

    @Test(
            description = "SCRUM-T48 | Money Transfer Systems API"
    )
    public void verifyMoneyTransferSystems() {


        moneyTransfersSteps
                .openHomePage()
                .openPersonalMenu()
                .openMoneyTransfersPage()
                .openFeeCalculation()
                .enterAmount("200")
                .selectCurrency();

        Response response =
                moneyTransfersSteps
                        .selectCountryAndCaptureNetworkResponse();

        System.out.println("URL: " + response.url());
        System.out.println("METHOD: " + response.request().method());
        System.out.println("STATUS: " + response.status());

        assertTrue(
                response.url().contains("/api/v1/moneyTransfer/fees")
        );

        assertEquals(
                response.request().method(),
                "GET"
        );

        assertEquals(
                response.status(),
                200
        );

        assertTrue(
                response.url().contains("amount=200")
        );

        assertTrue(
                response.url().contains("currencyCode=EUR")
        );

        assertTrue(
                response.url().contains("receiveCountryCode=GEO")
        );

        moneyTransfersSteps.verifyCommissionResults();
    }
}