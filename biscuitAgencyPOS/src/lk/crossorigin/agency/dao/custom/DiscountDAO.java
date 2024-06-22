package lk.crossorigin.agency.dao.custom;

import lk.crossorigin.agency.entity.Discount;
import lk.crossorigin.agency.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DiscountDAO {

    public boolean saveDiscount(Discount d) throws SQLException, ClassNotFoundException;
    public boolean updateDiscount(Discount d) throws SQLException, ClassNotFoundException;
    public boolean deleteDiscount(String orderId) throws SQLException, ClassNotFoundException;
    public Discount getItemByOrderId(String orderId) throws SQLException, ClassNotFoundException;
    public ArrayList<Discount> getAllDiscountByIdDup(String orderId,String orderIdDup) throws SQLException, ClassNotFoundException;
    public ArrayList<Discount> getAllDiscount(String text) throws SQLException, ClassNotFoundException;

}
