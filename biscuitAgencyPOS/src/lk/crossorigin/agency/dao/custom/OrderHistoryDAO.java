package lk.crossorigin.agency.dao.custom;

import lk.crossorigin.agency.entity.MonthlyReport;
import lk.crossorigin.agency.entity.OrderHistory;

import java.sql.SQLException;

public interface OrderHistoryDAO {
    public boolean saveOrderHistory(OrderHistory s) throws SQLException, ClassNotFoundException;
    public boolean deleteOrderHistory() throws SQLException, ClassNotFoundException ;


}
