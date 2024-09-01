package lk.crossorigin.agency.dao.custom.impl;

import lk.crossorigin.agency.dao.CrudUtil;
import lk.crossorigin.agency.dao.custom.MonthlyReportDAO;
import lk.crossorigin.agency.dao.custom.OrderHistoryDAO;
import lk.crossorigin.agency.entity.MonthlyReport;
import lk.crossorigin.agency.entity.OrderHistory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderHistoryDaoImpl implements OrderHistoryDAO {
    //Shop Management
    @Override
    public boolean saveOrderHistory(OrderHistory s) throws SQLException, ClassNotFoundException {
        String sql1 = "INSERT INTO OrderHistory VALUES(?,?,?,?,?,?,?,?,?,?)";
        if(CrudUtil.executeUpdate(sql1,
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
        ){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean deleteOrderHistory() throws SQLException, ClassNotFoundException {
        String checkSql = "SELECT COUNT(*) FROM OrderHistory";
        String sql = "DELETE FROM OrderHistory";
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
