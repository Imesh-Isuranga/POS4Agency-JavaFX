package lk.crossorigin.agency.dao.custom;

import lk.crossorigin.agency.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public interface OrderDAO {
    public boolean saveOrder(Order s) throws SQLException, ClassNotFoundException;
    public boolean deleteOrder(String id) throws SQLException, ClassNotFoundException;
    public Date getOrderDate(String orderId) throws SQLException, ClassNotFoundException;

    public String getLastOrderId() throws SQLException, ClassNotFoundException ;
}