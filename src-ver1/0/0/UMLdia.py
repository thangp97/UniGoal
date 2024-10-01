from diagrams import Diagram, Cluster
from diagrams.programming.language import Java

with Diagram("UML Class Diagram", show=False):
    with Cluster("ThiSinh Class"):
        thiSinh = Java("ThiSinh")
        ketQua = Java("KetQua")

    with Cluster("TinhDiemCong Class"):
        tinhDiemCong = Java("TinhDiemCong")

    with Cluster("ToHop Class"):
        toHop = Java("ToHop")

    with Cluster("TinhKetQua Class"):
        tinhKetQua = Java("TinhKetQua")

    with Cluster("DaiHoc Class"):
        daiHoc = Java("DaiHoc")

    with Cluster("ChuyenNganh Class"):
        chuyenNganh = Java("ChuyenNganh")

    with Cluster("DeXuat Class"):
        deXuat = Java("DeXuat")

    thiSinh >> ketQua
    tinhKetQua >> thiSinh
    tinhKetQua >> toHop
    tinhDiemCong >> thiSinh
    daiHoc >> chuyenNganh
    deXuat >> thiSinh
    deXuat >> daiHoc
