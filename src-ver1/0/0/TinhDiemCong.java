package src;

import java.util.HashMap;
import java.util.Map;

public class DiemCong {
    private ThiSinh thiSinh;
    private Map<String, Double> diemCongTheoVung;  // Điểm cộng theo vùng
    private Map<String, Double> diemCongKhac;      // Điểm cộng theo các tiêu chí khác (đối tượng ưu tiên)

    public DiemCong(ThiSinh thiSinh) {
        this.thiSinh = thiSinh;
        this.diemCongTheoVung = taoDiemCongTheoVung();
        this.diemCongKhac = taoDiemCongKhac();
    }

    private Map<String, Double> taoDiemCongTheoVung() {
        Map<String, Double> diemCongVung = new HashMap<>();
        diemCongVung.put("KV1", 0.75);  // Khu vực 1
        diemCongVung.put("KV2", 0.5);   // Khu vực 2
        diemCongVung.put("KV2-NT", 0.25); // Khu vực 2 nông thôn
        diemCongVung.put("KV3", 0.0);   // Khu vực 3 không được cộng
        return diemCongVung;
    }

    private Map<String, Double> taoDiemCongKhac() {
        Map<String, Double> diemCongKhac = new HashMap<>();
        diemCongKhac.put("UT1", 2.0);  // Ưu tiên 1 (Dân tộc thiểu số, vùng đặc biệt khó khăn)
        diemCongKhac.put("UT2", 1.0);  // Ưu tiên 2
        return diemCongKhac;
    }

    public double DiemCongVung(String khuVuc) {
        return diemCongTheoVung.getOrDefault(khuVuc, 0.0);
    }

    public double DiemCongKhac(String doiTuong) {
        return diemCongKhac.getOrDefault(doiTuong, 0.0);
    }

    public double tinhTongDiemCong(String khuVuc, String doiTuong) {
        double diemCongVung = DiemCongVung(khuVuc);
        double diemCongKhac = DiemCongKhac(doiTuong);
        return diemCongVung + diemCongKhac;
    }
}