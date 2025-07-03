package org.ike.integrate.tool;

import org.ike.integrate.config.SpringContext;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;

public class PostgreSQLBulk {

    private final DataSource dataSource = SpringContext.getBean(DataSource.class);

    private Connection connection;

    private Connection getConnection() throws Exception {
        if (null == connection) {
            Environment environment = SpringContext.getBean(Environment.class);

            String url = environment.getProperty("spring.datasource.url");
            String user = environment.getProperty("spring.datasource.username");
            String password = environment.getProperty("spring.datasource.password");
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }
    public boolean writeToServer(String tableName, String data) throws Exception {

        CopyManager copyManager = new CopyManager((BaseConnection) getConnection());

//        String input = "1,\"a1\",,false,0\n2,\"a2\",,true,1"; // 代表你的数据，实际应用中可能是从文件读取或其他方式构造的字符串
//        String input = "1,\"a1\",,false,0\n2,\"a2\",,true,1\n3,\"a3\",,true,1";
        StringReader reader = new StringReader(data);

        copyManager.copyIn("COPY tb_account(id, user_name, birthday, enable, ratio) FROM STDIN WITH CSV HEADER", reader);

        System.out.println("Data copied successfully!");
        return false;
    }

}
