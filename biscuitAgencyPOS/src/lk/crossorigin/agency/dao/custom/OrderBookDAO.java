package lk.crossorigin.agency.dao.custom;

import lk.crossorigin.agency.entity.Order;
import lk.crossorigin.agency.entity.OrderBook;

import java.sql.SQLException;
import java.util.Date;

public interface OrderBookDAO {
    public boolean saveOrderBook(OrderBook s) throws SQLException, ClassNotFoundException;
    public boolean deleteOrderBook(String id) throws SQLException, ClassNotFoundException;
    public OrderBook getOrderBook(String orderId) throws SQLException, ClassNotFoundException;

}
