import java.text.SimpleDateFormat;
import java.util.Date;

public class TiketBus {
    private String nomorTiket;
    private String rute;
    private String kelas;
    private int harga;
    private String loket;
    private int jumlahTiket;
    private Date tanggalPembelian;

    public TiketBus(String rute, String kelas, int harga, String loket) {
        setNomorTiket(generateNomorTiket());
        this.rute = rute;
        this.kelas = kelas;
        this.harga = harga;
        this.loket = loket;
    }

    public String getNomorTiket() {
        return nomorTiket;
    }

    // Tambahkan metode setNomorTiket
    public void setNomorTiket(String nomorTiket) {
        this.nomorTiket = nomorTiket;
    }

    public String getRute() {
        return rute;
    }

    public String getKelas() {
        return kelas;
    }

    public int getHarga() {
        return harga;
    }

    public String getLoket() {
        return loket;
    }

    public int getJumlahTiket() {
        return jumlahTiket;
    }

    public void setJumlahTiket(int jumlahTiket) {
        this.jumlahTiket = jumlahTiket;
    }

    public String getNamaPenumpang(String namaPenumpang) {
        return namaPenumpang;
    }




    

    // Tambahkan getter dan setter untuk tanggalPembelian
    public Date getTanggalPembelian() {
        return tanggalPembelian;
    }

    public void setTanggalPembelian(Date tanggalPembelian) {
        this.tanggalPembelian = tanggalPembelian;
    }

    // Metode untuk menghitung total harga
    public int hitungTotalHarga() {
        return harga * jumlahTiket;
    }
    
    // Metode untuk melakukan pembelian tiket
    public void beliTiket(String namaPenumpang, int jumlahTiket) {
        setJumlahTiket(jumlahTiket); // Atur jumlah tiket
        setTanggalPembelian(new Date()); // Atur tanggal pembelian saat tiket dibeli
        System.out.println("\nPembelian Tiket");
        System.out.println("Nomor Tiket       : " + nomorTiket);
        System.out.println("Rute              : " + rute);
        System.out.println("Kelas             : " + kelas);
        System.out.println("Harga Tiket       : Rp " + harga);
        System.out.println("Jumlah Tiket      : " + jumlahTiket);
        System.out.println("Total Harga       : Rp " + hitungTotalHarga()); // Menggunakan metode hitungTotalHarga() untuk mendapatkan total harga
        System.out.println("Tanggal Pembelian : " + formatDate(getTanggalPembelian())); // Format tanggal menggunakan metode formatDate
        System.out.println("Penumpang         : " + namaPenumpang);
        System.out.println("Loket             : " + loket);
    }
    

    // Metode untuk mencetak informasi tiket
    public void cetakInfoTiket() {
        System.out.println("Nomor Tiket: " + nomorTiket);
        System.out.println("Rute       : " + rute);
        System.out.println("Kelas      : " + kelas);
        System.out.println("Harga      : Rp " + harga);
        System.out.println("Loket      : " + loket);
        System.out.println("Jumlah Tiket: " + jumlahTiket);
        System.out.println("Tanggal Pembelian: " + formatDate(tanggalPembelian));
        System.out.println("--------------");
    }

    // Metode untuk generate nomor tiket sederhana
    private String generateNomorTiket() {
        return "T" + System.currentTimeMillis();
    }

    // Metode untuk format tanggal sebagai string
    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return dateFormat.format(date);
    }

    public int getTotalHarga() {
        return 0;
    }
}

