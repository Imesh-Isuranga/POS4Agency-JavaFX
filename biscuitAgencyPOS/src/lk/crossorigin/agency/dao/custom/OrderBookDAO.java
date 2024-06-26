package lk.crossorigin.agency.dao.custom;

import lk.crossorigin.agency.entity.Order;
import lk.crossorigin.agency.entity.OrderBook;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public interface OrderBookDAO {
    public boolean saveOrderBook(OrderBook s) throws SQLException, ClassNotFoundException;
    public boolean deleteOrderBook(String orderId) throws SQLException, ClassNotFoundException;
    public OrderBook getOrderBook(String orderId) throws SQLException, ClassNotFoundException;
    public ArrayList<OrderBook> getAllOrderBooks(String text) throws SQLException, ClassNotFoundException;
    public String generateOrderId(String bookName,String invNum) throws SQLException, ClassNotFoundException;
    public String getLastOrderId() throws SQLException, ClassNotFoundException;
}
