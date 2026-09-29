package ge.tbc.testautomation.tests.Base;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeClass
    public void setUp() {

        playwright = Playwright.create();

        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
        );
    }

    @BeforeMethod
    public void isolateContext() {

        context = browser.newContext();
        page = context.newPage();
    }

    @AfterMethod
    public void closeContext() {

        context.close();
    }

    @AfterClass
    public void tearDown() {

        browser.close();
        playwright.close();
    }
}