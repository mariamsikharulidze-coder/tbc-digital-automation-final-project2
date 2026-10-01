
package ge.tbc.testautomation.database;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;
import java.sql.Connection;

public class DatabaseConfig {

    private static SqlSessionFactory sqlSessionFactory;

    public static synchronized SqlSessionFactory getSqlSessionFactory() {
        if (sqlSessionFactory == null) {
            initializeDatabase();
        }
        return sqlSessionFactory;
    }

    private static void initializeDatabase() {
        try (Reader configReader =
                     Resources.getResourceAsReader("mybatis/mybatis-config.xml")) {

            SqlSessionFactory factory =
                    new SqlSessionFactoryBuilder().build(configReader);

            try (SqlSession session = factory.openSession();
                 Reader scriptReader =
                         Resources.getResourceAsReader("database.sql")) {

                Connection connection = session.getConnection();

                ScriptRunner scriptRunner = new ScriptRunner(connection);
                scriptRunner.runScript(scriptReader);

                session.commit();
            }

            sqlSessionFactory = factory;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to initialize database", e
            );
        }
    }
}
