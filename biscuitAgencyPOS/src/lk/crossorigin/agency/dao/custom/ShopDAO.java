package lk.crossorigin.agency.dao.custom;

import lk.crossorigin.agency.dto.ShopDTO;
import lk.crossorigin.agency.entity.Shop;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ShopDAO {
    public boolean saveShop(Shop s) throws SQLException, ClassNotFoundException;
    public boolean updateShop(Shop s) throws SQLException, ClassNotFoundException;
    public boolean deleteShop(String id) throws SQLException, ClassNotFoundException;
    public ArrayList<Shop> getAllShops(String text) throws SQLException, ClassNotFoundException;

}
