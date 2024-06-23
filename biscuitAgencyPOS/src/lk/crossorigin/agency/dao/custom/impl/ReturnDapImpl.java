package lk.crossorigin.agency.dao.custom.impl;

import lk.crossorigin.agency.dao.CrudUtil;
import lk.crossorigin.agency.dao.custom.ReturnStockDAO;
import lk.crossorigin.agency.entity.ReturnStock;
import lk.crossorigin.agency.entity.Shop;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnDapImpl implements ReturnStockDAO {
    @Override
    public boolean saveReturn(ReturnStock s) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO ReturnStock VALUES(?,?,?,?,?)";
        return CrudUtil.executeUpdate(sql,s.getId(),s.getOrderId(),s.getItemCode(),s.getBoxQty(),s.getItemQty());
    }

    @Override
    public boolean updateReturn(ReturnStock s) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE ReturnStock SET orderId=?,itemCode=?,boxQty=?,itemQty=? WHERE id=?";
        return CrudUtil.executeUpdate(sql,s.getOrderId(),s.getItemCode(),s.getBoxQty(),s.getItemQty(),s.getId());
    }

    @Override
    public boolean deleteReturn(String orderId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM ReturnStock WHERE id=?";
        return CrudUtil.executeUpdate(sql,orderId);
    }

    @Override
    public ReturnStock getReturn(String orderId, String itemCode) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM ReturnStock WHERE id=? AND itemCode=?";
        ResultSet rst = CrudUtil.executeQuery(sql,orderId,itemCode);
        if(rst.next()){
            return new ReturnStock(
                    rst.getInt(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getInt(5)
            );
        }
        return null;
    }

    @Override
    public ArrayList<ReturnStock> getReturnByOrderId(String text) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM ReturnStock WHERE id LIKE ? OR orderId LIKE ? OR itemCode LIKE ? OR boxQty LIKE ? OR itemQty LIKE ?";

        ResultSet rst = CrudUtil.executeQuery(sql,text,text,text,text,text);
        ArrayList<ReturnStock> entityList = new ArrayList<>();
        while (rst.next()) {
            ReturnStock returnStock = new ReturnStock(
                    rst.getInt(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getInt(5)
            );
            entityList.add(returnStock);
        }
        return entityList;
    }

    public String getLastOrderIdReturn() throws SQLException, ClassNotFoundException {
        String sql = "SELECT orderId FROM ReturnStock ORDER BY id DESC LIMIT 1";
        ResultSet rst = CrudUtil.executeQuery(sql);

        if (rst.next()) {
            return rst.getString(1);
        } else {
            return "1-1-1";
        }
    }



}
