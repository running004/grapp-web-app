package grapp.grapp;

import java.sql.SQLException;

import javax.sql.DataSource;

import  com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectForTests {
    
    
    private static String dbUrl = "jdbc:postgresql://ec2-63-32-7-190.eu-west-1.compute.amazonaws.com/daefi82lnqtrnl?user=udonuqdciohkgo&password=77997687eceee1a5bb4f89c886bb08bd4876a2f5b1468a1c0276e1b6930cd085&ssl=false";
    private static DataSource dataSource;

    private static ConnectForTests conexion;

    public ConnectForTests() {
      try{
        dataSource();
      } catch(SQLException e){ System.out.println(e.getMessage());}
    }

    public static ConnectForTests getConnect(){
        if(conexion == null) return new ConnectForTests();
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
          config.setMaximumPoolSize(1);
          config.setJdbcUrl(dbUrl);
          dataSource = new HikariDataSource(config);
        }
      }
}
