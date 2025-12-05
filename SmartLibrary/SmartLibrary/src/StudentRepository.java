import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    private String url = "jdbc:sqlite:library.db";

    public void add(Student student) {
        String sql = "INSERT INTO students(name, department) VALUES(?,?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getDepartment());
            pstmt.executeUpdate();
            System.out.println("Başarılı: Öğrenci eklendi.");
        } catch (SQLException e) {
            System.out.println("Hata (Öğrenci Ekle): " + e.getMessage());
        }
    }

    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setDepartment(rs.getString("department"));
                students.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Hata: " + e.getMessage());
        }
        return students;
    }
}