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
    public boolean saveItem(Item s) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Item VALUES(?,?,?,?)";
        return CrudUtil.executeUpdate(sql,s.getCode(),s.getName(),s.getUnitPrice(),s.getQty());
    }

    @Override
    public boolean updateItem(Item i) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Item SET qty=? WHERE code=?";
        return CrudUtil.executeUpdate(sql,i.getQty(),i.getCode());
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
            return new Item(rst.getString(1),rst.getString(2),rst.getDouble(3),rst.getInt(4));
        }
        return null;
    }

    @Override
    public Item getItemByName(String name) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Item WHERE name = ?";
        ResultSet rst = CrudUtil.executeQuery(sql,name);
        if(rst.next()){
            return new Item(rst.getString(1),rst.getString(2),rst.getDouble(3),rst.getInt(4));
        }
        return null;
    }

    @Override
    public ArrayList<Item> getAllItems(String text) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Item WHERE code LIKE ? OR name LIKE ? OR unitPrice LIKE ? OR qty LIKE ?";

        ResultSet rst = CrudUtil.executeQuery(sql,text,text,text,text);
        ArrayList<Item> entityList = new ArrayList<>();
        while (rst.next()) {
            Item item = new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4)
            );
            entityList.add(item);
        }
        return entityList;
    }

}
