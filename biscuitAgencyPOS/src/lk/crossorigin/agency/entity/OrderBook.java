package lk.crossorigin.agency.entity;

public class OrderBook {
    private int ob_id;
    private String id;
    private String bookId;
    private String InvId;
    private String shopId;

    public OrderBook() {
    }

    public OrderBook(String id, String bookId, String invId, String shopId) {
        this.id = id;
        this.bookId = bookId;
        InvId = invId;
        this.shopId = shopId;
    }

    public OrderBook(int ob_id, String id, String bookId, String invId, String shopId) {
        this.ob_id = ob_id;
        this.id = id;
        this.bookId = bookId;
        InvId = invId;
        this.shopId = shopId;
    }

    public int getOb_id() {
        return ob_id;
    }

    public void setOb_id(int ob_id) {
        this.ob_id = ob_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getInvId() {
        return InvId;
    }

    public void setInvId(String invId) {
        InvId = invId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "OrderBook{" +
                "ob_id=" + ob_id +
                ", id='" + id + '\'' +
                ", bookId='" + bookId + '\'' +
                ", InvId='" + InvId + '\'' +
                ", shopId='" + shopId + '\'' +
                '}';
    }
}
