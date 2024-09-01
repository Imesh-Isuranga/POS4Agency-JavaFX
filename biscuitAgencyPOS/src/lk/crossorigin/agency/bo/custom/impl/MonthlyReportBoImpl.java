package lk.crossorigin.agency.bo.custom.impl;

import lk.crossorigin.agency.bo.custom.MonthlyReportBO;
import lk.crossorigin.agency.bo.custom.ShopBO;
import lk.crossorigin.agency.dao.DAOFactory;
import lk.crossorigin.agency.dao.custom.MonthlyReportDAO;
import lk.crossorigin.agency.dao.custom.ShopDAO;
import lk.crossorigin.agency.dto.MonthlyReportDTO;
import lk.crossorigin.agency.dto.ShopDTO;
import lk.crossorigin.agency.entity.MonthlyReport;
import lk.crossorigin.agency.entity.Shop;

import java.sql.SQLException;
import java.util.ArrayList;

public class MonthlyReportBoImpl implements MonthlyReportBO {

    MonthlyReportDAO monthlyReportDAO = DAOFactory.getInstance().getDao(DAOFactory.DaoType.MONTHLYREPORT);

    public boolean saveMonthlyReport(MonthlyReportDTO s) throws SQLException, ClassNotFoundException {
        return monthlyReportDAO.saveMonthlyReport(new MonthlyReport(s.getId(),s.getDate(),s.getTotal(),s.getMr(),s.getDiscount()));
    }

    public boolean deleteMonthlyReport() throws SQLException, ClassNotFoundException {
        return monthlyReportDAO.deleteMonthlyReport();
    }

}
