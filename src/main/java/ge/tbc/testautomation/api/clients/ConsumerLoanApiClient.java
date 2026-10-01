package ge.tbc.testautomation.api.clients;

import ge.tbc.testautomation.api.models.ConsumerLoanResponse;
import ge.tbc.testautomation.api.models.CurrencyConfiguration;
import ge.tbc.testautomation.api.models.SectionComponent;
import ge.tbc.testautomation.constants.Constants;
import static io.restassured.RestAssured.given;


public class ConsumerLoanApiClient {

    public ConsumerLoanResponse getConsumerLoanResponse() {
        return given()
                .baseUri(Constants.API_BASE_URL)
                .queryParam("locale", Constants.EN_US_LOCALE)
                .when()
                .get(Constants.CONSUMER_LOAN_PAGE_ENDPOINT)
                .then()
                .statusCode(Constants.OK_STATUS_CODE)
                .extract()
                .as(ConsumerLoanResponse.class);
    }

    public CurrencyConfiguration getCurrencyConfiguration() {
        ConsumerLoanResponse response = getConsumerLoanResponse();

        SectionComponent calculatorSection = response
                .getSectionComponents()
                .stream()
                .filter(section ->
                        Constants.TABS_SECTION.equals(section.getType()))
                .findFirst()
                .orElseThrow();

        return calculatorSection
                .getInputs()
                .getTabs()
                .get(0)
                .getComponent()
                .getInputs()
                .getCurrencyConfiguration()
                .get(0);
    }
}