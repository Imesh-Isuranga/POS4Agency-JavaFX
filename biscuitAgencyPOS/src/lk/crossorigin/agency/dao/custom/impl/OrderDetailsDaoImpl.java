package lk.crossorigin.agency.dao.custom.impl;

import lk.crossorigin.agency.dao.CrudUtil;
import lk.crossorigin.agency.dao.custom.OrderDetailsDAO;
import lk.crossorigin.agency.entity.OrderDetail;
import lk.crossorigin.agency.entity.Shop;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDaoImpl implements OrderDetailsDAO {

    @Override
    public boolean saveOrderDetails(OrderDetail s) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO OrderDetail VALUES(?,?,?,?,?)";
        return CrudUtil.executeUpdate(sql,s.getOrderId(),s.getShopId(),s.getItemCode(),s.getQty(),s.getUnitPrice());
    }

    @Override
    public ArrayList<OrderDetail> getAllOrderDetails(String text) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM OrderDetail WHERE orderId LIKE ? OR shopId LIKE ? OR itemCode LIKE ? OR qty LIKE ? OR unitPrice LIKE ?";

        ResultSet rst = CrudUtil.executeQuery(sql,text,text,text,text,text);
        ArrayList<OrderDetail> entityList = new ArrayList<>();
        while (rst.next()) {
            OrderDetail orderDetail = new OrderDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getDouble(5)
            );
            entityList.add(orderDetail);
        }
        return entityList;
    }

}
