package lk.crossorigin.agency.dao.custom;

import lk.crossorigin.agency.entity.ReturnStock;
import lk.crossorigin.agency.entity.SelectedOrder;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SelectedOrderDAO {

    public boolean saveOrderId(SelectedOrder s) throws SQLException, ClassNotFoundException;
    public String getOrderId() throws SQLException, ClassNotFoundException;
    public boolean deleteOrderId() throws SQLException, ClassNotFoundException;
}
