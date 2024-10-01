package src;
import java.util.List;

public class ChuyenNganh {
    private String tenChuyenNganh;
    private double diemSan;
    private List<src.ToHop> toHopXetTuyen;

    public ChuyenNganh(String tenChuyenNganh, double diemSan, List<src.ToHop> toHopXetTuyen) {
        this.tenChuyenNganh = tenChuyenNganh;
        this.diemSan = diemSan;
        this.toHopXetTuyen = toHopXetTuyen;
    }

    public double getDiemSan() {
        return diemSan;
    }

    public List<src.ToHop> getTHXT() {
        return toHopXetTuyen;
    }

    public String getTenChuyenNganh() {
        return tenChuyenNganh;
    }
}