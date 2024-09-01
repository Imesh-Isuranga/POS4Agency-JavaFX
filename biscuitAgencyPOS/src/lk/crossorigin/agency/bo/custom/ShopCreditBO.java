package lk.crossorigin.agency.bo.custom;

import lk.crossorigin.agency.dto.ItemDTO;
import lk.crossorigin.agency.dto.ShopCreditDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public interface ShopCreditBO extends SuperBO{
    public boolean saveCredit(ShopCreditDTO dto) throws ClassNotFoundException, SQLException ;
    public boolean deleteCreditById(int id) throws ClassNotFoundException, SQLException ;
    public boolean deleteCreditByshop(String shopId) throws ClassNotFoundException, SQLException ;
    public boolean updateCredit(ShopCreditDTO dto) throws ClassNotFoundException, SQLException ;
    public ArrayList<ShopCreditDTO> getItemByShopId(String shopId) throws SQLException, ClassNotFoundException ;
    public ArrayList<ShopCreditDTO> getItemByAmount(double amount) throws SQLException, ClassNotFoundException ;
    public ArrayList<ShopCreditDTO> getItemByDate(Date date) throws SQLException, ClassNotFoundException ;
    public ArrayList<ShopCreditDTO> getAllCreditsShops(String text) throws ClassNotFoundException, SQLException ;

}
