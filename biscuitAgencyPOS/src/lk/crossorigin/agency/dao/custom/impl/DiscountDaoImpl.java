package lk.crossorigin.agency.dao.custom.impl;

import lk.crossorigin.agency.dao.CrudUtil;
import lk.crossorigin.agency.dao.custom.DiscountDAO;
import lk.crossorigin.agency.entity.Discount;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DiscountDaoImpl implements DiscountDAO {
    @Override
    public boolean saveDiscount(Discount d) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Discount VALUES(?,?,?,?,?)";
        return CrudUtil.executeUpdate(sql,d.getId(), d.getIdDup(),d.getOrderId(),d.getItemCode(),d.getDiscountValue());
    }

    @Override
    public boolean updateDiscount(Discount d) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Discount SET idDup=?, orderId=?, itemCode=?, discount_percent=?";
        return CrudUtil.executeUpdate(sql,d.getIdDup(),d.getOrderId(),d.getItemCode(),d.getDiscountValue());
    }

    @Override
    public boolean deleteDiscount(String orderId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Discount WHERE orderId=?";
        return CrudUtil.executeUpdate(sql,orderId);
    }

    @Override
    public Discount getItemByOrderId(String disId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Discount WHERE orderId=?";
        ResultSet rst = CrudUtil.executeQuery(sql,disId);
        if(rst.next()){
            return new Discount(
                    rst.getInt(1),
                    rst.getInt(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5)
            );
        }
        return null;
    }

    @Override
    public ArrayList<Discount> getAllDiscountByIdDup(String orderId, String orderIdDup) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Discount WHERE orderId=? && idDup=?";

        ResultSet rst = CrudUtil.executeQuery(sql,orderId,orderIdDup);
        ArrayList<Discount> entityList = new ArrayList<>();
        while (rst.next()) {
            Discount discount = new Discount(
                    rst.getInt(1),
                    rst.getInt(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5)
            );
            entityList.add(discount);
        }
        return entityList;
    }

    @Override
    public ArrayList<Discount> getAllDiscountByOrderId(String orderId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Discount WHERE orderId=?";

        ResultSet rst = CrudUtil.executeQuery(sql,orderId);
        ArrayList<Discount> entityList = new ArrayList<>();
        while (rst.next()) {
            Discount discount = new Discount(
                    rst.getInt(1),
                    rst.getInt(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5)
            );
            entityList.add(discount);
        }
        return entityList;
    }

    @Override
    public ArrayList<Discount> getAllDiscount(String text) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Discount WHERE id LIKE ? OR idDup LIKE ? OR orderId LIKE ? OR itemCode LIKE ? OR discount_percent LIKE ?";

        ResultSet rst = CrudUtil.executeQuery(sql,text,text,text,text,text);
        ArrayList<Discount> entityList = new ArrayList<>();
        while (rst.next()) {
            Discount discount = new Discount(
                    rst.getInt(1),
                    rst.getInt(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5)
            );
            entityList.add(discount);
        }
        return entityList;
    }

    @Override
    public String getLastDiscountId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT orderId FROM Discount ORDER BY id DESC LIMIT 1";
        ResultSet rst = CrudUtil.executeQuery(sql);

        if (rst.next()) {
            return rst.getString(1);
        } else {
            return "1-1-1";
        }
    }
}
