
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

    public List<ChuyenNganh> getCN() {
        return cacCN;
    }

    public String getTenTruong() {
        return tenTruong;
    }
}
