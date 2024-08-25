package lk.crossorigin.agency.dao.custom.impl;

import lk.crossorigin.agency.dao.CrudUtil;
import lk.crossorigin.agency.dao.custom.ItemDAO;
import lk.crossorigin.agency.dao.custom.MainItemDAO;
import lk.crossorigin.agency.entity.Item;
import lk.crossorigin.agency.entity.MainItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainItemDaoImpl implements MainItemDAO {
    @Override
    public boolean saveItem(MainItem i) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO MainItem VALUES(?,?,?,?,?,?,?)";
        return CrudUtil.executeUpdate(sql,i.getCode(),i.getName(),i.getUnitPrice_Box_Agency(),i.getUnitPrice_Box(),i.getItemCountInBox(),i.getBoxQty(),i.getItemQty());
    }

    @Override
    public boolean updateItem(MainItem i) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE MainItem SET name=?, unitPrice_Box_Agency=?,unitPrice_Box=?, itemCountInBox=?, boxQty=?, itemQty=? WHERE code=?";
        return CrudUtil.executeUpdate(sql,i.getName(),i.getUnitPrice_Box_Agency(),i.getUnitPrice_Box(),i.getItemCountInBox(),i.getBoxQty(),i.getItemQty(),i.getCode());
    }
    @Override
    public boolean updateItemQtys(MainItem i) throws SQLException, ClassNotFoundException {
        MainItem mainItem = getItem(i.getCode());
        if(i.getBoxQty() == -1){
            int ItemQTY = mainItem.getItemQty() + i.getItemQty();
            String sql = "UPDATE MainItem SET itemQty=?  WHERE code=?";
            return CrudUtil.executeUpdate(sql,ItemQTY,i.getCode());
        } else if (i.getItemQty() == -1) {
            int BoxQTY = mainItem.getBoxQty() + i.getBoxQty();
            String sql = "UPDATE MainItem SET boxQty=?  WHERE code=?";
            return CrudUtil.executeUpdate(sql,BoxQTY,i.getCode());
        }else{
            int ItemQTY = mainItem.getItemQty() + i.getItemQty();
            int BoxQTY = mainItem.getBoxQty() + i.getBoxQty();
            String sql = "UPDATE MainItem SET boxQty=?, itemQty=?  WHERE code=?";
            return CrudUtil.executeUpdate(sql,BoxQTY,ItemQTY,i.getCode());
        }
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM MainItem WHERE code=?";
        return CrudUtil.executeUpdate(sql,code);
    }

    @Override
    public MainItem getItem(String code) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM MainItem WHERE code=?";
        ResultSet rst = CrudUtil.executeQuery(sql,code);
        if(rst.next()){
            return new MainItem(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getInt(6),
                    rst.getInt(7)
            );
        }
        return null;
    }

    @Override
    public MainItem getItemByName(String name) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM MainItem WHERE name = ?";
        ResultSet rst = CrudUtil.executeQuery(sql,name);
        if(rst.next()){
            return new MainItem(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getInt(6),
                    rst.getInt(7)
            );
        }
        return null;
    }

    @Override
    public ArrayList<MainItem> getAllItems(String text) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM MainItem WHERE code LIKE ? OR name LIKE ? OR unitPrice_Box_Agency LIKE ? OR unitPrice_Box LIKE ? OR itemCountInBox LIKE ? OR boxQty LIKE ? OR itemQty LIKE ?";

        ResultSet rst = CrudUtil.executeQuery(sql,text,text,text,text,text,text,text);
        ArrayList<MainItem> entityList = new ArrayList<>();
        while (rst.next()) {
            MainItem mainItem = new MainItem(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getInt(6),
                    rst.getInt(7)
            );
            entityList.add(mainItem);
        }
        return entityList;
    }

}
