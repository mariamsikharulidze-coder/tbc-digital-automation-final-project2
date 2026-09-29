package ge.tbc.testautomation.api.models;

import java.util.List;

public class ConsumerLoanResponse {

    private List<SectionComponent> sectionComponents;

    public List<SectionComponent> getSectionComponents() {
        return sectionComponents;
    }

    public void setSectionComponents(
            List<SectionComponent> sectionComponents) {
        this.sectionComponents = sectionComponents;
    }
}