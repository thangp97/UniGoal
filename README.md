
# **UniGoal: Final Version**

*Hiện nay học sinh đặc biệt là những em sắp bước vào kì thi quan trọng nhất của cuộc đời là kì thi cuối cấp 3 để chuẩn bị bước vào con đường Đại Học. Nhưng các sĩ tử vẫn khó khăn trong việc tính điểm, tìm trường và đặc biệt là gợi ý các trường đại học cho thí sinh. Đó là lí do UniGoal được ra đời, Unigoal là Hệ thống tính toán điểm xét tuyển Đại học tại khu vực miền Bắc với đầy đủ các tổ hợp thi THPTQG, ĐGNL, ĐGTD và những phương thức xét tuyển khác, từ đó đưa ra những gợi ý danh sách nguyện vọng, ngành phù hợp với điểm số của thí sinh.*


## **Authors**

- [Phạm Mạnh Thắng](https://www.facebook.com/ThangP97/) : B22DCVT527

- [Lê Hoàng Tùng](https://www.facebook.com/profile.php?id=100009398140799) : B22DCVT497

- [Nguyễn Chí Quyền](https://www.facebook.com/quyen.nguyenchi.923) : B22DCVT432

- [Nguyễn Minh Tuấn](https://www.facebook.com/nguyenminhtuan1102) : B22DCVT486

- [Đỗ Văn Đức](https://www.facebook.com/dovanduc206) : B22DCDT092

## **Phân tích thiết kế các lớp**

- **Calendar**
    - Thuộc tính:
        - currentDate: Ngày Tháng Năm hiện tại
        - notes: Ghi chú
    - Phương Thức:
        - Calendar: Constructor nhập Ngày Tháng Năm
        - addNote: Thêm ghi chú
        - updateNote: Cập nhật ghi chú
        - getNoteForDate: Trả về ghi chú của date
        - removeNote: Xóa ghi chú
        - getCurrentDate: Trả về Date hiện tại
        - changeMonth: Thay đổi Tháng
        - changeYear: Thay đổi Năm
        - getDayOfWeekForFirstDayOfMonth: Trả về ngày đầu tiên của tháng
        - getDaysInMonth: Trả về ngày của Tháng
        - setCurrentDate: Cập nhật Date hiện tại

- **Note**
    - Thuộc tính:
        - date: Ngày Tháng Năm
        - content: Nội dung
    - Phương thức:
        - Note: Constructor nhập date và content
        - getDate: Trả về Ngày Tháng Năm
        - getContent: Trả về nội dung
        - setContent: Cập nhật nội dung
        - toString: Trả về Note

- **THPTScoreResult**
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

- **User**
    - Thuộc tính:
        - connection: Kết nối với database
        - username: Người dùng
    - Phương thức:
        - login: Đăng nhập
        - register: Đăng kí
        - changePassword: Đổi mật khẩu
        - favourite: Danh sách yêu thích của user

- **DaiHocLoadData**
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

- **DaiHocSearchResult (Kế thừa DaiHocLoadData)**
    - Thuộc tính:
        - tenNganh: Tên ngành
        - diemTrungTuyen: Điểm Trúng tuyển của ngành
    - Phương Thức:
        - getTenNganh: Trả về tên ngành
        - setTenNganh: Cập nhật tên ngành
        - getDiemTrungTuyen: Trả về điểm trúng tuyển
        - setDiemTrungTuyen: Cập nhật điểm trúng tuyển

- **DaiHocFavoriteData (Kế thừa DaiHocLoadData)**
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
## **UML**

![UML](https://scontent.fhan15-2.fna.fbcdn.net/v/t1.15752-9/465686759_555936200527887_3590526760142539730_n.png?_nc_cat=104&ccb=1-7&_nc_sid=9f807c&_nc_eui2=AeFonNYSdHb6T1oaWVI-aN8uDVsfIW-A98cNWx8hb4D3x6Hpw_MDjp4Vm0L4ZSkrovu5SKK8UOeCbVvostUJqRpI&_nc_ohc=UwDYAL1-hBwQ7kNvgGt_JyD&_nc_zt=23&_nc_ht=scontent.fhan15-2.fna&oh=03_Q7cD1QH5byVBs7kyAHEmfDRB2UukW6IgPk5X2ZhcViuP3d_vGQ&oe=6775699C)


## **Xem xét hiện thực các tính chất OOP vào Project**

### **Ví Dụ Với File CalendarView.java**

#### **1. Đóng gói (Encapsulation)**
- **Mô tả:**
  - **CalendarView** đóng gói logic hiển thị giao diện (View) và tương tác với người dùng.
  - **Calendar** đóng gói các logic liên quan đến ngày tháng và năm, như xác định ngày đầu tiên trong tháng hoặc số ngày trong tháng.
  - **Note** có thể được sử dụng để lưu trữ hoặc xử lý ghi chú liên quan đến các ngày cụ thể.
- **Thể hiện:**
  - Các trường dữ liệu (biến) trong `CalendarView` và `Calendar` được khai báo với phạm vi truy cập `private`, ví dụ:
    ```java
    private JTable calendarTable;
    private Calendar calendar;
    ```
  - Các lớp chỉ cung cấp những phương thức cần thiết để tương tác, như `updateCalendar()`, `changeMonth(int increment)`, `changeYear(int increment)`.

- **Lợi ích:**
  - Giúp ẩn chi tiết triển khai nội bộ của mỗi lớp, chỉ cung cấp những gì cần thiết ra bên ngoài.

---

#### **2. Tính kế thừa (Inheritance)**
- **Mô tả:**
  - `CalendarView` kế thừa từ `JPanel`, một lớp trong thư viện Swing, để tận dụng các tính năng giao diện có sẵn.
  - Kế thừa được thể hiện qua từ khóa `extends`:
    ```java
    public class CalendarView extends JPanel
    ```

- **Lợi ích:**
  - `CalendarView` không cần xây dựng lại toàn bộ logic hiển thị giao diện, chỉ cần bổ sung hoặc tùy chỉnh các thành phần cần thiết.

---

#### **3. Tính đa hình (Polymorphism)**
- **Mô tả:**
  - **Override phương thức:** 
    - `HighlightRenderer` ghi đè (`override`) phương thức `getTableCellRendererComponent` của lớp `DefaultTableCellRenderer` để tùy chỉnh cách hiển thị các ô trong bảng:
      ```java
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      }
      ```
  - **Event handling:** 
    - Listener (`addActionListener`, `addMouseListener`) là một ví dụ của tính đa hình, vì bạn cung cấp logic cụ thể thông qua các phương thức ẩn danh (`mouseClicked`, `actionPerformed`).

- **Lợi ích:**
  - Cho phép bạn tùy chỉnh hành vi của các thành phần hoặc mở rộng chúng mà không cần thay đổi lớp cơ bản.

---

#### **4. Tính trừu tượng (Abstraction)**
- **Mô tả:**
  - Mỗi lớp chỉ tập trung vào nhiệm vụ riêng:
    - **Calendar** trừu tượng hóa dữ liệu và logic liên quan đến ngày tháng, ví dụ:
      - Xác định số ngày trong một tháng (`getDaysInMonth()`).
      - Xác định ngày bắt đầu của tháng (`getDayOfWeekForFirstDayOfMonth()`).
    - **CalendarView** trừu tượng hóa giao diện và tương tác người dùng.
    - **Note** trừu tượng hóa dữ liệu liên quan đến ghi chú.
  - Chi tiết cụ thể của việc tính toán hoặc hiển thị bị ẩn bên trong các lớp, chỉ cung cấp giao diện (`API`) để sử dụng.

- **Lợi ích:**
  - Giảm phức tạp cho mã, chia nhỏ vấn đề thành các lớp/đơn vị có chức năng riêng biệt.
## Ứng dụng có giao diện

Để khởi chạy, có thể chạy từ ứng dụng có hỗ trợ ngôn ngữ Java hoặc chạy lệnh sau (Yêu cầu đã cài Java JDK):

```bash
  java Main
```
### Giao Diện Đăng Nhập
![Giao Diện Đăng Nhập](https://scontent.fhan15-1.fna.fbcdn.net/v/t1.15752-9/467470426_1221645345598837_1764695101920876432_n.png?_nc_cat=109&ccb=1-7&_nc_sid=9f807c&_nc_eui2=AeGVSkwUqaBjPCuxPCoFiMiAqWLfoylHj5GpYt-jKUePkaxVm659tEPNQnz6tDjhYin_f_SyKMt_I6t4Zf92uflz&_nc_ohc=LiOWOCle6uEQ7kNvgEAGm5H&_nc_zt=23&_nc_ht=scontent.fhan15-1.fna&oh=03_Q7cD1QH02wunlQxeRHctVujCrkAlPdssHSChHbiUDdiDlBSQOg&oe=67755FB9)
### Giao Diện Đăng Kí
![Giao Diện Đăng Kí](https://scontent.fhan15-2.fna.fbcdn.net/v/t1.15752-9/465982919_849214053808427_5118319567516209390_n.png?_nc_cat=111&ccb=1-7&_nc_sid=9f807c&_nc_eui2=AeEO9xsVbiL89ayzPAn75e-tcb4_75eHK6xxvj_vl4crrFDl8dd9pYIoVWJu61_mRzYJv56eKh_HYaTKWS3b0Biy&_nc_ohc=zoh3S3dtWtgQ7kNvgG0ZD6q&_nc_zt=23&_nc_ht=scontent.fhan15-2.fna&oh=03_Q7cD1QF_egeFxFIXCuKkJQQAZBTFk_ExFm3So8NOdqWPB690gg&oe=677553FD)
### Giao Diện Giới Thiệu
![Giao Diện Giới Thiệu](https://scontent.fhan15-1.fna.fbcdn.net/v/t1.15752-9/467475401_1323524095762367_6382347199800594031_n.png?_nc_cat=108&ccb=1-7&_nc_sid=9f807c&_nc_eui2=AeHbWjmStT8fxvicqv_hXBmVBgyxqZITbXAGDLGpkhNtcAmFVJFNKq_NhPbZX730VwuwK98uB4FlWqoZrk2ZjvbV&_nc_ohc=QBhmy1SdlUwQ7kNvgEY4rTM&_nc_zt=23&_nc_ht=scontent.fhan15-1.fna&oh=03_Q7cD1QGmVHyNVGJDocI0qFqvEgZj3u6YvftX6HxN1l-1IMoFlQ&oe=677543E6)
### Giao Diện Tính Điểm Tốt Nghiệp
![Giao Diện Tính Điểm Tốt Nghiệp](https://scontent.fhan15-2.fna.fbcdn.net/v/t1.15752-9/462648338_561493563182971_7365141125245314_n.png?_nc_cat=103&ccb=1-7&_nc_sid=9f807c&_nc_eui2=AeFRzEVMEr_P0Ni8hnsLbs6-cwe5dVZ1vvJzB7l1VnW-8s4dO4Gf2Q2dxI_lBBrmaHc7xl0obb10EEyAz6qIzkxf&_nc_ohc=FN4wFKW4drYQ7kNvgGSU8tq&_nc_zt=23&_nc_ht=scontent.fhan15-2.fna&oh=03_Q7cD1QGW6qLmNkxRbqQOoaOF5XKjeC1rG34xqsmxsWTiomq81w&oe=677554CC)
### Giao Diện Tìm Kiếm Thông Tin
![Giao Diện Tìm Kiếm Thông Tin](https://scontent.fhan15-1.fna.fbcdn.net/v/t1.15752-9/462644682_1527934751198308_7535867247481004679_n.png?_nc_cat=108&ccb=1-7&_nc_sid=9f807c&_nc_eui2=AeEcEwPy7GdX7wt3WNZQvPHL7RSx-9NhqQbtFLH702GpBsEZJyLtKVv_XGlvNHhKWz0uBQe7xyWbjvIj9ir73VfF&_nc_ohc=0KUedfZu1WUQ7kNvgGLaZ6E&_nc_zt=23&_nc_ht=scontent.fhan15-1.fna&oh=03_Q7cD1QEicPfhqRGKxJ6kDKmJ2eC_FYzabk92YuyCilZj1978Yg&oe=67755430)
### Giao Diện Xét Tuyển Đại Học Theo THPT Quốc Gia
![Giao Diện Xét Tuyển Đại Học Theo THPT Quốc Gia](https://scontent.fhan15-2.fna.fbcdn.net/v/t1.15752-9/462575341_1095284422176563_7212396781054843598_n.png?_nc_cat=100&ccb=1-7&_nc_sid=9f807c&_nc_eui2=AeEFwFoTWV_u0BIfuhBay6qhoQROJawG-fOhBE4lrAb580Le-bl1KMYjX_nHjbFBbxJrU1oLn1qZDiMZ8nwu0Pjz&_nc_ohc=SlhPs-cIRloQ7kNvgHVOR-D&_nc_zt=23&_nc_ht=scontent.fhan15-2.fna&oh=03_Q7cD1QE9BV6J2ZPU8zRsw-9eLI68ioqBHS_ANETuOZ_Vljn7mg&oe=67754EDA)
### Giao Diện Xét Tuyển Đại Học Theo Đánh Giá Năng Lực
![Giao Diện Xét Tuyển Đại Học Theo Đánh Giá Năng Lực](https://scontent.fhan15-2.fna.fbcdn.net/v/t1.15752-9/462642699_1693675084524280_7113794683962119994_n.png?_nc_cat=107&ccb=1-7&_nc_sid=9f807c&_nc_eui2=AeFv9tF_j12YKHfmpbMcHhzEMGTMzdx6rBAwZMzN3HqsEK2GlOGjsf818D0T4VaWLkf3Ss6aAHWxVPN14I0xCO0K&_nc_ohc=T9KmIP4Uz3UQ7kNvgHUibeG&_nc_zt=23&_nc_ht=scontent.fhan15-2.fna&oh=03_Q7cD1QGGxQekOWVGAGp3EqYoZ1DPVUR32L2qRyUlOMIhHhNxjQ&oe=677574B9)
### Giao Diện Xét Tuyển Đại Học Theo Đánh Gía Tư Duy
![Giao Diện Xét Tuyển Đại Học Theo Đánh Gía Tư Duy](https://scontent.fhan15-1.fna.fbcdn.net/v/t1.15752-9/462651631_930950065639844_7523245890312735152_n.png?_nc_cat=101&ccb=1-7&_nc_sid=9f807c&_nc_eui2=AeHLrGlqtSP13PgWqFdFut9mPCeALgIKcYE8J4AuAgpxgVXPbidUzGCrCMULemcFhBw5PU3kOOdawP0NhMyp3VMd&_nc_ohc=T0IiFj-S7c8Q7kNvgEOtn8R&_nc_zt=23&_nc_ht=scontent.fhan15-1.fna&oh=03_Q7cD1QG_d7t0otSMDOfPSNABrBQzbslxzqBP6cR-Z_WyvOMnKQ&oe=6775705A)
