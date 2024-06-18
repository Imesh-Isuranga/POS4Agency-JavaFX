package lk.crossorigin.agency.view.tm;

import javafx.scene.control.Button;

public class ShopTM {
    String id;
    String name;
    String address;
    Button btn;


    public ShopTM() {
    }

    public ShopTM(String id, String name, String address, Button btn) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.btn = btn;
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

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "ShopTM{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", btn=" + btn +
                '}';
    }
}
