package Version2.src;
import java.util.List;

public class ChuyenNganh {
    private String tenChuyenNganh;
    private double diemChuan;
    private List<DaiHoc> tenTruongCoCN;

    public ChuyenNganh(String tenChuyenNganh, List<DaiHoc> tenTruongCoCN, double diemChuan) {
        this.tenChuyenNganh = tenChuyenNganh;
        this.tenTruongCoCN = tenTruongCoCN;
        this.diemChuan = diemChuan;
    }

    public void setTenChuyenNganh(String tenChuyenNganh) {
        this.tenChuyenNganh = tenChuyenNganh;
    }

    public void setDiemChuan(double diemChuan) {
        this.diemChuan = diemChuan;
    }

    public void setTenTruong(List<DaiHoc> tenTruong) {
        this.tenTruongCoCN = tenTruong;
    }

    public List<DaiHoc> getTenTruongCoCN() {
        return tenTruongCoCN;
    }

    public double getDiemChuan() {
        return diemChuan;
    }

    public String getTenChuyenNganh() {
        return tenChuyenNganh;
    }
}
