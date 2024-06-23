package lk.crossorigin.agency.entity;

public class Payment {
    private int id;
    private String orderId;
    private String payment_Details;
    private String payment_Way;
    private double amount;

    public Payment() {
    }

    public Payment(int id, String ordeId, String payment_Details, String payment_Way, double amount) {
        this.id = id;
        this.orderId = ordeId;
        this.payment_Details = payment_Details;
        this.payment_Way = payment_Way;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayment_Details() {
        return payment_Details;
    }

    public void setPayment_Details(String payment_Details) {
        this.payment_Details = payment_Details;
    }

    public String getPayment_Way() {
        return payment_Way;
    }

    public void setPayment_Way(String payment_Way) {
        this.payment_Way = payment_Way;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", ordeId='" + orderId + '\'' +
                ", payment_Details='" + payment_Details + '\'' +
                ", payment_Way='" + payment_Way + '\'' +
                ", amount=" + amount +
                '}';
    }
}
