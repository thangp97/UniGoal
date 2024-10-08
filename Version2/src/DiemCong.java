import java.util.HashMap;
import java.util.Map;

public class DiemCong {
    private ThiSinh thiSinh;  // Đối tượng thí sinh
    private Map<String, Double> diemCongTheoVung;  // Điểm cộng theo vùng
    private Map<String, Double> diemCongKhac;      // Điểm cộng theo các tiêu chí khác (đối tượng ưu tiên)

    public DiemCong(ThiSinh thiSinh) {
        this.thiSinh = thiSinh;
        this.diemCongTheoVung = taoDiemCongTheoVung();
        this.diemCongKhac = taoDiemCongKhac();
    }

    // Tạo bản đồ điểm cộng theo khu vực
    private Map<String, Double> taoDiemCongTheoVung() {
        Map<String, Double> diemCongVung = new HashMap<>();
        diemCongVung.put("KV1", 0.75);  // Khu vực 1
        diemCongVung.put("KV2", 0.5);   // Khu vực 2
        diemCongVung.put("KV2-NT", 0.25); // Khu vực 2 nông thôn
        diemCongVung.put("KV3", 0.0);   // Khu vực 3 không được cộng
        return diemCongVung;
    }

    // Tạo bản đồ điểm cộng theo các tiêu chí khác
    private Map<String, Double> taoDiemCongKhac() {
        Map<String, Double> diemCongKhac = new HashMap<>();
        diemCongKhac.put("UT1", 2.0);  // Ưu tiên 1 (Dân tộc thiểu số, vùng đặc biệt khó khăn)
        diemCongKhac.put("UT2", 1.0);  // Ưu tiên 2
        return diemCongKhac;
    }

    // Tính điểm cộng theo khu vực
    public double DiemCongVung() {
        return diemCongTheoVung.getOrDefault(thiSinh.getKhuVuc(), 0.0);
    }

    // Tính điểm cộng theo đối tượng
    public double DiemCongKhac() {
        return diemCongKhac.getOrDefault(thiSinh.getDoiTuongUuTien(), 0.0);
    }

    // Tính tổng điểm cộng
    public double tinhTongDiemCong() {
        double diemCongVung = DiemCongVung();
        double diemCongKhac = DiemCongKhac();
        return diemCongVung + diemCongKhac;
    }
}
