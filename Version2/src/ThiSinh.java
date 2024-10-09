import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ThiSinh {
    private String khuVuc;            // Khu vực của thí sinh
    private String doiTuongUuTien;    // Đối tượng ưu tiên
    private double diemTB12;          // Điểm trung bình lớp 12
    private double diemKhuyenKhich;   // Điểm khuyến khích
    private double[] diemThanhPhan;   // Điểm thành phần của từng môn thi
    private ToHop toHop;

    public ThiSinh(String khuVuc, String doiTuongUuTien, double diemTB12, double diemKhuyenKhich, double[] diemThanhPhan, String hinhThucThi) {
        this.khuVuc = khuVuc;
        this.doiTuongUuTien = doiTuongUuTien;
        this.diemTB12 = diemTB12;
        this.diemKhuyenKhich = diemKhuyenKhich;
        this.diemThanhPhan = diemThanhPhan;
    }

    public String getKhuVuc() {
        return khuVuc;
    }
    public String getDoiTuongUuTien() {
        return doiTuongUuTien;
    }
    public double getDiemTB12() {
        return diemTB12;
    }
    public double getDiemKhuyenKhich() {
        return diemKhuyenKhich;
    }
    public double[] getDiemThanhPhan() {
        return diemThanhPhan;
    }

    public double DiemCongVung(){
        Map<String, Double> diemCongVung = new HashMap<>();
        diemCongVung.put("KV1", 0.75);  // Khu vực 1
        diemCongVung.put("KV2", 0.5);   // Khu vực 2
        diemCongVung.put("KV2-NT", 0.25); // Khu vực 2 nông thôn
        diemCongVung.put("KV3", 0.0);   // Khu vực 3 không được cộng
        return diemCongVung.getOrDefault(this.getKhuVuc(), 0.0);
    }
    public double DiemCongKhac() {
        Map<String, Double> diemCongKhac = new HashMap<>();
        diemCongKhac.put("UT1", 2.0);  // Ưu tiên 1 (Dân tộc thiểu số, vùng đặc biệt khó khăn)
        diemCongKhac.put("UT2", 1.0);  // Ưu tiên 2
        return diemCongKhac.getOrDefault(this.getDoiTuongUuTien(), 0.0);
    }
    public double tinhTongDiemCong() {
        double diemCongVung = DiemCongVung();
        double diemCongKhac = DiemCongKhac();
        return diemCongVung + diemCongKhac;
    }
    public double tinhDiemTotNghiep() {
        double diemTBThanhPhan = 0;

        // Tính điểm trung bình thành phần
        for (double diem : this.getDiemThanhPhan()) {
            diemTBThanhPhan += diem;
        }

        diemTBThanhPhan /= this.getDiemThanhPhan().length; // Tính điểm trung bình thành phần

        // Công thức tính điểm tốt nghiệp (giả định)
        return 0.7 * this.getDiemTB12() + 0.3 * diemTBThanhPhan + this.getDiemKhuyenKhich();
    }
    private boolean kiemTraKetQua() {
        // Giả định điểm tối thiểu để đỗ tốt nghiệp là 5.0
        return this.tinhDiemTotNghiep() >= 5.0;
    }
    public String getKetQuaTotNghiep() {
        return this.kiemTraKetQua() ? "Đỗ" : "Trượt";
    }
    public ToHop getToHop() {
        return this.toHop;
    }
    public double tinhDiemXetTuyen() {
        double tongDiem = 0.0;
        ToHop toHop = this.getToHop();
        // Tính điểm theo từng môn trong tổ hợp
        for (int i = 0; i < this.getDiemThanhPhan().length; i++) {
            String mon = this.getToHop().getMonThuocTH().get(i);
            double diem = this.getDiemThanhPhan()[i];
            double trongSoMon = toHop.getTrongSo().getOrDefault(mon, 1.0);
            tongDiem += diem * trongSoMon;
        }
        // Cộng điểm khuyến khích
        tongDiem += this.tinhTongDiemCong();

        return tongDiem;
    }
    public void display() {
        System.out.println("Xét tuyển THPT: Điểm xét tuyển là " + tinhDiemXetTuyen());
    }
    public static class DGNL {
        private double diemThi;

        public DGNL(double diemThi) {
            this.diemThi = diemThi;
        }

        // Phương thức tính điểm xét tuyển ĐGNL
        public double tinhDiemXetTuyen() {
            return diemThi;
        }

        public void display() {
            System.out.println("Xét tuyển ĐGNL: Điểm xét tuyển là " + tinhDiemXetTuyen());
        }
    }
    public static class DGTD {
        private String toHop;
        private double diemThi;

        public DGTD(String toHop, double diemThi) {
            this.toHop = toHop;
            this.diemThi = diemThi;
        }

        // Phương thức tính điểm xét tuyển ĐGTD
        public double tinhDiemXetTuyen() {
            return diemThi;
        }

        public void display() {
            System.out.println("Xét tuyển ĐGTD: Điểm xét tuyển là " + tinhDiemXetTuyen());
        }
    }
}

