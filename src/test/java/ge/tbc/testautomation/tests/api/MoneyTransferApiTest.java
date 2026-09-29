package ge.tbc.testautomation.tests.api;

import ge.tbc.testautomation.api.models.MoneyTransferFee;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class MoneyTransferApiTest {

    @Test
    public void verifyMoneyTransferSystems() {

        Response response = given()
                .baseUri("https://apigw.tbcbank.ge")
                .queryParam("amount", 200)
                .queryParam("currencyCode", "EUR")
                .queryParam("receiveCountryCode", "GEO")
                .when()
                .get("/api/v1/moneyTransfer/fees")
                .then()
                .statusCode(200)
                .extract()
                .response();

        List<MoneyTransferFee> transferFees =
                response.jsonPath()
                        .getList("", MoneyTransferFee.class);

        assertNotNull(transferFees);
        assertFalse(transferFees.isEmpty());

        MoneyTransferFee moneyGram = transferFees.stream()
                .filter(item -> item.getMtSystem().equals("MoneyGram"))
                .findFirst()
                .orElseThrow();

        assertEquals(moneyGram.getFee(), 2.0);

        MoneyTransferFee intelExpress = transferFees.stream()
                .filter(item -> item.getMtSystem().equals("IntelExpress"))
                .findFirst()
                .orElseThrow();

        assertEquals(intelExpress.getFee(), 1.0);

        MoneyTransferFee fastTransfer = transferFees.stream()
                .filter(item -> item.getMtSystem().equals("FastTransfer"))
                .findFirst()
                .orElseThrow();

        assertEquals(fastTransfer.getFee(), 5.0);
    }
    @Test
    public void verifyMoneyTransferRequestWithoutCurrencyCode() {

        given()
                .baseUri("https://apigw.tbcbank.ge")
                .queryParam("amount", 200)
                .queryParam("receiveCountryCode", "GEO")
                .when()
                .get("/api/v1/moneyTransfer/fees")
                .then()
                .statusCode(400)
                .body("status", equalTo(400))
                .body("title",
                        equalTo("One or more validation errors occurred."))
                .body("errors.currencyCode[0]",
                        equalTo("The currencyCode field is required."));
    }
}