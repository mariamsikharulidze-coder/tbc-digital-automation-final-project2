package ge.tbc.testautomation.api.models;

import lombok.Data;

import java.util.List;

@Data
public class ConsumerLoanResponse {

    private List<SectionComponent> sectionComponents;
}