package src;

import java.util.Map;

public class TinhKetQua {
    private ThiSinh thiSinh;
    private ToHop toHop;
    private TinhDiemCong tinhDiemCong;

    public TinhKetQua(ThiSinh thiSinh, ToHop toHop, TinhDiemCong tinhDiemCong) {
        this.thiSinh = thiSinh;
        this.toHop = toHop;
        this.tinhDiemCong = tinhDiemCong;
    }

    public double tinhKQTH(String khuVuc, String doiTuong) {
        double tongDiem = 0.0;
        Map<String, Double> diemThanhPhan = thiSinh.getKetQua().getDiemThanhPhan();

        for (String mon : toHop.getMonThuocTH()) {
            double diem = diemThanhPhan.getOrDefault(mon, 0.0);
            double trongSoMon = toHop.getTrongSo().getOrDefault(mon, 1.0);
            tongDiem += diem * trongSoMon;
        }

        double diemCong = tinhDiemCong.tinhTongDiemCong(khuVuc, doiTuong);
        tongDiem += diemCong;

        return tongDiem;
    }
}

