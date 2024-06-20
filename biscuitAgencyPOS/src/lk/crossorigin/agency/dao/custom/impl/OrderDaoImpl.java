package lk.crossorigin.agency.dao.custom.impl;

import lk.crossorigin.agency.dao.CrudUtil;
import lk.crossorigin.agency.dao.custom.OrderDAO;
import lk.crossorigin.agency.entity.Item;
import lk.crossorigin.agency.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class OrderDaoImpl implements OrderDAO {
    @Override
    public boolean saveOrder(Order s) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Orders VALUES(?,?)";
        return CrudUtil.executeUpdate(sql,s.getId(),s.getDate());
    }

    @Override
    public boolean deleteOrder(String orderId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Orders WHERE id=?";
        return CrudUtil.executeUpdate(sql,orderId);
    }

    @Override
    public Date getOrderDate(String orderId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT date FROM Orders WHERE id=?";
        ResultSet rst = CrudUtil.executeQuery(sql,orderId);
        return rst.next() ? rst.getDate("date") : null;
    }

    @Override
    public String getLastOrderId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT id FROM Orders ORDER BY id DESC LIMIT 1";
        ResultSet rst = CrudUtil.executeQuery(sql);

        if(rst.next()){
            String orderID = rst.getString(1);
            orderID = orderID.split("[A-Z]")[1];
            orderID = Integer.parseInt(orderID)+1+"";
            return "D" + orderID;
        }else{
            return "D001";
        }
    }

}
