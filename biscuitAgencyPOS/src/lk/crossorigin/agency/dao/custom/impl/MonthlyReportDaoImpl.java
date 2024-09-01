package lk.crossorigin.agency.dao.custom.impl;

import lk.crossorigin.agency.dao.CrudUtil;
import lk.crossorigin.agency.dao.custom.MonthlyReportDAO;
import lk.crossorigin.agency.dao.custom.ShopDAO;
import lk.crossorigin.agency.entity.MonthlyReport;
import lk.crossorigin.agency.entity.Shop;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MonthlyReportDaoImpl implements MonthlyReportDAO {
    //Shop Management
    @Override
    public boolean saveMonthlyReport(MonthlyReport s) throws SQLException, ClassNotFoundException {
        String sql1 = "INSERT INTO monthlyreport VALUES(?,?,?,?,?)";
        if(CrudUtil.executeUpdate(sql1,s.getId(),s.getDate(),s.getTotal(),s.getMr(),s.getDiscount())){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean deleteMonthlyReport() throws SQLException, ClassNotFoundException {
        String checkSql = "SELECT COUNT(*) FROM monthlyreport";
        String sql = "DELETE FROM monthlyreport";
        int rowCount=0;
        ResultSet resultSet = CrudUtil.executeQuery(checkSql);
        if (resultSet.next()) {  // Move the cursor to the first row
            rowCount = resultSet.getInt(1);
        }
        if(rowCount>0){
            return CrudUtil.executeUpdate(sql);
        }else{
            return true;
        }
    }
}
