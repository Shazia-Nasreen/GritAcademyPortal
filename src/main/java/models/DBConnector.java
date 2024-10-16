package models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

/**
 * DBConnector Singleton. Uses queries defined in rqueries.csv.
 * Access object with DBConnector.getConnector().
 * Use one of the <operation>Query methods.
 */
public class DBConnector {

    final private String queriesPath = "src/main/java/models/rqueries.csv"; // Adjust as needed
    private static DBConnector connector = null;

    private DBConnector() {}

    public static DBConnector getConnector() {
        if (connector == null) {
            connector = new DBConnector();
        }
        return connector;
    }

    /**
     * Executes a SELECT query and returns the results as a LinkedList of String arrays.
     * Additional conditional variable arguments can be added to the query with {0}, {1}, ...
     * @param queryName The name of the query from the CSV file.
     * @param args Additional arguments to replace placeholders in the query.
     * @return LinkedList with data rows as String arrays.
     */
    public LinkedList<String[]> selectQuery(String queryName, String... args) {
        try (BufferedReader br = new BufferedReader(new FileReader(queriesPath))) {
            String query;
            while ((query = br.readLine()) != null) {
                String[] line = query.trim().split(";");

                if (line.length >= 6 && line[0].equals(queryName)) {
                    for (int i = 0; i < args.length; i++) {
                        line[1] = line[1].replace("{" + i + "}", args[i]);
                    }

                    String db = line.length > 2 ? line[2] : "default_db";
                    String ip = line.length > 3 ? line[3] : "localhost";
                    String port = line.length > 4 ? line[4] : "3306";
                    String user = line.length > 5 ? line[5] : "root";
                    String password = line.length > 6 ? line[6] : "";

                    return select(line[1], db, ip, port, user, password);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading the query file: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Executes a SELECT query on the database and returns the results as a LinkedList of String arrays.
     * @param query The SQL query to be executed.
     * @param db The database name.
     * @param ip The IP address of the database.
     * @param port The database port.
     * @param user The database user.
     * @param password The database password.
     * @return LinkedList of result rows as String arrays.
     */
    private LinkedList<String[]> select(String query, String db, String ip, String port, String user, String password) {
        LinkedList<String[]> queryResult = new LinkedList<>();
        try (Connection con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + db, user, password);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();

            String[] headerRow = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                headerRow[i - 1] = md.getColumnName(i);
            }
            queryResult.add(headerRow);

            while (rs.next()) {
                String[] dataRow = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    dataRow[i - 1] = rs.getString(i);
                }
                queryResult.add(dataRow);
            }
        } catch (Exception e) {
            System.err.println("Database error: " + e.getMessage());
        }
        return queryResult;
    }

    /**
     * Executes an INSERT query with variable arguments.
     * The query and the types of arguments are defined in the CSV file.
     * @param queryName The name of the query in the CSV file.
     * @param args The values to be inserted and their types (e.g., S for string, I for integer).
     * @return true if the query was successful, false otherwise.
     */
    public boolean insertQuery(String queryName, String... args) {
        try (BufferedReader br = new BufferedReader(new FileReader(queriesPath))) {
            String query;
            while ((query = br.readLine()) != null) {
                String[] line = query.trim().split(";");

                if (line.length >= 6 && line[0].equals(queryName)) {
                    String db = line.length > 2 ? line[2] : "default_db";
                    String ip = line.length > 3 ? line[3] : "localhost";
                    String port = line.length > 4 ? line[4] : "3306";
                    String user = line.length > 5 ? line[5] : "root";
                    String password = line.length > 6 ? line[6] : "";

                    return insert(line[1], db, ip, port, user, password, args);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading the query file: " + e.getMessage(), e);
        }
        return false;
    }

    /**
     * Executes an INSERT query on the database.
     * @param query The SQL query to be executed.
     * @param db The database name.
     * @param ip The IP address of the database.
     * @param port The database port.
     * @param user The database user.
     * @param password The database password.
     * @param args The values to be inserted and their types (e.g., S for string, I for integer).
     * @return true if the query was successful, false otherwise.
     */
    private boolean insert(String query, String db, String ip, String port, String user, String password, String... args) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + db, user, password);
             PreparedStatement ps = con.prepareStatement(query)) {

            for (int i = 0; i < args.length / 2; i++) {
                if (args[i + (args.length / 2)].equals("S")) {
                    ps.setString(i + 1, args[i]);
                } else if (args[i + (args.length / 2)].equals("I")) {
                    ps.setInt(i + 1, Integer.parseInt(args[i]));
                }
            }

            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            System.err.println("Database error during insert: " + e.getMessage());
        }
        return false;
    }

    /**
     * Executes an UPDATE query with variable arguments.
     * The query and the types of arguments are defined in the CSV file.
     * @param queryName The name of the query in the CSV file.
     * @param args The values to be updated and their types (e.g., S for string, I for integer).
     * @return true if the query was successful, false otherwise.
     */
    public boolean updateQuery(String queryName, String... args) {
        try (BufferedReader br = new BufferedReader(new FileReader(queriesPath))) {
            String query;
            while ((query = br.readLine()) != null) {
                String[] line = query.trim().split(";");

                if (line.length >= 6 && line[0].equals(queryName)) {
                    String db = line.length > 2 ? line[2] : "default_db";
                    String ip = line.length > 3 ? line[3] : "localhost";
                    String port = line.length > 4 ? line[4] : "3306";
                    String user = line.length > 5 ? line[5] : "root";
                    String password = line.length > 6 ? line[6] : "";

                    return update(line[1], db, ip, port, user, password, args);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading the query file: " + e.getMessage(), e);
        }
        return false;
    }

    /**
     * Executes an UPDATE query on the database.
     * @param query The SQL query to be executed.
     * @param db The database name.
     * @param ip The IP address of the database.
     * @param port The database port.
     * @param user The database user.
     * @param password The database password.
     * @param args The values to be updated and their types (e.g., S for string, I for integer).
     * @return true if the query was successful, false otherwise.
     */
    private boolean update(String query, String db, String ip, String port, String user, String password, String... args) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + db, user, password);
             PreparedStatement ps = con.prepareStatement(query)) {

            for (int i = 0; i < args.length / 2; i++) {
                if (args[i + (args.length / 2)].equals("S")) {
                    ps.setString(i + 1, args[i]);
                } else if (args[i + (args.length / 2)].equals("I")) {
                    ps.setInt(i + 1, Integer.parseInt(args[i]));
                }
            }

            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            System.err.println("Database error during update: " + e.getMessage());
        }
        return false;
    }
}
