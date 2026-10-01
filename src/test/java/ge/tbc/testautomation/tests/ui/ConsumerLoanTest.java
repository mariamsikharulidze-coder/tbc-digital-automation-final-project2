package ge.tbc.testautomation.tests.ui;

import ge.tbc.testautomation.api.clients.ConsumerLoanApiClient;
import ge.tbc.testautomation.api.models.CurrencyConfiguration;
import ge.tbc.testautomation.constants.Constants;
import ge.tbc.testautomation.database.models.LoanData;
import ge.tbc.testautomation.steps.ConsumerLoanSteps;
import ge.tbc.testautomation.tests.Base.BaseTest;
import ge.tbc.testautomation.tests.data.LoanDataProvider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ConsumerLoanTest extends BaseTest {

    private ConsumerLoanSteps consumerLoanSteps;
    private ConsumerLoanApiClient consumerLoanApiClient;

    @BeforeMethod
    public void initialize() {
        consumerLoanSteps = new ConsumerLoanSteps(page);
        consumerLoanApiClient = new ConsumerLoanApiClient();
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

        CurrencyConfiguration currencyConfiguration =
                consumerLoanApiClient.getCurrencyConfiguration();

        consumerLoanSteps
                .openHomePage()
                .openPersonalMenu()
                .openConsumerLoan()
                .openTermsPage()
                .verifyInterestRatesFromApi(
                        String.valueOf(
                                currencyConfiguration.getYearlyPercent()
                        ),
                        String.valueOf(
                                currencyConfiguration.getEffectivePercent()
                        )
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