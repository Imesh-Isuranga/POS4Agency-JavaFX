package lk.crossorigin.agency.bo.custom;

import lk.crossorigin.agency.dto.OrderDTO;
import lk.crossorigin.agency.dto.SelectedOrderDTO;
import lk.crossorigin.agency.entity.SelectedOrder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public interface SelectedOrderBO extends SuperBO{
    public boolean saveOrderId(SelectedOrderDTO s) throws SQLException, ClassNotFoundException;
    public String getOrderId() throws SQLException, ClassNotFoundException;
    public boolean deleteOrderId() throws SQLException, ClassNotFoundException;
}
