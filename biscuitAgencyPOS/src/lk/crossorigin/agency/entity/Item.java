package lk.crossorigin.agency.entity;

public class Item {

    private String code;
    private String name;
    private double unitPrice_Box_Agency;
    private double unitPrice_Box;
    private int itemCountInBox;
    private int boxQty;
    private int itemQty;

    public Item() {
    }

    public Item(String code, int boxQty, int itemQty) {
        this.code = code;
        this.boxQty = boxQty;
        this.itemQty = itemQty;
    }

    public Item(String code, String name, double unitPrice_Box_Agency, double unitPrice_Box, int itemCountInBox, int boxQty, int itemQty) {
        this.code = code;
        this.name = name;
        this.unitPrice_Box_Agency = unitPrice_Box_Agency;
        this.unitPrice_Box = unitPrice_Box;
        this.itemCountInBox = itemCountInBox;
        this.boxQty = boxQty;
        this.itemQty = itemQty;
    }

    public double getUnitPrice_Box_Agency() {
        return unitPrice_Box_Agency;
    }

    public void setUnitPrice_Box_Agency(double unitPrice_Box_Agency) {
        this.unitPrice_Box_Agency = unitPrice_Box_Agency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice_Box() {
        return unitPrice_Box;
    }

    public void setUnitPrice_Box(double unitPrice_Box) {
        this.unitPrice_Box = unitPrice_Box;
    }

    public int getItemCountInBox() {
        return itemCountInBox;
    }

    public void setItemCountInBox(int itemCountInBox) {
        this.itemCountInBox = itemCountInBox;
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

    @Override
    public String toString() {
        return "Item{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", unitPrice_Box_Agency=" + unitPrice_Box_Agency +
                ", unitPrice_Box=" + unitPrice_Box +
                ", itemCountInBox=" + itemCountInBox +
                ", boxQty=" + boxQty +
                ", itemQty=" + itemQty +
                '}';
    }
}
