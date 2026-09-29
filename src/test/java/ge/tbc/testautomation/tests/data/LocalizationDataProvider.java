package ge.tbc.testautomation.tests.data;

import org.testng.annotations.DataProvider;

public class LocalizationDataProvider {

    @DataProvider(name = "localizationData")
    public static Object[][] localizationData() {
        return new Object[][]{
                {
                        "https://tbcbank.ge/en",
                        "Personal",
                        "For Business",
                        "TBC"
                },
                {
                        "https://tbcbank.ge/ka",
                        "ჩემთვის",
                        "ჩემი ბიზნესისთვის",
                        "თიბისი"
                }
        };
    }
}