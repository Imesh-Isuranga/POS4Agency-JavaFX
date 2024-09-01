package lk.crossorigin.agency.dao.custom;

import lk.crossorigin.agency.entity.Shop;
import lk.crossorigin.agency.entity.ShopCredit;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public interface ShopCreditDAO {
    public boolean saveCredit(ShopCredit dto) throws ClassNotFoundException, SQLException ;
    public boolean deleteCreditById(int id) throws ClassNotFoundException, SQLException ;
    public boolean deleteCreditByshop(String shopId) throws ClassNotFoundException, SQLException ;
    public boolean updateCredit(ShopCredit dto) throws ClassNotFoundException, SQLException ;
    public ArrayList<ShopCredit> getItemByShopId(String shopId) throws SQLException, ClassNotFoundException ;
    public ArrayList<ShopCredit> getItemByAmount(double amount) throws SQLException, ClassNotFoundException ;
    public ArrayList<ShopCredit> getItemByDate(Date date) throws SQLException, ClassNotFoundException ;
    public ArrayList<ShopCredit> getAllCreditsShops(String text) throws ClassNotFoundException, SQLException ;

}
