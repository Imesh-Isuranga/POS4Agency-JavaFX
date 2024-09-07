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
        String sql = "INSERT INTO Shop VALUES(?,?,?,?,?)";
        return CrudUtil.executeUpdate(sql,s.getSh_id(),s.getId(),s.getName(),s.getAddress(),s.getCredit_uptoNow());
    }

    @Override
    public boolean updateShop(Shop s) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Shop SET id=?,name=?,address=?,credit_uptoNow=? WHERE sh_id=?";
        return CrudUtil.executeUpdate(sql,s.getId(),s.getName(),s.getAddress(),s.getCredit_uptoNow(),s.getSh_id());
    }
    @Override
    public boolean updateShopCredit(String id,double creditAmount) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Shop SET credit_uptoNow=? WHERE id=?";
        return CrudUtil.executeUpdate(sql,creditAmount,id);
    }


    @Override
    public boolean updateShopWithoutCredit(Shop s) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Shop SET id=?,name=?,address=? WHERE sh_id=?";
        System.out.println("1111122222222333");
        System.out.println(s.getId());
        System.out.println(s.getName());
        System.out.println(s.getAddress());
        System.out.println(s.getSh_id());
        System.out.println(CrudUtil.executeUpdate(sql,s.getId(),s.getName(),s.getAddress(),s.getSh_id()));
        return CrudUtil.executeUpdate(sql,s.getId(),s.getName(),s.getAddress(),s.getSh_id());
    }

    @Override
    public boolean deleteShop(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Shop WHERE sh_id=?";
        return CrudUtil.executeUpdate(sql,id);
    }

    public Shop getShop(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Shop WHERE sh_id=?";
        ResultSet rst = CrudUtil.executeQuery(sql,id);
        if(rst.next()){
            return new Shop(rst.getInt(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getDouble(5));
        }
        return null;
    }

    @Override
    public ArrayList<Shop> getAllShops(String text) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Shop WHERE sh_id LIKE ? OR id LIKE ? OR name LIKE ? OR address LIKE ? OR credit_uptoNow LIKE ?";

        ResultSet rst = CrudUtil.executeQuery(sql,text,text,text,text,text);
        ArrayList<Shop> entityList = new ArrayList<>();
        while (rst.next()) {
            Shop shop = new Shop(
                    rst.getInt(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5)
            );
            entityList.add(shop);
        }
        return entityList;
    }

    @Override
    public String generateShopId(String name,String address) throws SQLException, ClassNotFoundException {
        String sql = "SELECT sh_id FROM Shop ORDER BY sh_id DESC LIMIT 1";
        ResultSet rst = CrudUtil.executeQuery(sql);
        String firstThreeOfA = address.substring(0, 3);
        if (rst.next()) {
            System.out.println(rst.getInt(1));
            int autoId = rst.getInt(1);
            int nextId = autoId + 1;
            System.out.println("sssssssssssssssssssssssssssssssss11111");
            System.out.println(nextId);
            System.out.println(nextId + " "+firstThreeOfA +"-" +name);
            return nextId + " "+firstThreeOfA +"-" +name;
        } else {
            System.out.println("ssssssssssssssssssssssssssssssssssss");
            System.out.println("1" + " "+firstThreeOfA +"-" +name);
            return "1" + " "+firstThreeOfA +"-" +name;
        }
    }

}
