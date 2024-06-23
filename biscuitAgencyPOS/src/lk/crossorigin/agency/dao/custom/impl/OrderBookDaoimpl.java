package lk.crossorigin.agency.dao.custom.impl;

import lk.crossorigin.agency.dao.CrudUtil;
import lk.crossorigin.agency.dao.custom.OrderBookDAO;
import lk.crossorigin.agency.entity.OrderBook;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderBookDaoimpl implements OrderBookDAO {
    @Override
    public boolean saveOrderBook(OrderBook s) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Orderbook VALUES(?,?,?,?,?)";
        return CrudUtil.executeUpdate(sql,s.getOb_id(),s.getId(),s.getBookId(), s.getInvId(),s.getShopId());
    }

    @Override
    public boolean deleteOrderBook(String orderId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Orderbook WHERE id=?";
        return CrudUtil.executeUpdate(sql,orderId);
    }

    @Override
    public OrderBook getOrderBook(String orderId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Orderbook WHERE id=?";
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

    @Override
    public String generateOrderId(String bookName,String invNum) throws SQLException, ClassNotFoundException {
        String sql = "SELECT ob_id FROM orderbook ORDER BY ob_id DESC LIMIT 1";
        ResultSet rst = CrudUtil.executeQuery(sql);

        if (rst.next()) {
            int autoId = rst.getInt(1);
            int nextId = autoId + 1;
            System.out.println(String.format(nextId+"-"+bookName+"-"+invNum));
            return String.format(nextId+"-"+bookName+"-"+invNum);
        } else {
            return String.format("1"+"-"+bookName+"-"+invNum);
        }
    }

    @Override
    public String getLastOrderId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT id FROM orderbook ORDER BY ob_id DESC LIMIT 1";
        ResultSet rst = CrudUtil.executeQuery(sql);

        if (rst.next()) {
            return rst.getString(1);
        } else {
            return "1-1-1";
        }
    }
}
