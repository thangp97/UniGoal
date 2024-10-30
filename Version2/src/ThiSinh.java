
package Version2.src;
import java.util.*;
public class ThiSinh {
    private String khuVuc;            // Khu vực của thí sinh
    private String doiTuongUuTien;    // Đối tượng ưu tiên
    private double diemTB12;          // Điểm trung bình lớp 12
    private double diemKhuyenKhich;   // Điểm khuyến khích
    private double[] diemThanhPhan;   // Điểm thành phần của từng môn thi

    public ThiSinh(String khuVuc, String doiTuongUuTien, double diemTB12, double diemKhuyenKhich, double[] diemThanhPhan) {
        this.khuVuc = khuVuc;
        this.doiTuongUuTien = doiTuongUuTien;
        this.diemTB12 = diemTB12;
        this.diemKhuyenKhich = diemKhuyenKhich;
        this.diemThanhPhan = diemThanhPhan;
    }

    public String getKhuVuc() {

        return khuVuc;
    }
    public String getDoiTuongUuTien() {

        return doiTuongUuTien;
    }
    public double getDiemTB12() {
        return diemTB12;
    }
    public double getDiemKhuyenKhich() {

        return diemKhuyenKhich;
    }
    public double[] getDiemThanhPhan() {

        return diemThanhPhan;
    }
    public double DiemCongVung(){
        Map<String, Double> diemCongVung = new HashMap<>();
        diemCongVung.put("KV1", 0.75);  // Khu vực 1
        diemCongVung.put("KV2", 0.5);   // Khu vực 2
        diemCongVung.put("KV2-NT", 0.25); // Khu vực 2 nông thôn
        diemCongVung.put("KV3", 0.0);   // Khu vực 3 không được cộng
        return diemCongVung.getOrDefault(this.getKhuVuc(), 0.0);
    }
    public double DiemCongKhac() {
        Map<String, Double> diemCongKhac = new HashMap<>();
        diemCongKhac.put("UT1", 2.0);  // Ưu tiên 1 (Dân tộc thiểu số, vùng đặc biệt khó khăn)
        diemCongKhac.put("UT2", 1.0);  // Ưu tiên 2
        return diemCongKhac.getOrDefault(this.getDoiTuongUuTien(), 0.0);
    }
    public double tinhTongDiemCong() {
        double diemCongVung = DiemCongVung();
        double diemCongKhac = DiemCongKhac();
        return diemCongVung + diemCongKhac;
    }
}
