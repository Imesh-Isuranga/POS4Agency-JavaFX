package lk.crossorigin.agency.dao.custom;


import lk.crossorigin.agency.entity.Order;
import lk.crossorigin.agency.entity.OrderDetail;
import lk.crossorigin.agency.entity.Shop;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailsDAO {
    public boolean saveOrderDetails(OrderDetail s) throws SQLException, ClassNotFoundException;

    public ArrayList<OrderDetail> getAllOrderDetails(String text) throws SQLException, ClassNotFoundException;
}
