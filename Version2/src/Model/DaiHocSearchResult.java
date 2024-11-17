package Version2.src.Model;

public class DaiHocSearchResult extends DaiHocLoadData{
    private String tenNganh;

    public DaiHocSearchResult(String maTruong, String tenTruong, double diemSan, String tenNganh) {
        super(maTruong, tenTruong,diemSan);
        this.tenNganh = tenNganh;
    }

    public String getTenNganh() {
        return tenNganh;
    }

    public void setTenNganh(String tenNganh) {
        this.tenNganh = tenNganh;
    }
}
