package lk.crossorigin.agency.bo.custom;

import lk.crossorigin.agency.dto.ShopDTO;
import lk.crossorigin.agency.entity.Shop;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ShopBO extends SuperBO{
    public boolean saveShop(ShopDTO dto) throws ClassNotFoundException, SQLException ;
    public boolean updateShop(ShopDTO dto) throws ClassNotFoundException, SQLException ;
    public boolean updateShopCredit(String id,double creditAmount) throws ClassNotFoundException, SQLException ;
    public boolean updateShopWithoutCredit(ShopDTO dto) throws ClassNotFoundException, SQLException ;
    public boolean deleteShop(String id) throws ClassNotFoundException, SQLException ;
    public ShopDTO getShop(String id) throws SQLException, ClassNotFoundException ;
    public ArrayList<ShopDTO> getAllShops(String text) throws ClassNotFoundException, SQLException ;
    public ArrayList<ShopDTO> getAllShopsByAddress(String text) throws ClassNotFoundException, SQLException ;
    public String generateShopId(String name,String address) throws SQLException, ClassNotFoundException;

}
