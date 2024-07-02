package lk.crossorigin.agency.bo.custom.impl;

import lk.crossorigin.agency.bo.custom.OrderBO;
import lk.crossorigin.agency.dao.DAOFactory;
import lk.crossorigin.agency.dao.custom.OrderDAO;
import lk.crossorigin.agency.dto.OrderDTO;
import lk.crossorigin.agency.entity.Order;

import java.sql.SQLException;
import java.util.Date;

public class OrderBoImpl implements OrderBO {

    OrderDAO orderDAO = DAOFactory.getInstance().getDao(DAOFactory.DaoType.ORDER);

    public boolean saveOrder(OrderDTO dto) throws ClassNotFoundException, SQLException {
        return orderDAO.saveOrder(new Order(dto.getOrderId(),dto.getDate(),dto.getShopId()));
    }
    public boolean deleteOrder(String orderId) throws ClassNotFoundException, SQLException {
        return orderDAO.deleteOrder(orderId);
    }
    public Date getOrderDate(String orderId) throws ClassNotFoundException, SQLException {
        return orderDAO.getOrderDate(orderId);
    }
    public String getLastOrderIdOrder() throws ClassNotFoundException, SQLException {
        return orderDAO.getLastOrderId();
    }


}
