package lk.crossorigin.agency.view.tm;

import javafx.scene.control.Button;

public class SaveListTM {

    private int no;
    private String listItem;
    private Button btn;

    public SaveListTM() {
    }

    public SaveListTM(int no, String listItem, Button btn) {
        this.no = no;
        this.listItem = listItem;
        this.btn = btn;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getListItem() {
        return listItem;
    }

    public void setListItem(String listItem) {
        this.listItem = listItem;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "SaveListTM{" +
                "no=" + no +
                ", listItem='" + listItem + '\'' +
                ", btn=" + btn +
                '}';
    }
}
