/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.sandbox;


import java.sql.*;
import org.junit.Assert;

public class SQLDatabaseTest {

    public static void main(String[] args) {
        String connectionString ="jdbc:sqlserver://bytes.database.windows.net:1433;database=SampleDB;user=kentative@bytes;password=Azure2016;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
//                "jdbc:sqlserver://bytes.database.windows.net:1433;"
//                        + "database=ThinkrDB;"
//                        + "user=kentative@bytes;"
//                        + "password=Azure2016;"
//                        + "encrypt=true;"
//                        + "trustServerCertificate=false;"
//                        + "hostNameInCertificate=*.database.windows.net;"
//                        + "loginTimeout=30;";

        // Declare the JDBC objects.
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        PreparedStatement prepsInsertPerson = null;
        PreparedStatement prepsUpdateAge = null;

        try {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionString);

            displayDbProperties(connection);

            Assert.assertTrue(connection !=null);
            // INSERT two rows into the table.
            // ...

            // TRANSACTION and commit for an UPDATE.
            // ...

            // SELECT rows from the table.
            // ...
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // Close the connections after the entity has been handled.
            if (prepsInsertPerson != null) try { prepsInsertPerson.close(); } catch(Exception e) {}
            if (prepsUpdateAge != null) try { prepsUpdateAge.close(); } catch(Exception e) {}
            if (resultSet != null) try { resultSet.close(); } catch(Exception e) {}
            if (statement != null) try { statement.close(); } catch(Exception e) {}
            if (connection != null) try { connection.close(); } catch(Exception e) {}
        }

    }


    private static void displayDbProperties(Connection con) {
        java.sql.DatabaseMetaData dm = null;
        java.sql.ResultSet rs = null;
        try {
            if (con != null) {
                dm = con.getMetaData();
                System.out.println("Driver Information");
                System.out.println("\tDriver Name: " + dm.getDriverName());
                System.out.println("\tDriver Version: " + dm.getDriverVersion());
                System.out.println("\nDatabase Information ");
                System.out.println("\tDatabase Name: " + dm.getDatabaseProductName());
                System.out.println("\tDatabase Version: " + dm.getDatabaseProductVersion());
                System.out.println("Avalilable Catalogs ");
                rs = dm.getCatalogs();
                while (rs.next()) {
                    System.out.println("\tcatalog: " + rs.getString(1));
                }
                rs.close();
                rs = null;
            } else {
                System.out.println("Error: No active Connection");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dm = null;
    }

}