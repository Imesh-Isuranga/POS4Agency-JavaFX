package lk.crossorigin.agency.bo.custom.impl;

import lk.crossorigin.agency.bo.custom.OrderBO;
import lk.crossorigin.agency.bo.custom.SelectedOrderBO;
import lk.crossorigin.agency.dao.CrudUtil;
import lk.crossorigin.agency.dao.DAOFactory;
import lk.crossorigin.agency.dao.custom.OrderDAO;
import lk.crossorigin.agency.dao.custom.SelectedOrderDAO;
import lk.crossorigin.agency.dto.OrderDTO;
import lk.crossorigin.agency.dto.SelectedOrderDTO;
import lk.crossorigin.agency.entity.Item;
import lk.crossorigin.agency.entity.Order;
import lk.crossorigin.agency.entity.SelectedOrder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class SelectedOrderBoImpl implements SelectedOrderBO {

    SelectedOrderDAO selectedOrderDAO = DAOFactory.getInstance().getDao(DAOFactory.DaoType.SELECTEDORDER);



    @Override
    public boolean saveOrderId(SelectedOrderDTO s) throws SQLException, ClassNotFoundException {
        return selectedOrderDAO.saveOrderId(new SelectedOrder(s.getAuto_id(),s.getOrderId()));
    }

    @Override
    public String getOrderId() throws SQLException, ClassNotFoundException {
        return selectedOrderDAO.getOrderId();
    }

    @Override
    public boolean deleteOrderId() throws SQLException, ClassNotFoundException {
        return selectedOrderDAO.deleteOrderId();
    }


}
