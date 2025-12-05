import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    
    private String url = "jdbc:sqlite:library.db";

    public void add(Book book) {
        String sql = "INSERT INTO books(title, author, year) VALUES(?,?,?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setInt(3, book.getYear());
            pstmt.executeUpdate();
            System.out.println("Başarılı: Kitap veritabanına eklendi.");
            
        } catch (SQLException e) {
            System.out.println("Hata (Kitap Ekleme): " + e.getMessage());
        }
    }

    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Book book = new Book(); 
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setYear(rs.getInt("year"));
                
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Hata (Listeleme): " + e.getMessage());
        }
        return books;
    }
}