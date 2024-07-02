package lk.crossorigin.agency.bo.custom;

import lk.crossorigin.agency.dto.OrderBookDTO;
import lk.crossorigin.agency.entity.OrderBook;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBookBO extends SuperBO{
    public boolean saveOrderBook(OrderBookDTO s) throws SQLException, ClassNotFoundException;
    public boolean deleteOrderBook(String id) throws SQLException, ClassNotFoundException;
    public OrderBookDTO getOrderBook(String orderId) throws SQLException, ClassNotFoundException;
    public ArrayList<OrderBookDTO> getAllOrderBooks(String text) throws SQLException, ClassNotFoundException ;
    public String generateOrderId(String bookName,String invNum) throws SQLException, ClassNotFoundException ;
    public String getLastOrderId() throws SQLException, ClassNotFoundException ;
}
