package Version2.src.Model;

public class THPTScoreResult {
    private String maToHop;
    private double diemMon1;
    private double diemMon2;
    private double diemMon3;
    private double diemUuTien;
    private String khuVuc; // Khu vực của thí sinh
    private String doiTuong; // Đối tượng ưu tiên (VD: 01 - đối tượng 1, 02 - đối tượng 2, v.v.)

    // Constructor
    public THPTScoreResult(String maToHop, double diemMon1, double diemMon2, double diemMon3,
                           double diemUuTien, String khuVuc, String doiTuong) {
        this.maToHop = maToHop;
        this.diemMon1 = diemMon1;
        this.diemMon2 = diemMon2;
        this.diemMon3 = diemMon3;
        this.diemUuTien = diemUuTien;
        this.khuVuc = khuVuc;
        this.doiTuong = doiTuong;
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
            case "KV1": // Khu vực 1
                diemKhuVuc = 0.75;
                break;
            case "KV2-NT": // Khu vực 2-NT (Nội thành)
                diemKhuVuc = 0.5;
                break;
            case "KV2": // Khu vực 2
                diemKhuVuc = 0.25;
                break;
            case "KV3": // Khu vực 3
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
            case "Đối tượng 1": // Đối tượng 1
                diemDoiTuong = 2;
                break;
            case "Đối tượng 2": // Đối tượng 2
                diemDoiTuong = 2;
                break;
            case "Đối tượng 3": // Đối tượng 3
                diemDoiTuong = 2;
                break;
            case "Đối tượng 4": // Đối tượng 4
                diemDoiTuong = 2;
                break;
            case "Đối tượng 5": // Đối tượng 5
                diemDoiTuong = 1;
                break;
            case "Đối tượng 6": // Đối tượng 6
                diemDoiTuong = 1;
                break;
            case "Đối tượng 7": // Đối tượng 7
                diemDoiTuong = 1;
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

        // Tính điểm tổng: điểm môn
        double tongDiem = diemMon1 + diemMon2 + diemMon3;
        double diemcong = diemKhuVuc + diemDoiTuong;

        // Công thức tính điểm ưu tiên thí sinh được hưởng
        double diemUuTienThucTe = ((30 - tongDiem) / 7.5) * diemcong;

        return tongDiem + diemUuTienThucTe;
    }

}