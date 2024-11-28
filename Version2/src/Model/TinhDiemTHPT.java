package Version2.src.Model;

public class TinhDiemTHPT {
    private String hoTen;
    private String maToHop;
    private double diemMon1;
    private double diemMon2;
    private double diemMon3;
    private double diemUuTien;
    private String khuVuc; // Khu vực của thí sinh
    private String doiTuong; // Đối tượng ưu tiên (VD: 01 - đối tượng 1, 02 - đối tượng 2, v.v.)

    // Constructor
    public TinhDiemTHPT(String hoTen, String maToHop, double diemMon1, double diemMon2, double diemMon3,
                   double diemUuTien, String khuVuc, String doiTuong) {
        this.hoTen = hoTen;
        this.maToHop = maToHop;
        this.diemMon1 = diemMon1;
        this.diemMon2 = diemMon2;
        this.diemMon3 = diemMon3;
        this.diemUuTien = diemUuTien;
        this.khuVuc = khuVuc;
        this.doiTuong = doiTuong;
    }

    // Getters
    public String getHoTen() {
        return hoTen;
    }

    public String getMaToHop() {
        return maToHop;
    }

    public double getDiemMon1() {
        return diemMon1;
    }

    public double getDiemMon2() {
        return diemMon2;
    }

    public double getDiemMon3() {
        return diemMon3;
    }

    public double getDiemUuTien() {
        return diemUuTien;
    }

    public String getKhuVuc() {
        return khuVuc;
    }

    public String getDoiTuong() {
        return doiTuong;
    }

    // Hàm tính điểm cộng khu vực
    private double tinhDiemKhuVuc() {
        double diemKhuVuc = 0.0;
        switch (khuVuc) {
            case "KhuVuc1": // Khu vực 1
                diemKhuVuc = 1.0;
                break;
            case "KhuVuc2NT": // Khu vực 2-NT (Nội thành)
                diemKhuVuc = 0.75;
                break;
            case "KhuVuc2": // Khu vực 2
                diemKhuVuc = 0.5;
                break;
            case "KhuVuc3": // Khu vực 3
                diemKhuVuc = 0.0;
                break;
            default:
                diemKhuVuc = 0.0; // Nếu không có khu vực hợp lệ, không cộng điểm
        }
        return diemKhuVuc;
    }

    // Hàm tính điểm cộng đối tượng
    private double tinhDiemDoiTuong() {
        double diemDoiTuong = 0.0;
        switch (doiTuong) {
            case "01": // Đối tượng 1
                diemDoiTuong = 2.5;
                break;
            case "02": // Đối tượng 2
                diemDoiTuong = 1.5;
                break;
            case "03": // Đối tượng 3
                diemDoiTuong = 1.0;
                break;
            default:
                diemDoiTuong = 0.0; // Nếu không có đối tượng hợp lệ, không cộng điểm
        }
        return diemDoiTuong;
    }

    // Tính tổng điểm xét tuyển (cộng điểm khu vực và đối tượng)
    public double tinhDiemXetTuyen() {
        double diemKhuVuc = tinhDiemKhuVuc();
        double diemDoiTuong = tinhDiemDoiTuong();

        return diemMon1 + diemMon2 + diemMon3 + diemUuTien + diemKhuVuc + diemDoiTuong;
    }

}
