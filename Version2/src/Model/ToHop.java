package Version2.src.Model;
import java.util.List;
import java.util.Map;

public class ToHop {
    private String tenToHop;
    private List<String> monThuocTH;
    private Map<String, Double> trongSo;

    public ToHop(String tenToHop, List<String> monThuocTH, Map<String, Double> trongSo) {
        this.tenToHop = tenToHop;
        this.monThuocTH = monThuocTH;
        this.trongSo = (trongSo != null) ? trongSo : getTrongSoMacDinh();
    }

    public void setMonThuocTH(List<String> monThuocTH) {
        this.monThuocTH = monThuocTH;
    }

    public void setTrongSo(Map<String, Double> trongSo) {
        this.trongSo = trongSo;
    }

    public void setTenToHop(String tenToHop) {
        this.tenToHop = tenToHop;
    }

    private Map<String, Double> getTrongSoMacDinh() {
        return monThuocTH.stream().collect(java.util.stream.Collectors.toMap(mon -> mon, mon -> 1.0));
    }

    public String getTenToHop() {
        return tenToHop;
    }

    public List<String> getMonThuocTH() {
        return monThuocTH;
    }

    public Map<String, Double> getTrongSo() {
        return trongSo;
    }
}