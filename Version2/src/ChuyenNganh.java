package Version2.src;

import java.util.List;

public class ChuyenNganh {
    private String tenChuyenNganh;
    private double diemChuan;
    private List<DaiHoc> tenTruong;

    public ChuyenNganh(String tenChuyenNganh, List<DaiHoc> tenTruong, double diemChuan) {
        this.tenChuyenNganh = tenChuyenNganh;
        this.tenTruong = tenTruong;
        this.diemChuan = diemChuan;
    }

    public List<DaiHoc> gettenTruong() {
        return tenTruong;
    }

    public double getDiemChuan() {
        return diemChuan;
    }

    public String getTenChuyenNganh() {
        return tenChuyenNganh;
    }
}
