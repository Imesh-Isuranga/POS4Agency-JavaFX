package lk.crossorigin.agency.dto;

public class ShopDTO {
    private String id;
    private String name;
    private String address;
    private double credit_uptoNow;

    public ShopDTO() {
    }

    public ShopDTO(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public ShopDTO(String id, String name, String address, double credit_uptoNow) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.credit_uptoNow = credit_uptoNow;
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
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", credit_uptoNow=" + credit_uptoNow +
                '}';
    }
}
