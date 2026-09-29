package ge.tbc.testautomation.database;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;
import java.sql.Connection;

public class DatabaseConfig {

    private static SqlSessionFactory sqlSessionFactory;

    public static SqlSessionFactory getSqlSessionFactory() {
        if (sqlSessionFactory == null) {
            initializeDatabase();
        }

        return sqlSessionFactory;
    }

    private static void initializeDatabase() {
        try {
            Reader configReader =
                    Resources.getResourceAsReader("mybatis/mybatis-config.xml");

            sqlSessionFactory =
                    new SqlSessionFactoryBuilder().build(configReader);

            try (Connection connection =
                         sqlSessionFactory
                                 .openSession()
                                 .getConnection()) {

                Reader scriptReader =
                        Resources.getResourceAsReader("database.sql");

                ScriptRunner scriptRunner =
                        new ScriptRunner(connection);

                scriptRunner.runScript(scriptReader);
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to initialize database",
                    e
            );
        }
    }
}