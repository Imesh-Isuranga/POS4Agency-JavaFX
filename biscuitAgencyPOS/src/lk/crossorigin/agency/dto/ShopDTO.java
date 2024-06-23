package lk.crossorigin.agency.dto;

public class ShopDTO {
    private int sh_id;
    private String id;
    private String name;
    private String address;
    private double credit_uptoNow;

    public ShopDTO() {
    }


    public ShopDTO(int sh_id, String id, String name, String address) {
        this.sh_id = sh_id;
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public ShopDTO(int sh_id, String id, String name, String address, double credit_uptoNow) {
        this.sh_id = sh_id;
        this.id = id;
        this.name = name;
        this.address = address;
        this.credit_uptoNow = credit_uptoNow;
    }

    public int getSh_id() {
        return sh_id;
    }

    public void setSh_id(int sh_id) {
        this.sh_id = sh_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getCredit_uptoNow() {
        return credit_uptoNow;
    }

    public void setCredit_uptoNow(double credit_uptoNow) {
        this.credit_uptoNow = credit_uptoNow;
    }

    @Override
    public String toString() {
        return "ShopDTO{" +
                "sh_id=" + sh_id +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", credit_uptoNow=" + credit_uptoNow +
                '}';
    }
}
