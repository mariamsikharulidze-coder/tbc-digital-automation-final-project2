package ge.tbc.testautomation.tests.ui;

import com.microsoft.playwright.Response;
import ge.tbc.testautomation.constants.Constants;
import ge.tbc.testautomation.steps.MoneyTransfersSteps;
import ge.tbc.testautomation.tests.Base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class MoneyTransferNetworkTest extends BaseTest {

    private MoneyTransfersSteps moneyTransfersSteps;

    @BeforeMethod
    public void initializeSteps() {
        moneyTransfersSteps = new MoneyTransfersSteps(page);
    }

    @Test(
            description = "SCRUM-T58 | Verify Money Transfer Fee Network Request"
    )
    public void verifyMoneyTransferFeeNetworkRequest() {

        moneyTransfersSteps
                .openHomePage()
                .openPersonalMenu()
                .openMoneyTransfersPage()
                .openFeeCalculation()
                .enterAmount(Constants.TRANSFER_AMOUNT)
                .selectCurrency();

        Response response =
                moneyTransfersSteps
                        .selectCountryAndCaptureNetworkResponse();

        assertTrue(
                response.url().contains(
                        Constants.MONEY_TRANSFER_FEES_ENDPOINT
                )
        );

        assertEquals(
                response.request().method(),
                Constants.GET_METHOD
        );

        assertEquals(
                response.status(),
                Constants.OK_STATUS_CODE
        );

        assertTrue(
                response.url().contains(
                        "amount=" + Constants.TRANSFER_AMOUNT
                )
        );

        assertTrue(
                response.url().contains(
                        "currencyCode=" + Constants.CURRENCY
                )
        );

        assertTrue(
                response.url().contains(
                        "receiveCountryCode=" + Constants.COUNTRY_CODE
                )
        );

        moneyTransfersSteps.verifyCommissionResults();
    }
}