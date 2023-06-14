package coffee.lkh.dofusrpa.repositories;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.ejb.Singleton;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

@Singleton
public class AnkamaGamesDatabaseManager {
    public static Integer MaximumPoolSize = 10;
    private HikariDataSource dataSource;

    public AnkamaGamesDatabaseManager() {
        // Load properties file
        Properties props = new Properties();


        try (InputStream inputStream = AnkamaGamesDatabaseManager.class.getClassLoader().getResourceAsStream("dofus.database.properties")) {
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
            config.setMaximumPoolSize(MaximumPoolSize);

            try {
                dataSource = new HikariDataSource(config);
            }catch (Exception ex){
                System.err.println(ex.getMessage());
            }
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }

    }

    public HikariDataSource getDataSource() {
        return  dataSource;
    }

}
