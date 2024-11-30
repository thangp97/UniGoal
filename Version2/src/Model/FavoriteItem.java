package Version2.src.Model;

public class FavoriteItem {
    String maTruong;
    String tenTruong;
    String tenNganh;
    double diemSan;

    public FavoriteItem(String maTruong, String tenTruong, String tenNganh, double diemSan) {
        this.maTruong = maTruong;
        this.tenTruong = tenTruong;
        this.tenNganh = tenNganh;
        this.diemSan = diemSan;
    }

    @Override
    public String toString() {
        return maTruong + " - " + tenTruong + " - " + tenNganh + " - " + diemSan;
    }

    public String getMaTruong() {
        return maTruong;
    }

    public void setMaTruong(String maTruong) {
        this.maTruong = maTruong;
    }

    public String getTenTruong() {
        return tenTruong;
    }

    public void setTenTruong(String tenTruong) {
        this.tenTruong = tenTruong;
    }

    public String getTenNganh() {
        return tenNganh;
    }

    public void setTenNganh(String tenNganh) {
        this.tenNganh = tenNganh;
    }

    public double getDiemSan() {
        return diemSan;
    }

    public void setDiemSan(double diemSan) {
        this.diemSan = diemSan;
    }
}