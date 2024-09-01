package lk.crossorigin.agency.bo.custom;

import lk.crossorigin.agency.dto.MonthlyReportDTO;
import lk.crossorigin.agency.dto.ShopDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MonthlyReportBO extends SuperBO{
    public boolean saveMonthlyReport(MonthlyReportDTO s) throws SQLException, ClassNotFoundException ;

    public boolean deleteMonthlyReport() throws SQLException, ClassNotFoundException ;

}
