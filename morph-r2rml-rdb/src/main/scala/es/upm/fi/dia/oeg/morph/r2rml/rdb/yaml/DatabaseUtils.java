package es.upm.fi.dia.oeg.morph.r2rml.rdb.yaml;
/**
 * @author Jhon Toledo
 */
 

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;



public class DatabaseUtils {


	
	
	 public static Connection createConnection(String driver, String url, String username, String password) throws ClassNotFoundException, SQLException {
	        Class.forName(driver);
	        if ((username == null) || (password == null) || (username.trim().length() == 0) || (password.trim().length() == 0)) {
	            return DriverManager.getConnection(url);
	        } else {
	            return DriverManager.getConnection(url, username, password);
	        }
	    }


}
