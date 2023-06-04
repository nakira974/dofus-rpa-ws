package coffee.lkh.dofusrpa.repositories;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

public class DatabaseUtil {

    private static HikariDataSource dataSource;

    public static HikariDataSource getDataSource() {
        if (dataSource == null) {


            // Load properties file
            Properties props = new Properties();


            try (InputStream inputStream = DatabaseUtil.class.getClassLoader().getResourceAsStream("dofus.database.properties")) {
                if (inputStream != null) {
                    props.load(inputStream);
                }
            } catch ( IOException ex) {
                System.err.println(ex.getMessage());
            }

            // Set database properties

            props.put("dataSource.logWriter", new PrintWriter(System.out));
            try{
                HikariConfig config = new HikariConfig(props);

                // Set connection pool properties
                config.setMinimumIdle(5);
                config.setMaximumPoolSize(10);

                try {
                    dataSource = new HikariDataSource(config);
                }catch (Exception ex){
                    System.err.println(ex.getMessage());
                }
            }catch (Exception ex){
                System.err.println(ex.getMessage());
            }

        }
        return  dataSource;
    }

}
