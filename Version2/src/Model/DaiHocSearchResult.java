package Version2.src.Model;

public class DaiHocSearchResult extends DaiHocLoadData{
    private String tenNganh;
    private double diemTrungTuyen;

    public DaiHocSearchResult(String maTruong, String tenTruong, double diemSan, String tenNganh, double diemTrungTuyen) {
        super(maTruong, tenTruong, diemSan);
        this.tenNganh = tenNganh;
        this.diemTrungTuyen = diemTrungTuyen;
    }

    public String getTenNganh() {
        return tenNganh;
    }

    public void setTenNganh(String tenNganh) {
        this.tenNganh = tenNganh;
    }

    public double getDiemTrungTuyen() {
        return diemTrungTuyen;
    }

    public void setDiemTrungTuyen(double diemTrungTuyen) {
        this.diemTrungTuyen = diemTrungTuyen;
    }
}
