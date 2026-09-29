package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class InstallmentPage {

    public Locator installmentsButton;
    public Locator installmentTermsButton;
    public Locator termsTab;
    public Locator loanLimit;

    public InstallmentPage(Page page) {

        installmentsButton = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions()
                        .setName("Installments")
                        .setExact(true)
        );

        installmentTermsButton = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions()
                        .setName("Terms")
                        .setExact(true)
        );
        installmentTermsButton = page
                .locator("tbcx-pw-cta")
                .filter(new Locator.FilterOptions()
                        .setHasText("Installment Buy any item from"))
                .getByRole(
                        AriaRole.BUTTON,
                        new Locator.GetByRoleOptions()
                                .setName("Terms")
                                .setExact(true)
                );

        termsTab = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions()
                        .setName("Terms")
                        .setExact(true)
        );

        loanLimit = page.getByText(
                "Loan limit",
                new Page.GetByTextOptions().setExact(true)
        );
    }
}