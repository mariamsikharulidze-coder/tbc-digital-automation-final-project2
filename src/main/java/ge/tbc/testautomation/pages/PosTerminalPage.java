package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import ge.tbc.testautomation.constants.Constants;

public class PosTerminalPage {

    public Locator posTerminalsButton;
    public Locator androidTerminalCard;
    public Locator fillOutFormButton;

    public PosTerminalPage(Page page) {

        posTerminalsButton = page
                .locator(
                        "a[href='" + Constants.POS_TERMINALS_PATH + "']"
                )
                .first();

        androidTerminalCard = page
                .locator("tbcx-pw-product-card")
                .filter(
                        new Locator.FilterOptions()
                                .setHasText(Constants.ANDROID_TERMINAL)
                );

        fillOutFormButton = androidTerminalCard.getByRole(
                AriaRole.BUTTON,
                new Locator.GetByRoleOptions()
                        .setName(Constants.FILL_OUT_FORM)
                        .setExact(true)
        );
    }
}