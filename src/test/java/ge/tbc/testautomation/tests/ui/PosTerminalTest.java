package ge.tbc.testautomation.tests.ui;

import ge.tbc.testautomation.steps.PosTerminalSteps;
import ge.tbc.testautomation.tests.Base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PosTerminalTest extends BaseTest {

    private PosTerminalSteps posTerminalSteps;

    @BeforeMethod
    public void initializeSteps() {
        posTerminalSteps = new PosTerminalSteps(page);
    }

    @Test(
            description = "SCRUM-T42 | Verify POS Terminal Application Form"
    )
    public void verifyPosTerminalApplicationForm() {

        posTerminalSteps
                .openHomePage()
                .openForBusinessMenu()
                .openPosTerminalsPage()
                .openAndroidTerminalForm();
    }
}