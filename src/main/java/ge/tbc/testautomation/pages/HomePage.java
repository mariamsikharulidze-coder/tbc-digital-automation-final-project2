package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.components.NavigationComponent;

public class HomePage {

    private final Page page;
    private final NavigationComponent navigationComponent;

    public HomePage(Page page) {
        this.page = page;
        this.navigationComponent = new NavigationComponent(page);
    }

    public void open() {
        page.navigate(UrlConstants.HOME_URL);
    }

    public NavigationComponent navigation() {
        return navigationComponent;
    }
}