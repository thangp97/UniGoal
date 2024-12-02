
# UniGoal: Final Version

Hiện nay học sinh đặc biệt là những em sắp bước vào kì thi quan trọng nhất của cuộc đời là kì thi cuối cấp 3 để chuẩn bị bước vào con đường Đại Học. Nhưng các sĩ tử vẫn khó khăn trong việc tính điểm, tìm trường và đặc biệt là gợi ý các trường đại học cho thí sinh. Đó là lí do UniGoal được ra đời, Unigoal là Hệ thống tính toán điểm xét tuyển Đại học tại khu vực miền Bắc với đầy đủ các tổ hợp thi THPTQG, ĐGNL, ĐGTD và những phương thức xét tuyển khác, từ đó đưa ra những gợi ý danh sách nguyện vọng, ngành phù hợp với điểm số của thí sinh.


## Authors

- [Phạm Mạnh Thắng](https://www.facebook.com/ThangP97/)

- [Lê Hoàng Tùng](https://www.facebook.com/profile.php?id=100009398140799)

- [Nguyễn Chí Quyền](https://www.facebook.com/quyen.nguyenchi.923)

- [Nguyễn Minh Tuấn](https://www.facebook.com/nguyenminhtuan1102)

- [Đỗ Văn Đức](https://www.facebook.com/dovanduc206)

## Phân tích thiết kế các lớp

- SuKien
    - Thuộc tính:
        - tenSuKien: Tên sự kiện
        - ngayBatDau: Ngày bắt đầu
        - ngayKetThuc: Ngày kết thúc
        - noiDung: Nội dung
    - Phương thức:
        - getTenSuKien: Trả về tên sự kiện
        - getNgayBatDau: Trả về ngày bắt đầu
        - getNgayKetThuc: Trả về ngày kết thúc
        - getNoiDung: Trả về nội dung
        - hienThiSuKien: Trả về sự kiện

- THPTScoreResult
    - Thuộc tính:
        - maToHop: Chọn tổ hợp
        - diemMon1: Điểm môn 1
        - diemMon2: Điểm môn 2
        - diemMon3: Điểm môn 3
        - diemUuTien: Điểm ưu tiên
        - khuVuc: Khu vực
        - doiTuong: Đối tượng
    - Phương thức:
        - getMaToHop: Trả về mã tổ hợp
        - getDiemMon1: Trả về điểm môn 1
        - getDiemMon2: Trả về điểm môn 2
    - getDiemMon3: Trả về điểm môn 3
        - getDiemUuTien: Trả về điểm ưu tiên
        - getKhuVuc: Trả về khu vực
        - getDoiTuong: Trả về đối tượng
        - tinhDiemKhuVuc: Trả về Điểm Khu Vực
        - tinhDiemDoiTuong: Trả về Điểm Đối Tượng
        - tinhDiemXetTuyen: Trả về Điểm Xét Tuyển

- User
    - Thuộc tính:
        - connection: Kết nối với database
    - Phương thức:
        - login: Đăng nhập
        - register: Đăng kí

- DaiHocLoadData
    - Thuộc tính:
        - maTruong: Mã của trường đại học
        - tenTruong: Tên của trường đại học
        - diemSan: Điểm sàn của trường đại học
    - Phương thức:
        - getMaTruong: Trả về mã trường
        - setMaTruong: Cập nhật mã trường
        - getTenTruong: Trả về tên trường
        - setTenTruong: Cập nhật tên trường
        - getDiemSan: Trả về Điểm Sàn
        - setDiemSan: Cập nhật Điểm Sàn

- DaiHocSearchResult (Kế thừa DaiHocLoadData)
    - Thuộc tính:
        - tenNganh: Tên ngành
        - diemTrungTuyen: Điểm Trúng tuyển của ngành
    - Phương Thức:
        - getTenNganh: Trả về tên ngành
        - setTenNganh: Cập nhật tên ngành
        - getDiemTrungTuyen: Trả về điểm trúng tuyển
        - setDiemTrungTuyen: Cập nhật điểm trúng tuyển

- DaiHocFavoriteData (Kế thừa DaiHocLoadData)
    - Thuộc tính:
        - tenNganh: Tên ngành yêu thích
        - stt: Số thứ tự ngành yêu thích
        - diemTrungTuyen: Điểm trúng tuyển của ngành đó
        - ID: Biến tăng số thứ tự lựa chọn của người dùng
    - Phương thức:
        - toString: Trả về trường yêu thích
        - getTenNganh: Trả về tên ngành
        - setTenNganh: Cập nhật tên ngành
        - getDiemTrungTuyen: Trả về điểm trúng tuyển
        - setDiemTrungTuyen: Cập nhật điểm trúng tuyển
## UML

![image](https://github.com/user-attachments/assets/ed1371f8-edf5-471f-8b96-36fbeb807f73)


## Xem xét hiện thực các tính chất OOP vào Project



## Ứng dụng có giao diện

To run tests, run the following command

```bash
  npm run test
```

