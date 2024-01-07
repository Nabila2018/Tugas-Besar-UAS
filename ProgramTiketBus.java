import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ProgramTiketBus {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/database tiket bus";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Membuat ArrayList untuk menyimpan informasi tiket yang telah dibeli
        List<TiketBus> daftarTiketTerjual = new ArrayList<>();

        boolean pembayaranSukses = false;

        // Membuat koneksi ke database
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            // Melakukan inisialisasi database (membuat tabel tiket_bus jika belum ada)
            initializeDatabase(connection);

            do {
                try {
                    // Menampilkan menu CRUD
                    System.out.println("=====   PROGRAM PEMBELIAN TIKET BUS    =====");
                    System.out.println("1. Lihat daftar tiket");
                    System.out.println("2. Tambah tiket");
                    System.out.println("3. Ubah tiket");
                    System.out.println("4. Hapus tiket");
                    System.out.println("5. Keluar");

                    System.out.print("Pilih menu (1-5): ");
                    int pilihanMenu = scanner.nextInt();

                    // Melakukan CRUD sesuai pilihan menu
                    switch (pilihanMenu) {
                        case 1:
                            // Lihat daftar tiket
                            lihatDaftarTiket(connection, daftarTiketTerjual);
                            break;
                        case 2:
                            // Tambah tiket
                            tambahTiket(scanner, connection, daftarTiketTerjual);
                            break;
                        case 3:
                            // Ubah tiket
                            if (daftarTiketTerjual.isEmpty()) {
                                System.out.println("Daftar tiket masih kosong.");
                            } else {
                                ubahTiket(scanner, connection, daftarTiketTerjual);
                            }
                            break;
                        case 4:
                            // Hapus tiket
                            if (daftarTiketTerjual.isEmpty()) {
                                System.out.println("Daftar tiket masih kosong.");
                            } else {
                                hapusTiket(scanner, connection, daftarTiketTerjual);
                            }
                            break;
                        case 5:
                            // Keluar
                            System.out.println("Terima kasih telah menggunakan program ini.");
                            pembayaranSukses = true;
                            break;
                        default:
                            System.out.println("Pilihan menu tidak valid.");
                            break;
                    }

                } catch (PembayaranException e) {
                    // Menangani exception jika pembayaran tidak mencukupi
                    System.out.println("Error: " + e.getMessage());
                    System.out.println("Kekurangan: Rp " + e.getKekurangan());
                    System.out.println("Silakan lakukan pembayaran ulang.");

                } catch (Exception e) {
                    // Menangani exception umum
                    System.out.println("Error: " + e.getMessage());
                    scanner.nextLine(); // Membersihkan buffer
                }

            } while (!pembayaranSukses);

        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        } finally {
            // Menutup scanner
            scanner.close();
        }
    }

    private static void initializeDatabase(Connection connection) throws SQLException {
        // Membuat tabel tiket_bus jika belum ada
        String createTableQuery = "CREATE TABLE IF NOT EXISTS tiket_bus (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "nomor_tiket VARCHAR(255)," +
                "rute VARCHAR(255)," +
                "kelas VARCHAR(255)," +
                "jumlah_tiket INT," +
                "total_harga INT," +
                "tanggal_pembelian DATE," +
                "loket VARCHAR(255),";

        try (PreparedStatement statement = connection.prepareStatement(createTableQuery)) {
            statement.executeUpdate();
        }
    }

    private static void lihatDaftarTiket(Connection connection, List<TiketBus> daftarTiketTerjual) throws SQLException {
        // Menampilkan daftar tiket dari database
        String query = "SELECT * FROM tiket_bus";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("\nDaftar Tiket yang Telah Terjual:");
            while (resultSet.next()) {
                TiketBus tiket = new TiketBus(
                        resultSet.getString("rute"),
                        resultSet.getString("kelas"),
                        resultSet.getInt("total_harga"),
                        resultSet.getString("loket")
                );

                tiket.setNomorTiket(resultSet.getString("nomor_tiket"));
                tiket.setJumlahTiket(resultSet.getInt("jumlah_tiket"));
                tiket.setTanggalPembelian(resultSet.getDate("tanggal_pembelian"));

                daftarTiketTerjual.add(tiket);
                tiket.cetakInfoTiket();
            }
        }
    }

    private static void tambahTiket(Scanner scanner, Connection connection, List<TiketBus> daftarTiketTerjual) throws PembayaranException, SQLException {
        // Menampilkan pilihan tiket
        System.out.println("Pilihan Tiket:");
        System.out.println("1. Padang-Jakarta (Executive)      Harga: Rp 675.000");
        System.out.println("2. Padang-Jakarta (Executive Plus) Harga: Rp 775.000");
        System.out.println("3. Jakarta-Padang (Executive)      Harga: Rp 685.000");
        System.out.println("4. Jakarta-Padang (Executive Plus) Harga: Rp 785.000");

        // Meminta input pilihan tiket dari pengguna
        System.out.print("Masukkan nomor tiket yang dipilih          : ");
        int pilihanTiket = scanner.nextInt();

        // Menentukan objek tiket berdasarkan pilihan pengguna
        TiketBus tiketDipilih = null;

        // Menggunakan switch untuk menentukan tiket yang dipilih
        switch (pilihanTiket) {
            case 1:
                tiketDipilih = new TiketBus("Padang - Jakarta", "Executive", 675000, "Loket 1");
                break;
            case 2:
                tiketDipilih = new TiketBus("Padang - Jakarta", "Executive Plus", 775000, "Loket 1");
                break;
            case 3:
                tiketDipilih = new TiketBus("Jakarta - Padang", "Executive", 685000, "Loket 2");
                break;
            case 4:
                tiketDipilih = new TiketBus("Jakarta - Padang", "Executive Plus", 785000, "Loket 2");
                break;
            default:
                throw new IllegalArgumentException("Pilihan tiket tidak valid.");
        }

        // Menampilkan tanggal keberangkatan
        System.out.println("Tanggal keberangkatan (yyyy-MM-dd): ");
        String tanggalKeberangkatanStr = scanner.next();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date tanggalKeberangkatan = null;
        try {
            tanggalKeberangkatan = dateFormat.parse(tanggalKeberangkatanStr);
        } catch (ParseException e) {
            System.out.println("Error: Invalid date format. Please enter in the format yyyy-MM-dd.");
            return;
        }

        // Menampilkan nama penumpang
        System.out.print("Masukkan nama penumpang                    : ");
        String namaPenumpang = scanner.next();

        // Menampilkan jumlah tiket
        System.out.print("Masukkan jumlah tiket yang dibeli          : ");
        int jumlahTiket = scanner.nextInt();

        // Melakukan pembayaran
        tiketDipilih.beliTiket(namaPenumpang, jumlahTiket);

        // Menyimpan informasi tiket yang telah dibeli ke dalam ArrayList
        daftarTiketTerjual.add(tiketDipilih);

        // Menyimpan tiket ke database
        String sql = "INSERT INTO tiket_bus (nomor_tiket, rute, kelas, jumlah_tiket, total_harga, tanggal_pembelian, loket, Nama_Penumpang) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tiketDipilih.getNomorTiket());
            statement.setString(2, tiketDipilih.getRute());
            statement.setString(3, tiketDipilih.getKelas());
            statement.setInt(4, tiketDipilih.getJumlahTiket());
            statement.setInt(5, tiketDipilih.getTotalHarga());
            statement.setDate(6, new java.sql.Date(tanggalKeberangkatan.getTime()));
            statement.setString(7, tiketDipilih.getLoket());

            statement.executeUpdate();
        }

        System.out.println("Pembelian tiket berhasil.");
    }

    private static void ubahTiket(Scanner scanner, Connection connection, List<TiketBus> daftarTiketTerjual) throws SQLException {
        // Menampilkan daftar tiket
        lihatDaftarTiket(connection, daftarTiketTerjual);

        // Meminta input nomor tiket yang akan diubah
        System.out.print("Masukkan nomor tiket yang akan diubah: ");
        String nomorTiket = scanner.next();

        // Mencari tiket berdasarkan nomor tiket
        TiketBus tiket = null;
        for (TiketBus tiketTerjual : daftarTiketTerjual) {
            if (tiketTerjual.getNomorTiket().equals(nomorTiket)) {
                tiket = tiketTerjual;
                break;
            }
        }

        // Jika tiket tidak ditemukan
        if (tiket == null) {
            System.out.println("Tiket dengan nomor tersebut tidak ditemukan.");
            return;
        }

        // Implementasi untuk mengubah tiket di dalam ArrayList dan database
        // Anda dapat menambahkan logika perubahan data tiket di sini
        // ...

        System.out.println("Tiket berhasil diubah.");
    }

    private static void hapusTiket(Scanner scanner, Connection connection, List<TiketBus> daftarTiketTerjual) throws SQLException {
        // 1. Tampilkan daftar tiket yang tersedia untuk dihapus
        lihatDaftarTiket(connection, daftarTiketTerjual);

        // 2. Minta input nomor tiket yang ingin dihapus
        System.out.print("Masukkan nomor tiket yang ingin dihapus: ");
        String nomorTiket = scanner.next();

        // 3. Hapus tiket dari daftar dan database
        String deleteQuery = "DELETE FROM tiket_bus WHERE nomor_tiket = ?";
        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            deleteStatement.setString(1, nomorTiket);
            int rowsAffected = deleteStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Tiket berhasil dihapus.");
            } else {
                System.out.println("Tiket dengan nomor tersebut tidak ditemukan.");
            }
        }
    }
}
