package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.components.NavigationComponent;
import ge.tbc.testautomation.pages.PosTerminalPage;

import java.util.regex.Pattern;

public class PosTerminalSteps {

    Page page;
    PosTerminalPage posTerminalPage;
    NavigationComponent navigationComponent;

    public PosTerminalSteps(Page page) {
        this.page = page;
        this.posTerminalPage = new PosTerminalPage(page);
        this.navigationComponent = new NavigationComponent(page);
    }

    public PosTerminalSteps openHomePage() {
        page.navigate("https://tbcbank.ge/en");
        return this;
    }

    public PosTerminalSteps openForBusinessMenu() {
        navigationComponent.forBusinessButton.hover();
        return this;
    }

    public PosTerminalSteps openPosTerminalsPage() {
        posTerminalPage.posTerminalsButton.click();
        return this;
    }

    public PosTerminalSteps openAndroidTerminalForm() {
        posTerminalPage.androidTerminalCard.hover();

        PlaywrightAssertions.assertThat(
                posTerminalPage.fillOutFormButton
        ).isVisible();

        posTerminalPage.fillOutFormButton.click();

        PlaywrightAssertions.assertThat(page).hasURL(
                Pattern.compile(
                        ".*/en/pos-terminals/order/pos/5/1.*"
                )
        );

        return this;
    }
}