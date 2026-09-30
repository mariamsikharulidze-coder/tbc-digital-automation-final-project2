package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.constants.Constants;

public class InstallmentPage {

    public Locator installmentsButton;
    public Locator installmentTermsButton;
    public Locator termsTab;
    public Locator loanLimit;

    public InstallmentPage(Page page) {

        installmentsButton = page.locator(
                "(//button[normalize-space()='" +
                        Constants.INSTALLMENTS + "'])[1]"
        );

        installmentTermsButton = page.locator(
                "//tbcx-pw-cta[contains(normalize-space(),'" +
                        Constants.INSTALLMENT_CARD_TEXT +
                        "')]//button[normalize-space()='" +
                        Constants.TERMS + "']"
        );

        termsTab = page.locator(
                "//button[normalize-space()='" +
                        Constants.TERMS + "']"
        );

        loanLimit = page.locator(
                "//*[normalize-space()='" +
                        Constants.LOAN_LIMIT + "']"
        );
    }
}