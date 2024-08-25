package lk.crossorigin.agency.dao.custom.impl;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import lk.crossorigin.agency.dao.CrudUtil;
import lk.crossorigin.agency.dao.custom.OrderDAO;
import lk.crossorigin.agency.dto.OrderDTO;
import lk.crossorigin.agency.dto.OrderDetailsDTO;
import lk.crossorigin.agency.entity.Item;
import lk.crossorigin.agency.entity.Order;
import lk.crossorigin.agency.entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    @Override
    public Order getOrderByOrderId(String orderId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Orders WHERE orderId=?";
        ResultSet rst = CrudUtil.executeQuery(sql,orderId);
        if(rst.next()){
            return new Order(
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getString(4)
            );
        }
        return null;
    }

    @Override
    public ArrayList<Order> getAllOrders(String text) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Orders WHERE date LIKE ?";

        ResultSet rst = CrudUtil.executeQuery(sql,text);
        ArrayList<Order> entityList = new ArrayList<>();
        while (rst.next()) {
            Order order = new Order(
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getString(4)
            );
            entityList.add(order);
        }
        return entityList;
    }


}