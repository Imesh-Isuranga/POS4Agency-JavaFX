package lk.crossorigin.agency.dao.custom.impl;

import lk.crossorigin.agency.dao.CrudUtil;
import lk.crossorigin.agency.dao.custom.ItemDAO;
import lk.crossorigin.agency.db.DBConnection;
import lk.crossorigin.agency.dto.ItemDTO;
import lk.crossorigin.agency.entity.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDaoImpl implements ItemDAO {
    @Override
    public boolean saveItem(Item i) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Item VALUES(?,?,?,?,?,?,?)";
        return CrudUtil.executeUpdate(sql,i.getCode(),i.getName(),i.getUnitPrice_Box_Agency(),i.getUnitPrice_Box(),i.getItemCountInBox(),i.getBoxQty(),i.getItemQty());
    }

    @Override
    public boolean updateItem(Item i) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Item SET name=?, unitPrice_Box_Agency=?,unitPrice_Box=?, itemCountInBox=?, boxQty=?, itemQty=? WHERE code=?";
        return CrudUtil.executeUpdate(sql,i.getName(),i.getUnitPrice_Box_Agency(),i.getUnitPrice_Box(),i.getItemCountInBox(),i.getBoxQty(),i.getItemQty(),i.getCode());
    }
    public boolean updateItemQtys(Item i) throws SQLException, ClassNotFoundException {
        if(i.getBoxQty() == -1){
            String sql = "UPDATE Item SET itemQty=?  WHERE code=?";
            return CrudUtil.executeUpdate(sql,i.getItemQty(),i.getCode());
        } else if (i.getItemQty() == -1) {
            String sql = "UPDATE Item SET boxQty=?  WHERE code=?";
            return CrudUtil.executeUpdate(sql,i.getBoxQty(),i.getCode());
        }else{
            String sql = "UPDATE Item SET boxQty=?, itemQty=?  WHERE code=?";
            return CrudUtil.executeUpdate(sql,i.getBoxQty(),i.getItemQty(),i.getCode());
        }
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Item WHERE code=?";
        return CrudUtil.executeUpdate(sql,code);
    }

    @Override
    public Item getItem(String code) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Item WHERE code=?";
        ResultSet rst = CrudUtil.executeQuery(sql,code);
        if(rst.next()){
            return new Item(
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
    public Item getItemByName(String name) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Item WHERE name = ?";
        ResultSet rst = CrudUtil.executeQuery(sql,name);
        if(rst.next()){
            return new Item(
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
    public ArrayList<Item> getAllItems(String text) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Item WHERE code LIKE ? OR name LIKE ? OR unitPrice_Box_Agency LIKE ? OR unitPrice_Box LIKE ? OR itemCountInBox LIKE ? OR boxQty LIKE ? OR itemQty LIKE ?";

        ResultSet rst = CrudUtil.executeQuery(sql,text,text,text,text,text,text,text);
        ArrayList<Item> entityList = new ArrayList<>();
        while (rst.next()) {
            Item item = new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getInt(6),
                    rst.getInt(7)
            );
            entityList.add(item);
        }
        return entityList;
    }

}
