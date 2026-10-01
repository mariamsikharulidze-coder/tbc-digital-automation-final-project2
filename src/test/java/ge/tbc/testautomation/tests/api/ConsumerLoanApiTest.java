package ge.tbc.testautomation.tests.api;

import ge.tbc.testautomation.api.clients.ConsumerLoanApiClient;
import ge.tbc.testautomation.api.models.ConsumerLoanResponse;
import ge.tbc.testautomation.api.models.CurrencyConfiguration;
import ge.tbc.testautomation.constants.Constants;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ConsumerLoanApiTest {

    private ConsumerLoanApiClient consumerLoanApiClient;

    @BeforeMethod
    public void initializeClient() {
        consumerLoanApiClient = new ConsumerLoanApiClient();
    }

    @Test
    public void checkConsumerLoanApiResponse() {

        ConsumerLoanResponse response =
                consumerLoanApiClient.getConsumerLoanResponse();

        CurrencyConfiguration currencyConfiguration =
                consumerLoanApiClient.getCurrencyConfiguration();

        assertThat(response, notNullValue());

        assertThat(
                response.getSectionComponents(),
                is(not(empty()))
        );

        assertThat(
                currencyConfiguration.getYearlyPercent(),
                equalTo(9.9)
        );

        assertThat(
                currencyConfiguration.getEffectivePercent(),
                equalTo(18)
        );
    }
}