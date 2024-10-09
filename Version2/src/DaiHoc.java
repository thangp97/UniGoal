import java.util.List;

public class DaiHoc {
    private String tenTruong;
    private String diaChiTruong;
    private List<ChuyenNganh> cacCN;

    public DaiHoc(String tenTruong, String diaChiTruong, List<ChuyenNganh> cacCN) {
        this.tenTruong = tenTruong;
        this.diaChiTruong = diaChiTruong;
        this.cacCN = cacCN;
    }

    public void setTenTruong(String tenTruong) {
        this.tenTruong = tenTruong;
    }

    public void setCacCN(List<ChuyenNganh> cacCN) {
        this.cacCN = cacCN;
    }

    public void setDiaChiTruong(String diaChiTruong) {
        this.diaChiTruong = diaChiTruong;
    }

    public List<ChuyenNganh> getCN() {
        return cacCN;
    }

    public String getTenTruong() {
        return tenTruong;
    }
}
