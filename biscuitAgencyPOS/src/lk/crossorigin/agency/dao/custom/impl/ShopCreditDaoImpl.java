package lk.crossorigin.agency.dao.custom.impl;

import lk.crossorigin.agency.dao.CrudUtil;
import lk.crossorigin.agency.dao.custom.ShopCreditDAO;
import lk.crossorigin.agency.entity.Shop;
import lk.crossorigin.agency.entity.ShopCredit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class ShopCreditDaoImpl implements ShopCreditDAO {
    @Override
    public boolean saveCredit(ShopCredit s) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO ShopsCredit VALUES(?,?,?,?,?)";
        return CrudUtil.executeUpdate(sql,s.getId(),s.getShopId(),s.getDate_paid(),s.getAmount(),s.getPaymentDetails());
    }

    @Override
    public boolean deleteCreditById(int id) throws ClassNotFoundException, SQLException {
        String sql = "DELETE FROM ShopsCredit WHERE id=?";
        return CrudUtil.executeUpdate(sql,id);
    }

    @Override
    public boolean deleteCreditByshop(String shopId) throws ClassNotFoundException, SQLException {
        String sql = "DELETE FROM ShopsCredit WHERE shopId=?";
        return CrudUtil.executeUpdate(sql,shopId);
    }

    @Override
    public boolean updateCredit(ShopCredit s) throws ClassNotFoundException, SQLException {
        String sql = "UPDATE ShopsCredit SET shopId=?,date_paid=?,amount=?,paymentDetails=? WHERE id=?";
        return CrudUtil.executeUpdate(sql,s.getShopId(),s.getDate_paid(),s.getAmount(),s.getPaymentDetails(),s.getId());
    }

    @Override
    public ArrayList<ShopCredit> getItemByShopId(String shopId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM ShopsCredit WHERE shopId LIKE ?";

        ResultSet rst = CrudUtil.executeQuery(sql,shopId);
        ArrayList<ShopCredit> entityList = new ArrayList<>();
        while (rst.next()) {
            ShopCredit shopCredit = new ShopCredit(
                    rst.getInt(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getDouble(4),
                    rst.getString(5)
            );
            entityList.add(shopCredit);
        }
        return entityList;
    }

    @Override
    public ArrayList<ShopCredit> getItemByAmount(double amount) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM ShopsCredit WHERE amount LIKE ?";

        ResultSet rst = CrudUtil.executeQuery(sql,amount);
        ArrayList<ShopCredit> entityList = new ArrayList<>();
        while (rst.next()) {
            ShopCredit shopCredit = new ShopCredit(
                    rst.getInt(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getDouble(4),
                    rst.getString(5)
            );
            entityList.add(shopCredit);
        }
        return entityList;
    }

    @Override
    public ArrayList<ShopCredit> getItemByDate(Date date) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM ShopsCredit WHERE date_paid LIKE ?";

        ResultSet rst = CrudUtil.executeQuery(sql,date);
        ArrayList<ShopCredit> entityList = new ArrayList<>();
        while (rst.next()) {
            ShopCredit shopCredit = new ShopCredit(
                    rst.getInt(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getDouble(4),
                    rst.getString(5)
            );
            entityList.add(shopCredit);
        }
        return entityList;
    }

    @Override
    public ArrayList<ShopCredit> getAllCreditsShops(String text) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM ShopsCredit WHERE shopId LIKE ? OR date_paid LIKE ? OR amount LIKE ? OR paymentDetails LIKE ?";

        ResultSet rst = CrudUtil.executeQuery(sql,text,text,text,text);
        ArrayList<ShopCredit> entityList = new ArrayList<>();
        while (rst.next()) {
            ShopCredit shopCredit = new ShopCredit(
                    rst.getInt(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getDouble(4),
                    rst.getString(5)
            );
            entityList.add(shopCredit);
        }
        return entityList;
    }
}
