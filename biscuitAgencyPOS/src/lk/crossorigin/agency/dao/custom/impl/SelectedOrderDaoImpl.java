package lk.crossorigin.agency.dao.custom.impl;

import lk.crossorigin.agency.dao.CrudUtil;
import lk.crossorigin.agency.dao.custom.ReturnStockDAO;
import lk.crossorigin.agency.dao.custom.SelectedOrderDAO;
import lk.crossorigin.agency.entity.Item;
import lk.crossorigin.agency.entity.ReturnStock;
import lk.crossorigin.agency.entity.SelectedOrder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SelectedOrderDaoImpl implements SelectedOrderDAO {


    @Override
    public boolean saveOrderId(SelectedOrder s) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO selectedorder VALUES(?,?)";
        return CrudUtil.executeUpdate(sql,s.getAuto_id(),s.getOrderId());
    }

    @Override
    public String getOrderId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT or_id FROM selectedorder ORDER BY or_id DESC LIMIT 1";
        ResultSet rst = CrudUtil.executeQuery(sql);

        if (rst.next()) {
            return rst.getString(1);
        } else {
            return "1-1-1";
        }
    }

    @Override
    public boolean deleteOrderId() throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM SelectedOrder";
        return CrudUtil.executeUpdate(sql);
    }
}
