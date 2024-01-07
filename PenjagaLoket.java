// Kelas PenjagaLoket untuk menyimpan data diri penjaga loket
class PenjagaLoket {
    private String nama;
    private String nomorID;

    public PenjagaLoket(String nama, String nomorID) {
        this.nama = nama;
        this.nomorID = nomorID;
    }

    public String getNama() {
        return nama;
    }

    public String getNomorID() {
        return nomorID;
    }
}
