public class ThiSinh {
    private String khuVuc;            // Khu vực của thí sinh
    private String doiTuongUuTien;    // Đối tượng ưu tiên
    private double diemTB12;          // Điểm trung bình lớp 12
    private double diemKhuyenKhich;   // Điểm khuyến khích
    private double[] diemThanhPhan;   // Điểm thành phần của từng môn thi

    public ThiSinh(String khuVuc, String doiTuongUuTien, double diemTB12, double diemKhuyenKhich, double[] diemThanhPhan) {
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
}
