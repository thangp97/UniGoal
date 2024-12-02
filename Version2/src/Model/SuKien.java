package Version2.src.Model;

public class SuKien {
    private String tenSuKien;
    private String ngayBatDau;
    private String ngayKetThuc;
    private String noiDung;

    public SuKien(String tenSuKien, String ngayBatDau, String ngayKetThuc, String noiDung) {
        this.tenSuKien = tenSuKien;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.noiDung = noiDung;
    }

    public String getTenSuKien() {
        return tenSuKien;
    }

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public String getNgayKetThuc() {
        return ngayKetThuc;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public String hienThiSuKien() {
        return "Sự kiện: " + tenSuKien + "\n" +
                "Bắt đầu: " + ngayBatDau + "\n" +
                "Kết thúc: " + ngayKetThuc + "\n" +
                "Nội dung: " + noiDung + "\n";
    }
}
