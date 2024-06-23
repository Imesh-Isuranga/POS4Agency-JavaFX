package lk.crossorigin.agency.dao.custom.impl;

import lk.crossorigin.agency.dao.CrudUtil;
import lk.crossorigin.agency.dao.custom.OrderBookDAO;
import lk.crossorigin.agency.entity.Discount;
import lk.crossorigin.agency.entity.OrderBook;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderBookDaoimpl implements OrderBookDAO {
    @Override
    public boolean saveOrderBook(OrderBook s) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Orderbook VALUES(?,?,?,?,?)";
        return CrudUtil.executeUpdate(sql,s.getId(),s.getBookId(), s.getInvId(),s.getOrderId(),s.getShopId());
    }

    @Override
    public boolean deleteOrderBook(String orderId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Orderbook WHERE orderId=?";
        return CrudUtil.executeUpdate(sql,orderId);
    }

    @Override
    public OrderBook getOrderBook(String orderId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Orderbook WHERE orderId=?";
        ResultSet rst = CrudUtil.executeQuery(sql,orderId);
        if(rst.next()){
            return new OrderBook(
                    rst.getInt(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }
}
