package Version2.src;
public class KetQuaXetTuyen {

    // Static nested class cho xét tuyển THPT
    public static class THPT {
        private ThiSinh thiSinh;
        private ToHop toHop;

        public THPT(ToHop toHop, ThiSinh thiSinh) {
            this.toHop = toHop;
            this.thiSinh = thiSinh;
        }

        public ToHop gettoHop() {
            return this.toHop;
        }

        public double tinhDiemTHPT() {
            double tongDiem = 0.0;
            ToHop toHop = this.gettoHop();
            // Tính điểm theo từng môn trong tổ hợp
            for (int i = 0; i < thiSinh.getDiemThanhPhan().length; i++) {
                String mon = this.gettoHop().getMonThuocTH().get(i);
                double diem = thiSinh.getDiemThanhPhan()[i];
                double trongSoMon = toHop.getTrongSo().getOrDefault(mon, 1.0);
                tongDiem += diem * trongSoMon;
            }
            // Cộng điểm khuyến khích
            tongDiem += thiSinh.tinhTongDiemCong();

            return tongDiem;
        }
        public void display() {
            System.out.println("Xét tuyển THPT: Điểm xét tuyển là " + tinhDiemTHPT());
        }
    }

    // Static nested class cho xét tuyển ĐGNL (Đánh giá năng lực)
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

    // Static nested class cho xét tuyển ĐGTD (Đánh giá tư duy)
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

