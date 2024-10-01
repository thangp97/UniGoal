package src;

public class ThiSinh {
    private String hoVaTen;
    private String diaChi;
    private KetQua ketQua;

    public ThiSinh(String hoVaTen, String diaChi, KetQua ketQua) {
        this.hoVaTen = hoVaTen;
        this.diaChi = diaChi;
        this.ketQua = ketQua;
    }

    public KetQua getKetQua() {
        return ketQua;
    }
}
