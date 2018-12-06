import java.nio.file.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLDatabase extends Database {
    private String connectionUrl;

    public SQLDatabase() {
        this.connectionUrl = "jdbc:sqlserver://phonebooks.database.windows.net:1433;" + "database=PhoneBook;"
                + "user=;" + "password=;";
    }

    @Override
    public void add(String name, String number) {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement stmt = connection.createStatement()) {
            if (connection != null) {
                String insertString = String.format("INSERT INTO phonebook VALUES ('%s', '%s');", name, number);
                stmt.execute(insertString);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String name) {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement stmt = connection.createStatement()) {
            if (connection != null) {
                String deleteString = String.format("DELETE FROM phonebook WHERE CONVERT(varchar, name) = '%s';", name);
                stmt.execute(deleteString);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String name, String number) {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement stmt = connection.createStatement()) {
            if (connection != null) {
                String updateString = String.format(
                        "UPDATE phonebook SET number = '%s' WHERE CONVERT(varchar, name) = '%s';", number, name);
                stmt.execute(updateString);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String search(String name) {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement stmt = connection.createStatement()) {
            if (connection != null) {
                String selectString = String.format("SELECT * FROM PhoneBook");
                ResultSet result = stmt.executeQuery(selectString);
                while (result.next()) {
                    if (result.getString("Name").equals(name))
                        return result.getString("Number");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}