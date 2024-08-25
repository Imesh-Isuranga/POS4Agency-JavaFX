package lk.crossorigin.agency.view.tm;

public class ReturnItemsTM {

    private String code;
    private String shopName;
    private int boxQty;
    private int itemQty;
    private double perQTyPrice;

    public ReturnItemsTM() {
    }

    public ReturnItemsTM(String code, String shopName, int boxQty, int itemQty, double perQTyPrice) {
        this.code = code;
        this.shopName = shopName;
        this.boxQty = boxQty;
        this.itemQty = itemQty;
        this.perQTyPrice = perQTyPrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getBoxQty() {
        return boxQty;
    }

    public void setBoxQty(int boxQty) {
        this.boxQty = boxQty;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public double getPerQTyPrice() {
        return perQTyPrice;
    }

    public void setPerQTyPrice(double perQTyPrice) {
        this.perQTyPrice = perQTyPrice;
    }

    @Override
    public String toString() {
        return "ReturnItemsTM{" +
                "code='" + code + '\'' +
                ", shopName='" + shopName + '\'' +
                ", boxQty=" + boxQty +
                ", itemQty=" + itemQty +
                ", perQTyPrice=" + perQTyPrice +
                '}';
    }
}
