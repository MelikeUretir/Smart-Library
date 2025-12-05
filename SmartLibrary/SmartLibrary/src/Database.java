import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:sqlite:library.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Bağlantı hatası: " + e.getMessage());
        }
        return conn;
    }

    public static void createNewTables() {
        String sqlBooks = "CREATE TABLE IF NOT EXISTS books (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " title TEXT,\n"
                + " author TEXT,\n"
                + " year INTEGER\n"
                + ");";

        String sqlStudents = "CREATE TABLE IF NOT EXISTS students (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " name TEXT,\n"
                + " department TEXT\n"
                + ");";

        String sqlLoans = "CREATE TABLE IF NOT EXISTS loans (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " bookId INTEGER,\n"
                + " studentId INTEGER,\n"
                + " dateBorrowed TEXT,\n"
                + " dateReturned TEXT\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlBooks);
            stmt.execute(sqlStudents);
            stmt.execute(sqlLoans);
            System.out.println("Veritabanı ve tablolar başarıyla oluşturuldu/kontrol edildi.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}