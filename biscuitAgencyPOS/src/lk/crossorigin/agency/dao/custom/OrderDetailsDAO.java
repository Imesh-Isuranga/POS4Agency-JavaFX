package lk.crossorigin.agency.dao.custom;


import lk.crossorigin.agency.entity.Order;
import lk.crossorigin.agency.entity.OrderDetail;
import lk.crossorigin.agency.entity.Shop;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public interface OrderDetailsDAO {
    public boolean saveOrderDetails(OrderDetail s) throws SQLException, ClassNotFoundException;
    public boolean deleteOrderDelete(String orderId) throws SQLException, ClassNotFoundException;
    public OrderDetail getOrderDetail(String orderId,String itemCode) throws SQLException, ClassNotFoundException;
    public ArrayList<OrderDetail> getOrderDetailsByDate(Date date) throws SQLException, ClassNotFoundException;
    public ArrayList<OrderDetail> getAllOrderDetailsByOrderId(String orderId) throws SQLException, ClassNotFoundException;
    public ArrayList<OrderDetail> getAllOrderDetails(String text) throws SQLException, ClassNotFoundException;
}