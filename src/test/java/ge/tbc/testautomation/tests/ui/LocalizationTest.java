package ge.tbc.testautomation.tests.ui;

import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.components.NavigationComponent;
import ge.tbc.testautomation.tests.Base.BaseTest;
import ge.tbc.testautomation.tests.data.LocalizationDataProvider;
import org.testng.annotations.Test;

public class LocalizationTest extends BaseTest {

   @Test(
    description = "SCRUM-T56 | Verify Header Localization for Georgian and English Languages",
    dataProvider = "localizationData",
    dataProviderClass = LocalizationDataProvider.class
)
    public void verifyHomepageLocalization(
            String url,
            String personalText,
            String businessText,
            String tbcText) {

        page.navigate(url);

        NavigationComponent navigationComponent =
                new NavigationComponent(page);

        PlaywrightAssertions.assertThat(
                navigationComponent.getNavigationButton(personalText)
        ).isVisible();

        PlaywrightAssertions.assertThat(
                navigationComponent.getNavigationButton(businessText)
        ).isVisible();

        PlaywrightAssertions.assertThat(
                navigationComponent.getNavigationButton(tbcText)
        ).isVisible();
    }
}