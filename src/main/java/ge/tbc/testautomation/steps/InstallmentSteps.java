package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.components.NavigationComponent;
import ge.tbc.testautomation.constants.Constants;
import ge.tbc.testautomation.pages.InstallmentPage;

public class InstallmentSteps {

    private final Page page;
    private final InstallmentPage installmentPage;
    private final NavigationComponent navigationComponent;

    public InstallmentSteps(Page page) {
        this.page = page;
        this.installmentPage = new InstallmentPage(page);
        this.navigationComponent = new NavigationComponent(page);
    }

    public InstallmentSteps openHomePage() {
        page.navigate(Constants.HOME_URL);
        return this;
    }

    public InstallmentSteps openPersonalMenu() {
        navigationComponent.personalButton.hover();
        return this;
    }

    public InstallmentSteps openInstallmentsPage() {
        installmentPage.installmentsButton.click();
        return this;
    }

    public InstallmentSteps openInstallmentTermsPage() {
        installmentPage.installmentTermsButton.click();
        return this;
    }

    public InstallmentSteps openTermsTab() {
        installmentPage.termsTab.click();
        return this;
    }

    public InstallmentSteps verifyLoanLimitIsDisplayed() {
        PlaywrightAssertions.assertThat(
                installmentPage.loanLimit
        ).isVisible();

        return this;
    }
}