package Version2.src.Model;

public class ToHopMon {
    private String maToHop;
    private String mon1;
    private String mon2;
    private String mon3;

    // Constructor
    public ToHopMon(String maToHop, String mon1, String mon2, String mon3) {
        this.maToHop = maToHop;
        this.mon1 = mon1;
        this.mon2 = mon2;
        this.mon3 = mon3;
    }

    // Getters
    public String getMaToHop() {
        return maToHop;
    }

    public String getMon1() {
        return mon1;
    }

    public String getMon2() {
        return mon2;
    }

    public String getMon3() {
        return mon3;
    }

}