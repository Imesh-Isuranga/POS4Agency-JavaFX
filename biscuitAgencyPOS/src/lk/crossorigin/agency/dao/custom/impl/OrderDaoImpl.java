package lk.crossorigin.agency.dao.custom.impl;

import lk.crossorigin.agency.dao.CrudUtil;
import lk.crossorigin.agency.dao.custom.OrderDAO;
import lk.crossorigin.agency.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class OrderDaoImpl implements OrderDAO {
    @Override
    public boolean saveOrder(Order s) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Orders VALUES(?,?,?,?)";
        return CrudUtil.executeUpdate(sql,s.getAuto_id(), s.getOrderId(),s.getDate(),s.getShopId());
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
        String sql = "SELECT orderId FROM Orders ORDER BY auto_id DESC LIMIT 1";
        ResultSet rst = CrudUtil.executeQuery(sql);

        if (rst.next()) {
            return rst.getString(1);
        } else {
            return "1-1-1";
        }
    }




}