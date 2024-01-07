// Exception untuk menangani kesalahan pembayaran
class PembayaranException extends Exception {
    private final int kekurangan;

    public PembayaranException(String message, int kekurangan) {
        super(message);
        this.kekurangan = kekurangan;
    }

    public int getKekurangan() {
        return kekurangan;
    }
}
