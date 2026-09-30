package ge.tbc.testautomation.tests.ui;

import ge.tbc.testautomation.steps.InstallmentSteps;
import ge.tbc.testautomation.tests.Base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InstallmentTest extends BaseTest {

    private InstallmentSteps installmentSteps;

    @BeforeMethod
    public void initializeSteps() {
        installmentSteps = new InstallmentSteps(page);
    }

    @Test(
            description = "SCRUM-T51 | Verify Online Installment Terms"
    )
    public void verifyOnlineInstallmentTerms() {

        installmentSteps
                .openHomePage()
                .openPersonalMenu()
                .openInstallmentsPage()
                .openInstallmentTermsPage()
                .openTermsTab()
                .verifyLoanLimitIsDisplayed();
    }
}