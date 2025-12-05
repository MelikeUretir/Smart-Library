import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Database.createNewTables();
        
        Scanner scanner = new Scanner(System.in);
        
        BookRepository bookRepo = new BookRepository();
        StudentRepository studentRepo = new StudentRepository();
        LoanRepository loanRepo = new LoanRepository();

        while (true) {
            System.out.println("\n--- SMART LIBRARY SİSTEMİ ---");
            System.out.println("1. Kitap Ekle");
            System.out.println("2. Kitapları Listele");
            System.out.println("3. Öğrenci Ekle");
            System.out.println("4. Öğrencileri Listele");
            System.out.println("5. Kitap Ödünç Ver");
            System.out.println("6. Ödünç Listesi");
            System.out.println("7. Kitap Geri Teslim Al");
            System.out.println("0. Çıkış");
            System.out.print("Seçiminiz: ");

            int secim = scanner.nextInt();
            scanner.nextLine();

            switch (secim) {
                case 1:
                    System.out.print("Kitap Adı: ");
                    String title = scanner.nextLine();
                    System.out.print("Yazar: ");
                    String author = scanner.nextLine();
                    System.out.print("Yıl: ");
                    int year = scanner.nextInt();
                    bookRepo.add(new Book(title, author, year));
                    break;

                case 2:
                    System.out.println("\n--- Kitap Listesi ---");
                    for (Book b : bookRepo.getAll()) {
                        System.out.println("ID: " + b.getId() + " - " + b.getTitle() + " (" + b.getAuthor() + ")");
                    }
                    break;

                case 3:
                    System.out.print("Öğrenci Adı: ");
                    String name = scanner.nextLine();
                    System.out.print("Bölüm: ");
                    String dept = scanner.nextLine();
                    studentRepo.add(new Student(name, dept));
                    break;

                case 4:
                    System.out.println("\n--- Öğrenci Listesi ---");
                    for (Student s : studentRepo.getAll()) {
                        System.out.println("ID: " + s.getId() + " - " + s.getName() + " (" + s.getDepartment() + ")");
                    }
                    break;

                case 5:
                    System.out.println("\n--- Ödünç Verme Ekranı ---");
                    System.out.println("Mevcut Kitap ID'leri:");
                    for (Book b : bookRepo.getAll()) System.out.println(b.getId() + "-" + b.getTitle());
                    
                    System.out.print("Ödünç Verilecek Kitap ID: ");
                    int bId = scanner.nextInt();
                    
                    System.out.print("Öğrenci ID: ");
                    int sId = scanner.nextInt();
                    scanner.nextLine();
                    
                    System.out.print("Tarih (Örn: 2025-12-04): ");
                    String date = scanner.nextLine();
                    
                    loanRepo.lendBook(new Loan(bId, sId, date));
                    break;

                case 6:
                    System.out.println("\n--- Ödünç Durumu ---");
                    for (String info : loanRepo.getAllLoans()) {
                        System.out.println(info);
                    }
                    break;

                case 7:
                    System.out.print("İade Edilen Kitap ID: ");
                    int returnBookId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("İade Tarihi: ");
                    String retDate = scanner.nextLine();
                    
                    loanRepo.returnBook(returnBookId, retDate);
                    break;

                case 0:
                    System.out.println("Çıkış yapılıyor...");
                    return;
                default:
                    System.out.println("Geçersiz seçim.");
            }
        }
    }
}