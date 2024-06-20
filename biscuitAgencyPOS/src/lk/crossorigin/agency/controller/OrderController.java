package lk.crossorigin.agency.controller;

import lk.crossorigin.agency.db.DBConnection;
import lk.crossorigin.agency.entity.Order;
import lk.crossorigin.agency.entity.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderController {
    public static boolean addOrder(Order order) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        boolean isOrderAdded = false;

        connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        //1. Insert a record into Orders (orderId, date)
        PreparedStatement stm = connection.prepareStatement("INSERT into Orders values(?,?)");
        stm.setObject(1, order.getId());
        stm.setObject(2, order.getDate());
        isOrderAdded = stm.executeUpdate() > 0;

        if (isOrderAdded) {
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                PreparedStatement stm1 = connection.prepareStatement("INSERT into OrderDetail values(?,?,?,?,?)");
                stm1.setObject(1, orderDetail.getOrderId());
                stm1.setObject(2, orderDetail.getShopId());
                stm1.setObject(3, orderDetail.getItemCode());
                stm1.setObject(4, orderDetail.getQty());
                stm1.setObject(5, orderDetail.getUnitPrice());
                boolean isOrderDetailAdded = stm1.executeUpdate() > 0;

                if (!isOrderDetailAdded) {
                    connection.rollback();
                    return false;
                }
            }

            // Commit the transaction
            connection.commit();
            return true;
        } else {
            connection.rollback();
            return false;
        }
    }
}
