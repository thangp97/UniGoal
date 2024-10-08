public class KetQuaTHPT {
    private ThiSinh thiSinh;
    private ToHop toHop;
    private DiemCong diemCong;

    public KetQuaTHPT(ThiSinh thiSinh, ToHop toHop) {
        this.thiSinh = thiSinh;
        this.toHop = toHop;
        this.diemCong = new DiemCong(thiSinh);  // Khởi tạo điểm cộng dựa trên thí sinh
    }

    public double KQTH() {
        double tongDiem = 0.0;
        double[] diemThanhPhan = thiSinh.getDiemThanhPhan();

        // Tính điểm theo từng môn trong tổ hợp
        for (int i = 0; i < toHop.getMonThuocTH().size(); i++) {
            String mon = toHop.getMonThuocTH().get(i);
            double diem = diemThanhPhan[i];
            double trongSoMon = toHop.getTrongSo().getOrDefault(mon, 1.0);
            tongDiem += diem * trongSoMon;
        }
        // Cộng điểm khuyến khích
        tongDiem += diemCong.tinhTongDiemCong()+ thiSinh.getDiemKhuyenKhich();

        return tongDiem;
    }
}
