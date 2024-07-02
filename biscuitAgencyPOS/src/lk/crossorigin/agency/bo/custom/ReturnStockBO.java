package lk.crossorigin.agency.bo.custom;

import lk.crossorigin.agency.dto.ReturnStockDTO;
import lk.crossorigin.agency.entity.ReturnStock;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReturnStockBO extends SuperBO{
    public boolean saveReturn(ReturnStockDTO dto) throws SQLException, ClassNotFoundException;
    public boolean updateReturn(ReturnStockDTO dto) throws SQLException, ClassNotFoundException;
    public boolean deleteReturn(String orderId) throws SQLException, ClassNotFoundException;
    public ReturnStockDTO getReturn(String orderId,String itemCode) throws SQLException, ClassNotFoundException;
    public ArrayList<ReturnStockDTO> getReturnByOrderId(String text) throws SQLException, ClassNotFoundException;
    public String getLastOrderIdReturn() throws SQLException, ClassNotFoundException ;
}
