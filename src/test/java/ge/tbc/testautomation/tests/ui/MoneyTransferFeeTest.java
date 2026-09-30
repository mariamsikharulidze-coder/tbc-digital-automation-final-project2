package ge.tbc.testautomation.tests.ui;

import ge.tbc.testautomation.constants.Constants;
import ge.tbc.testautomation.steps.MoneyTransfersSteps;
import ge.tbc.testautomation.tests.Base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MoneyTransferFeeTest extends BaseTest {

    private MoneyTransfersSteps moneyTransfersSteps;

    @BeforeMethod
    public void initializeSteps() {
        moneyTransfersSteps = new MoneyTransfersSteps(page);
    }

    @Test(
            description = "SCRUM-T47 | Verify Money Transfer Fee Calculation UI"
    )
    public void verifyMoneyTransferFeeCalculationUi() {

        moneyTransfersSteps
                .openHomePage()
                .openPersonalMenu()
                .openMoneyTransfersPage()
                .openFeeCalculation()
                .enterAmount(Constants.TRANSFER_AMOUNT)
                .selectCurrency()
                .selectCountry()
                .verifyCommissionResults();
    }
}