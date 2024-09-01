package lk.crossorigin.agency.bo.custom.impl;

import lk.crossorigin.agency.bo.custom.MonthlyReportBO;
import lk.crossorigin.agency.bo.custom.OrderHistoryBO;
import lk.crossorigin.agency.dao.DAOFactory;
import lk.crossorigin.agency.dao.custom.MonthlyReportDAO;
import lk.crossorigin.agency.dao.custom.OrderHistoryDAO;
import lk.crossorigin.agency.dto.MonthlyReportDTO;
import lk.crossorigin.agency.dto.OrderHistoryDTO;
import lk.crossorigin.agency.entity.MonthlyReport;
import lk.crossorigin.agency.entity.OrderHistory;

import java.sql.SQLException;

public class OrderHistoryBoImpl implements OrderHistoryBO {

    OrderHistoryDAO orderHistoryDAO = DAOFactory.getInstance().getDao(DAOFactory.DaoType.ORDERHISTORY);

    public boolean saveOrderHistory(OrderHistoryDTO s) throws SQLException, ClassNotFoundException {
        return orderHistoryDAO.saveOrderHistory(new OrderHistory(
                s.getId(),
                s.getInvoice_num(),
                s.getName_of_dealer(),
                s.getTotal(),
                s.getCash(),
                s.getCredit(),
                s.getCheque(),
                s.getCheque_no(),
                s.getMr(),
                s.getDiscount())
        );
    }

    public boolean deleteOrderHistory() throws SQLException, ClassNotFoundException {
        return orderHistoryDAO.deleteOrderHistory();
    }

}
