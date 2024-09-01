package lk.crossorigin.agency.bo.custom;

import lk.crossorigin.agency.dto.MonthlyReportDTO;
import lk.crossorigin.agency.dto.OrderHistoryDTO;

import java.sql.SQLException;

public interface OrderHistoryBO extends SuperBO{
    public boolean saveOrderHistory(OrderHistoryDTO s) throws SQLException, ClassNotFoundException ;

    public boolean deleteOrderHistory() throws SQLException, ClassNotFoundException ;

}
