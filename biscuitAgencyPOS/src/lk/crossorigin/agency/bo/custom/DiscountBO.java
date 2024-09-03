package lk.crossorigin.agency.bo.custom;

import lk.crossorigin.agency.dto.DiscountDTO;
import lk.crossorigin.agency.entity.Discount;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DiscountBO extends SuperBO{
    public boolean saveDiscount(DiscountDTO d) throws SQLException, ClassNotFoundException ;
    public boolean updateDiscount(DiscountDTO d) throws SQLException, ClassNotFoundException ;
    public boolean deleteDiscount(String orderId) throws SQLException, ClassNotFoundException ;
    public boolean deleteDiscountByIdDup(int idDup) throws SQLException, ClassNotFoundException ;
    public DiscountDTO getItemByOrderId(String disId) throws SQLException, ClassNotFoundException ;
    public ArrayList<DiscountDTO> getAllDiscountByIdDup(String orderId, String orderIdDup) throws SQLException, ClassNotFoundException ;
    public ArrayList<DiscountDTO> getAllDiscountByOrderId(String orderId) throws SQLException, ClassNotFoundException;
    public ArrayList<DiscountDTO> getAllDiscount(String text) throws SQLException, ClassNotFoundException;
    public String getLastDiscountId() throws SQLException, ClassNotFoundException ;


}
