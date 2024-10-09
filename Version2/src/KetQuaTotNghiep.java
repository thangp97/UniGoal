public class KetQuaTotNghiep {
    private ThiSinh thiSinh;
    private double diemTotNghiep;
    private boolean ketQua;
    public KetQuaTotNghiep(ThiSinh thiSinh) {
        this.thiSinh = thiSinh;
        this.diemTotNghiep = tinhDiemTotNghiep();
        this.ketQua = kiemTraKetQua();
    }

    private double tinhDiemTotNghiep() {
        double diemTBThanhPhan = 0;

        // Tính điểm trung bình thành phần
        for (double diem : thiSinh.getDiemThanhPhan()) {
            diemTBThanhPhan += diem;
        }

        diemTBThanhPhan /= thiSinh.getDiemThanhPhan().length; // Tính điểm trung bình thành phần

        // Công thức tính điểm tốt nghiệp (giả định)
        return 0.7 * thiSinh.getDiemTB12() + 0.3 * diemTBThanhPhan + thiSinh.getDiemKhuyenKhich();
    }

    // Phương thức kiểm tra xem thí sinh có đỗ tốt nghiệp hay không
    private boolean kiemTraKetQua() {
        // Giả định điểm tối thiểu để đỗ tốt nghiệp là 5.0
        return diemTotNghiep >= 5.0;
    }

    public String getKetQua() {
        return ketQua ? "Đỗ" : "Trượt";
    }
}
