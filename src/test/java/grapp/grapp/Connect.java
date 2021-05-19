package grapp.grapp;

import java.sql.SQLException;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Connect {
    
    
    private static String dbUrl = "jdbc:postgresql://ec2-52-50-171-4.eu-west-1.compute.amazonaws.com/dduetcch1mnm33?user=fqdunfercvmtrb&password=4893ba593d036a518f11634deae9224233e95c7f1e9e37bb2f446805dceb3a29&ssl=false";
  
    private static DataSource dataSource;

    private static Connect conexion;

    public Connect() {
      try{
        dataSource();
      } catch(SQLException e){ System.out.println(e.getMessage());}
    }

    public static Connect getConnect(){
        if(conexion == null) return new Connect();
        else return conexion;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    private void  dataSource() throws SQLException {
        if (dbUrl == null || dbUrl.isEmpty()) {
          dataSource = new HikariDataSource();
        } else {
          HikariConfig config = new HikariConfig();
          config.setJdbcUrl(dbUrl);
          dataSource = new HikariDataSource(config);
        }
      }
}
