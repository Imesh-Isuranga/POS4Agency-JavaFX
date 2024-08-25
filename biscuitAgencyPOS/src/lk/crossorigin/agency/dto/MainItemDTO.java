package lk.crossorigin.agency.dto;

public class MainItemDTO {
    private String code;
    private String name;
    private double unitPrice_Box_Agency;
    private double unitPrice_Box;
    private int boxQty;
    private int itemCountInBox;
    private int itemQty;

    public MainItemDTO() {
    }

    public MainItemDTO(String code, int boxQty, int itemQty) {
        this.code = code;
        this.boxQty = boxQty;
        this.itemQty = itemQty;
    }
    public MainItemDTO(String code, String name, double unitPrice_Box_Agency, double unitPrice_Box, int itemCountInBox, int boxQty, int itemQty) {
        this.code = code;
        this.name = name;
        this.unitPrice_Box_Agency = unitPrice_Box_Agency;
        this.unitPrice_Box = unitPrice_Box;
        this.boxQty = boxQty;
        this.itemCountInBox = itemCountInBox;
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

    public int getBoxQty() {
        return boxQty;
    }

    public void setBoxQty(int boxQty) {
        this.boxQty = boxQty;
    }

    public int getItemCountInBox() {
        return itemCountInBox;
    }

    public void setItemCountInBox(int itemCountInBox) {
        this.itemCountInBox = itemCountInBox;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", unitPrice_Box_Agency=" + unitPrice_Box_Agency +
                ", unitPrice_Box=" + unitPrice_Box +
                ", boxQty=" + boxQty +
                ", itemCountInBox=" + itemCountInBox +
                ", itemQty=" + itemQty +
                '}';
    }
}
