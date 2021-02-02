package es.upm.fi.dia.oeg.morph.r2rml.rdb.yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
/**
 * @author Jhon Toledo
 */
import java.sql.*;
import java.util.*; 
import org.yaml.snakeyaml.Yaml;

public class DatabaseUtils2 {
	


	/*
    private static final String DEFAULT_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DEFAULT_URL = "jdbc:oracle:thin:@host:1521:database";
    private static final String DEFAULT_USERNAME = "username";
    private static final String DEFAULT_PASSWORD = "password";

    private static final String DEFAULT_DRIVER = "org.postgresql.Driver";
    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/party";
    private static final String DEFAULT_USERNAME = "username";
    private static final String DEFAULT_PASSWORD = "password";
*/
	
	
	private static final String DEFAULT_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/morph_example?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "toor";

    
    public static void main(String[] args) {
    	
    	Yaml yaml = new Yaml();
        try {
			Reader yamlFile = new FileReader(/*System.getProperty("user.dir")+"//*/"database.yml");
			  Map<String, Object> obj = yaml.load(yamlFile);
		        System.out.println(obj);

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        //System.out.println("Present Project Directory : "+ System.getProperty("user.dir"));
       
      
	

        long begTime = System.currentTimeMillis();
        
        String driver = ((args.length > 0) ? args[0] : DEFAULT_DRIVER);
        String url = ((args.length > 1) ? args[1] : DEFAULT_URL);
        String username = ((args.length > 2) ? args[2] : DEFAULT_USERNAME);
        String password = ((args.length > 3) ? args[3] : DEFAULT_PASSWORD);

        Connection connection = null;
        // No, I loaded the driver as I intended.  It's correct.  The edit is not.
        try {
            connection = createConnection(driver, url, username, password);
            DatabaseMetaData meta = connection.getMetaData();
            System.out.println(meta.getDatabaseProductName());
            System.out.println(meta.getDatabaseProductVersion());
            String sqlQuery = "SELECT * FROM student";
            System.out.println("before insert: " + query(connection, sqlQuery, Collections.EMPTY_LIST));
        } catch (Exception e) {
            rollback(connection);
            e.printStackTrace();
        } finally {
            close(connection);
            long endTime = System.currentTimeMillis();
            System.out.println("wall time: " + (endTime - begTime) + " ms");
        }
    }

    public static Connection createConnection(String driver, String url, String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        if ((username == null) || (password == null) || (username.trim().length() == 0) || (password.trim().length() == 0)) {
            return DriverManager.getConnection(url);
        } else {
            return DriverManager.getConnection(url, username, password);
        }
    }

    public static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void close(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rollback(Connection connection) {
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Map<String, Object>> map(ResultSet rs) throws SQLException {
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        try {
            if (rs != null) {
                ResultSetMetaData meta = rs.getMetaData();
                int numColumns = meta.getColumnCount();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<String, Object>();
                    for (int i = 1; i <= numColumns; ++i) {
                        String name = meta.getColumnName(i);
                        Object value = rs.getObject(i);
                        row.put(name, value);
                    }
                    results.add(row);
                }
            }
        } finally {
            close(rs);
        }
        return results;
    }

    public static List<Map<String, Object>> query(Connection connection, String sql, List<Object> parameters) throws SQLException {
        List<Map<String, Object>> results = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);

            int i = 0;
            for (Object parameter : parameters) {
                ps.setObject(++i, parameter);
            }
            rs = ps.executeQuery();
            results = map(rs);
        } finally {
            close(rs);
            close(ps);
        }
        return results;
    }

    public static int update(Connection connection, String sql, List<Object> parameters) throws SQLException {
        int numRowsUpdated = 0;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);

            int i = 0;
            for (Object parameter : parameters) {
                ps.setObject(++i, parameter);
            }
            numRowsUpdated = ps.executeUpdate();
        } finally {
            close(ps);
        }
        return numRowsUpdated;
    }

}
