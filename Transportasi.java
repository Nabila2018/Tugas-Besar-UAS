// Kelas abstrak sebagai superclass untuk Transportasi
abstract class Transportasi {
    protected String rute;
    protected String kelas;
    protected int tarif;
    protected String loket;

    public Transportasi(String rute, String kelas, int tarif, String loket) {
        this.rute = rute;
        this.kelas = kelas;
        this.tarif = tarif;
        this.loket = loket;
    }

    public String getRute() {
        return rute;
    }

    public String getKelas() {
        return kelas;
    }

    public int getTarif() {
        return tarif;
    }

    public String getLoket() {
        return loket;
    }

    public abstract int getTotalHarga(int jumlahTiket);
}
