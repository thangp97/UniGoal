package src;

import java.util.Map;

public class KetQua {
    private String hinhThucThi;  // Ví dụ: "THPTQG"
    private Map<String, Double> diemThanhPhan;  // Map lưu điểm của từng môn

    public KetQua(String hinhThucThi, Map<String, Double> diemThanhPhan) {
        this.hinhThucThi = hinhThucThi;
        this.diemThanhPhan = diemThanhPhan;
    }

    public Map<String, Double> getDiemThanhPhan() {
        return diemThanhPhan;
    }
}