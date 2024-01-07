// Interface untuk mengelola pembayaran
interface Pembayaran {
    void prosesPembayaran(int jumlahPembayaran) throws PembayaranException;
}
