package ge.tbc.testautomation.tests.data;

import ge.tbc.testautomation.database.DatabaseConfig;
import ge.tbc.testautomation.database.mappers.LoanDataMapper;
import ge.tbc.testautomation.database.models.LoanData;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.DataProvider;

import java.util.List;

public class LoanDataProvider {

    @DataProvider(name = "loanData")
    public static Object[][] loanData() {

        List<LoanData> loanDataList;

        try (SqlSession session =
                     DatabaseConfig
                             .getSqlSessionFactory()
                             .openSession()) {

            LoanDataMapper mapper =
                    session.getMapper(LoanDataMapper.class);

            loanDataList = mapper.getAllLoanData();
        }

        Object[][] data = new Object[loanDataList.size()][1];

        for (int i = 0; i < loanDataList.size(); i++) {
            data[i][0] = loanDataList.get(i);
        }

        return data;
    }
}