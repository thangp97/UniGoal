package src;

import java.util.ArrayList;
import java.util.List;

public class DeXuat {
    private ThiSinh thiSinh;
    private List<DaiHoc> DHDuDK;

    public DeXuat(ThiSinh thiSinh, List<DaiHoc> DHDuDK) {
        this.thiSinh = thiSinh;
        this.DHDuDK = DHDuDK;
    }

    public List<String> deXuatCN(String khuVuc, String doiTuong) {
        List<String> suggestions = new ArrayList<>();

        for (DaiHoc daiHoc : DHDuDK) {
            for (ChuyenNganh chuyenNganh : daiHoc.getCN()) {
                for (ToHop toHop : chuyenNganh.getTHXT()) {
                    TinhDiemCong tinhDiemCong = new TinhDiemCong(thiSinh);
                    TinhKetQua calculator = new TinhKetQua(thiSinh, toHop, tinhDiemCong);
                    double score = calculator.tinhKQTH(khuVuc, doiTuong);

                    if (score >= chuyenNganh.getDiemSan()) {
                        suggestions.add(String.format("Trường: %s, Ngành: %s, Điểm của bạn: %.2f, Điểm chuẩn: %.2f",
                                daiHoc.getTenTruong(), chuyenNganh.getTenChuyenNganh(), score, chuyenNganh.getDiemSan()));
                    }
                }
            }
        }

        return suggestions;
    }
}

