package ge.tbc.testautomation.api.clients;

import ge.tbc.testautomation.constants.Constants;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class MoneyTransferApiClient {

    public Response getMoneyTransferFees() {
        return given()
                .baseUri(Constants.API_BASE_URL)
                .queryParam("amount", Constants.TRANSFER_AMOUNT)
                .queryParam("currencyCode", Constants.CURRENCY)
                .queryParam("receiveCountryCode", Constants.COUNTRY_CODE)
                .when()
                .get(Constants.MONEY_TRANSFER_FEES_ENDPOINT);
    }

    public Response getMoneyTransferFeesWithoutCurrencyCode() {
        return given()
                .baseUri(Constants.API_BASE_URL)
                .queryParam("amount", Constants.TRANSFER_AMOUNT)
                .queryParam("receiveCountryCode", Constants.COUNTRY_CODE)
                .when()
                .get(Constants.MONEY_TRANSFER_FEES_ENDPOINT);
    }
}