package ge.tbc.testautomation.tests.api;

import ge.tbc.testautomation.api.clients.MoneyTransferApiClient;
import ge.tbc.testautomation.api.models.MoneyTransferFee;
import ge.tbc.testautomation.constants.Constants;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MoneyTransferApiTest {

    private MoneyTransferApiClient moneyTransferApiClient;

    @BeforeMethod
    public void initializeClient() {
        moneyTransferApiClient = new MoneyTransferApiClient();
    }

    @Test
    public void verifyMoneyTransferSystems() {

        Response response =
                moneyTransferApiClient.getMoneyTransferFees();

        assertThat(
                response.statusCode(),
                equalTo(Constants.OK_STATUS_CODE)
        );

        List<MoneyTransferFee> transferFees =
                response.jsonPath()
                        .getList("", MoneyTransferFee.class);

        assertThat(transferFees, is(not(empty())));

        assertThat(
                transferFees.stream()
                        .filter(item ->
                                item.getMtSystem()
                                        .equals(Constants.MONEY_GRAM))
                        .findFirst()
                        .orElseThrow()
                        .getFee(),
                equalTo(Constants.MONEY_GRAM_FEE)
        );

        assertThat(
                transferFees.stream()
                        .filter(item ->
                                item.getMtSystem()
                                        .equals(Constants.INTEL_EXPRESS))
                        .findFirst()
                        .orElseThrow()
                        .getFee(),
                equalTo(Constants.INTEL_EXPRESS_FEE)
        );

        assertThat(
                transferFees.stream()
                        .filter(item ->
                                item.getMtSystem()
                                        .equals(Constants.FAST_TRANSFER))
                        .findFirst()
                        .orElseThrow()
                        .getFee(),
                equalTo(Constants.FAST_TRANSFER_FEE)
        );
    }

    @Test
    public void verifyMoneyTransferRequestWithoutCurrencyCode() {

        Response response =
                moneyTransferApiClient
                        .getMoneyTransferFeesWithoutCurrencyCode();

        assertThat(
                response.statusCode(),
                equalTo(Constants.BAD_REQUEST_STATUS_CODE)
        );

        assertThat(
                response.jsonPath().getInt("status"),
                equalTo(Constants.BAD_REQUEST_STATUS_CODE)
        );

        assertThat(
                response.jsonPath().getString("title"),
                equalTo("One or more validation errors occurred.")
        );

        assertThat(
                response.jsonPath()
                        .getString("errors.currencyCode[0]"),
                equalTo("The currencyCode field is required.")
        );
    }
}