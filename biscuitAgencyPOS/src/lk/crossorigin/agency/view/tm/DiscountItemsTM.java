package lk.crossorigin.agency.view.tm;

import javafx.scene.control.Button;

public class DiscountItemsTM {
    private String discountItemCode;
    private Button removeBtn;

    public DiscountItemsTM() {
    }

    public DiscountItemsTM(String discountItemCode, Button removeBtn) {
        this.discountItemCode = discountItemCode;
        this.removeBtn = removeBtn;
    }

    public String getDiscountItemCode() {
        return discountItemCode;
    }

    public void setDiscountItemCode(String discountItemCode) {
        this.discountItemCode = discountItemCode;
    }

    public Button getRemoveBtn() {
        return removeBtn;
    }

    public void setRemoveBtn(Button removeBtn) {
        this.removeBtn = removeBtn;
    }

    @Override
    public String toString() {
        return "DiscountItemsTM{" +
                "discountItemCode='" + discountItemCode + '\'' +
                ", removeBtn=" + removeBtn +
                '}';
    }
}
