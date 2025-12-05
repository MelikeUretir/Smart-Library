import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanRepository {
    private String url = "jdbc:sqlite:library.db";

    public void lendBook(Loan loan) {
        if (isBookBorrowed(loan.getBookId())) {
            System.out.println("HATA: Bu kitap şu an başkasında ödünçte!");
            return;
        }

        String sql = "INSERT INTO loans(bookId, studentId, dateBorrowed) VALUES(?,?,?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, loan.getBookId());
            pstmt.setInt(2, loan.getStudentId());
            pstmt.setString(3, loan.getDateBorrowed());
            pstmt.executeUpdate();
            System.out.println("Başarılı: Kitap ödünç verildi.");
        } catch (SQLException e) {
            System.out.println("Hata (Ödünç Ver): " + e.getMessage());
        }
    }

    public boolean isBookBorrowed(int bookId) {
        String sql = "SELECT count(*) FROM loans WHERE bookId = ? AND dateReturned IS NULL";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getAllLoans() {
        List<String> loans = new ArrayList<>();
        String sql = "SELECT l.id, b.title, s.name, l.dateBorrowed, l.dateReturned " +
                     "FROM loans l " +
                     "JOIN books b ON l.bookId = b.id " +
                     "JOIN students s ON l.studentId = s.id";
                     
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String status = (rs.getString("dateReturned") == null) ? "Devam Ediyor" : "Teslim Edildi: " + rs.getString("dateReturned");
                String info = "ID: " + rs.getInt("id") + " | Kitap: " + rs.getString("title") + 
                              " | Öğrenci: " + rs.getString("name") + " | Durum: " + status;
                loans.add(info);
            }
        } catch (SQLException e) {
            System.out.println("Hata: " + e.getMessage());
        }
        return loans;
    }

    public void returnBook(int bookId, String returnDate) {
        String sql = "UPDATE loans SET dateReturned = ? WHERE bookId = ? AND dateReturned IS NULL";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, returnDate);
            pstmt.setInt(2, bookId);
            int affected = pstmt.executeUpdate();
            if (affected > 0) {
                System.out.println("Başarılı: Kitap teslim alındı.");
            } else {
                System.out.println("Hata: Bu kitap şu an ödünçte görünmüyor.");
            }
        } catch (SQLException e) {
            System.out.println("Hata (Teslim Al): " + e.getMessage());
        }
    }
}