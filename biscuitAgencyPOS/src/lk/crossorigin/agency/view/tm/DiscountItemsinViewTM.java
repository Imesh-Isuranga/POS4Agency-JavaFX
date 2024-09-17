package lk.crossorigin.agency.view.tm;

import javafx.scene.control.Button;

public class DiscountItemsinViewTM {
    private String discountItemCode;
    private double percentage;

    public DiscountItemsinViewTM() {
    }

    public DiscountItemsinViewTM(String discountItemCode, double percentage) {
        this.discountItemCode = discountItemCode;
        this.percentage = percentage;
    }

    public String getDiscountItemCode() {
        return discountItemCode;
    }

    public void setDiscountItemCode(String discountItemCode) {
        this.discountItemCode = discountItemCode;
    }


    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "DiscountItemsTM{" +
                "discountItemCode='" + discountItemCode + '\'' +
                ", percentage=" + percentage +
                '}';
    }
}
