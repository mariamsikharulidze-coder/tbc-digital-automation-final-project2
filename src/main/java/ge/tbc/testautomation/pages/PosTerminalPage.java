package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class PosTerminalPage {

    public Locator posTerminalsButton;
    public Locator androidTerminalCard;
    public Locator fillOutFormButton;

    public PosTerminalPage(Page page) {

        posTerminalsButton = page
                .locator("a[href='/en/business/payment-systems/pos-terminals']")
                .first();

        androidTerminalCard = page
                .locator("tbcx-pw-product-card")
                .filter(
                        new Locator.FilterOptions()
                                .setHasText("Android Terminal")
                );

        fillOutFormButton = androidTerminalCard.getByRole(
                AriaRole.BUTTON,
                new Locator.GetByRoleOptions()
                        .setName("fill out the form")
                        .setExact(true)
        );
    }
}