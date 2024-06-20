package lk.crossorigin.agency.dao.custom.impl;

import lk.crossorigin.agency.dao.CrudUtil;
import lk.crossorigin.agency.dao.custom.ShopDAO;
import lk.crossorigin.agency.db.DBConnection;
import lk.crossorigin.agency.dto.ShopDTO;
import lk.crossorigin.agency.entity.Item;
import lk.crossorigin.agency.entity.Shop;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShopDaoImpl implements ShopDAO {
    //Shop Management
    @Override
    public boolean saveShop(Shop s) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Shop VALUES(?,?,?)";
        return CrudUtil.executeUpdate(sql,s.getId(),s.getName(),s.getAddress());
    }

    @Override
    public boolean updateShop(Shop s) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Shop SET name=?,address=? WHERE id=?";
        return CrudUtil.executeUpdate(sql,s.getName(),s.getAddress(),s.getId());
    }

    @Override
    public boolean deleteShop(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Shop WHERE id=?";
        return CrudUtil.executeUpdate(sql,id);
    }

    public Shop getShop(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Shop WHERE id=?";
        ResultSet rst = CrudUtil.executeQuery(sql,id);
        if(rst.next()){
            return new Shop(rst.getString(1),rst.getString(2),rst.getString(3));
        }
        return null;
    }

    @Override
    public ArrayList<Shop> getAllShops(String text) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Shop WHERE id LIKE ? OR name LIKE ? OR address LIKE ?";

        ResultSet rst = CrudUtil.executeQuery(sql,text,text,text);
        ArrayList<Shop> entityList = new ArrayList<>();
        while (rst.next()) {
            Shop shop = new Shop(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
            entityList.add(shop);
        }
        return entityList;
    }

}
