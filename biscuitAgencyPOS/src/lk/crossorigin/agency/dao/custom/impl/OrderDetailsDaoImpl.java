package lk.crossorigin.agency.dao.custom.impl;

import lk.crossorigin.agency.dao.CrudUtil;
import lk.crossorigin.agency.dao.custom.OrderDetailsDAO;
import lk.crossorigin.agency.entity.Item;
import lk.crossorigin.agency.entity.OrderDetail;
import lk.crossorigin.agency.entity.Shop;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDaoImpl implements OrderDetailsDAO {

    @Override
    public boolean saveOrderDetails(OrderDetail s) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO OrderDetail VALUES(?,?,?,?,?,?,?)";
        return CrudUtil.executeUpdate(sql,s.getOrderId(),s.getItemCode(),s.getUnitPrice_Box(),s.getBoxQty(),s.getItemQty(),s.getBoxQtyFree(),s.getItemQtyFree());
    }

    @Override
    public boolean deleteOrderDelete(String orderId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Orderdetail WHERE orderId=?";
        return CrudUtil.executeUpdate(sql,orderId);
    }

    @Override
    public OrderDetail getOrderDetail(String orderId,String itemCode) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Orderdetail WHERE orderId = ? && itemCode = ?";
        System.out.println("====================================");
        ResultSet rst = CrudUtil.executeQuery(sql,orderId,itemCode);
        System.out.println(rst);
        if(rst.next()){
            return new OrderDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4),
                    rst.getInt(5),
                    rst.getInt(6),
                    rst.getInt(7)
            );
        }
        return null;
    }

    @Override
    public ArrayList<OrderDetail> getAllOrderDetails(String text) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM OrderDetail WHERE orderId LIKE ? OR itemCode LIKE ? OR unitPrice_Box LIKE ? OR boxQty LIKE ? OR itemQty LIKE ? OR boxQtyFree LIKE ? OR itemQtyFree LIKE ?";

        ResultSet rst = CrudUtil.executeQuery(sql,text,text,text,text,text);
        ArrayList<OrderDetail> entityList = new ArrayList<>();
        while (rst.next()) {
            OrderDetail orderDetail = new OrderDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4),
                    rst.getInt(5),
                    rst.getInt(6),
                    rst.getInt(7)
            );
            entityList.add(orderDetail);
        }
        return entityList;
    }

}
