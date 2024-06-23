package lk.crossorigin.agency.dao.custom;

import lk.crossorigin.agency.dto.ShopDTO;
import lk.crossorigin.agency.entity.Shop;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ShopDAO {
    public boolean saveShop(Shop s) throws SQLException, ClassNotFoundException;
    public boolean updateShop(Shop s) throws SQLException, ClassNotFoundException;
    public boolean updateShopCredit(String id,double creditAmount) throws SQLException, ClassNotFoundException;
    public boolean updateShopWithoutCredit(Shop s) throws SQLException, ClassNotFoundException;
    public boolean deleteShop(String id) throws SQLException, ClassNotFoundException;
    public Shop getShop(String id) throws SQLException, ClassNotFoundException;
    public ArrayList<Shop> getAllShops(String text) throws SQLException, ClassNotFoundException;
    public String generateShopId(String name,String address) throws SQLException, ClassNotFoundException ;

}
