package ge.tbc.testautomation.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class NavigationComponent {

    Page page;
    public Locator personalButton;
    public Locator forBusinessButton;

    public NavigationComponent(Page page) {
        this.page = page;

        personalButton = page
                .getByRole(AriaRole.BANNER)
                .getByRole(
                        AriaRole.BUTTON,
                        new Locator.GetByRoleOptions()
                                .setName("Personal")
                                .setExact(true)
                );
        forBusinessButton = page
                .getByRole(AriaRole.BANNER)
                .getByRole(
                        AriaRole.BUTTON,
                        new Locator.GetByRoleOptions()
                                .setName("For Business")
                                .setExact(true)
                );
    }


    public Locator getNavigationButton(String buttonName) {
        return page
                .getByRole(AriaRole.BANNER)
                .getByRole(
                        AriaRole.BUTTON,
                        new Locator.GetByRoleOptions()
                                .setName(buttonName)
                                .setExact(true)
                );
    }
}