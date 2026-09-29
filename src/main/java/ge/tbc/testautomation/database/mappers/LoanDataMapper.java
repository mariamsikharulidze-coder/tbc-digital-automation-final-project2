package ge.tbc.testautomation.database.mappers;

import ge.tbc.testautomation.database.models.LoanData;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LoanDataMapper {

    @Select("""
            SELECT id, amount, period
            FROM loan_data
            ORDER BY id
            """)
    List<LoanData> getAllLoanData();
}