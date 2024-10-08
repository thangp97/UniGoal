package Version2.src;

// UniversityAdmission.java
public class TinhDiemXetTuyen {

    // Static nested class cho xét tuyển THPT
    public static class THPT {
        private String toHop;
        private double[] diemThanhPhan;
        private double diemCong;

        public THPT(String toHop, double[] diemThanhPhan, double diemCong) {
            this.toHop = toHop;
            this.diemThanhPhan = diemThanhPhan;
            this.diemCong = diemCong;
        }

        // Phương thức tính điểm xét tuyển THPT
        public double tinhDiemXetTuyen() {
            double tongDiem = 0;
            for (double diem : diemThanhPhan) {
                tongDiem += diem;
            }
            return tongDiem + diemCong;
        }

        public void display() {
            System.out.println("Xét tuyển THPT: Điểm xét tuyển là " + tinhDiemXetTuyen());
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

