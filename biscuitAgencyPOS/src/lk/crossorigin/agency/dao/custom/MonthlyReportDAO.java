package lk.crossorigin.agency.dao.custom;

import lk.crossorigin.agency.entity.MonthlyReport;
import lk.crossorigin.agency.entity.Shop;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MonthlyReportDAO {
    public boolean saveMonthlyReport(MonthlyReport s) throws SQLException, ClassNotFoundException;
    public boolean deleteMonthlyReport() throws SQLException, ClassNotFoundException ;


}
