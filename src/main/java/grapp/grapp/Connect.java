package grapp.grapp;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import org.springframework.context.annotation.Bean;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;

public class Connect {

    
    @Value("${spring.datasource.url}")
    private String dbUrl;


    @Bean
    public DataSource dataSource() throws SQLException {
      if (dbUrl == null || dbUrl.isEmpty()) {
        return new HikariDataSource();
      } else {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        return new HikariDataSource(config);
      }
    }
}
