package Database;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;

public class Main {

    // без ?autoReconnect=true&useSSL=false всегда в консоль выводится warning
    private static final String URL = "jdbc:mysql://localhost:3306/users_db?autoReconnect=true&useSSL=false"; // место, куда должен обратиться DriverManager, чтобы мы получили Connection
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) throws SQLException {

        Driver driver = new FabricMySQLDriver(); // создаем драйвер
        DriverManager.registerDriver(driver); // регистрируем наш драйвер с помощью DriverManager

        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); // получаем Connection из DriverManager

        // Statement позволяет отпралять запросы базе данных
        Statement statement = connection.createStatement(); // получаем Statement из Connection

        ResultSet resultSet = statement.executeQuery("SELECT * FROM users"); // в resultSet получаем результирующий набор (таблицу)
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData(); // класс ResultSetMetaData содержит информацию о нашей таблице
        // делаем нечто, похожее на таблицу
        System.out.print("|");
        int columnCount = resultSetMetaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) { // выводим названия полей
            System.out.print(resultSetMetaData.getColumnName(i) + "|");
        }
        System.out.println();
        while (resultSet.next()) {
            System.out.print("|");
            for (int i = 1; i <= columnCount; i++)
                System.out.print(resultSet.getString(i) + "|");
            System.out.println();
        }

        resultSet.close();
        statement.close();
        connection.close();

    }

}
