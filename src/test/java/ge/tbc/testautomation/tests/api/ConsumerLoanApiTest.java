package ge.tbc.testautomation.tests.api;

import ge.tbc.testautomation.api.models.ConsumerLoanResponse;
import ge.tbc.testautomation.api.models.CurrencyConfiguration;
import ge.tbc.testautomation.api.models.SectionComponent;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class ConsumerLoanApiTest {

    @Test
    public void checkConsumerLoanApiResponse() {

        ConsumerLoanResponse response = given()
                .baseUri("https://apigw.tbcbank.ge")
                .queryParam("locale", "en-US")
                .when()
                .get("/api/v1/sites/pages/VL9d8DnAnqAGWv84sUJvZ")
                .then()
                .statusCode(200)
                .extract()
                .as(ConsumerLoanResponse.class);

        assertNotNull(response);
        assertNotNull(response.getSectionComponents());

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

        assertEquals(currencyConfiguration.getYearlyPercent(), 9.9);
        assertEquals(currencyConfiguration.getEffectivePercent(), 18);
    }
}