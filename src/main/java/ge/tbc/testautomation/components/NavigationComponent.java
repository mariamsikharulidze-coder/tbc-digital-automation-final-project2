package ge.tbc.testautomation.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class NavigationComponent {

    private final Page page;

    public Locator personalButton;
    public Locator forBusinessButton;

    public NavigationComponent(Page page) {
        this.page = page;

        personalButton = page.locator(
                "//tbcx-pw-navigation//button[normalize-space()='Personal']"
        );

        forBusinessButton = page.locator(
                "//tbcx-pw-navigation//button[normalize-space()='For Business']"
        );
    }

    public Locator getNavigationButton(String buttonName) {
        return page.locator(
                "//tbcx-pw-navigation//button[normalize-space()='"
                        + buttonName + "']"
        );
    }
}