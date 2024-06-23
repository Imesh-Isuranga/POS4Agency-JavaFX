package lk.crossorigin.agency.dao.custom;

import lk.crossorigin.agency.entity.Item;
import lk.crossorigin.agency.entity.ReturnStock;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReturnStockDAO {

    public boolean saveReturn(ReturnStock s) throws SQLException, ClassNotFoundException;
    public boolean updateReturn(ReturnStock i) throws SQLException, ClassNotFoundException;
    public boolean deleteReturn(String orderId) throws SQLException, ClassNotFoundException;
    public ReturnStock getReturn(String orderId,String itemCode) throws SQLException, ClassNotFoundException;
    public ArrayList<ReturnStock> getReturnByOrderId(String text) throws SQLException, ClassNotFoundException;
    public String getLastOrderIdReturn() throws SQLException, ClassNotFoundException;
}
