package Version2.src.Model;

import Version2.src.View.LoginView;

public class DaiHocFavoriteData extends DaiHocLoadData{
    private String tenNganh, stt;
    private double diemTrungTuyen;
    private static int ID = 1;
    private String username;


    public DaiHocFavoriteData(String stt, String maTruong, String tenTruong, double diemSan, String tenNganh, double diemTrungTuyen, String username) {
        super(maTruong, tenTruong, diemSan);
        this.stt = String.format("%03d", ID++);
        this.tenNganh = tenNganh;
        this.diemTrungTuyen = diemTrungTuyen;
        this.username = username;
    }

    @Override
    public String toString() {
        return stt + ". "+ getMaTruong() + " - " + getTenTruong() + " - " + tenNganh + " - " + diemTrungTuyen;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}