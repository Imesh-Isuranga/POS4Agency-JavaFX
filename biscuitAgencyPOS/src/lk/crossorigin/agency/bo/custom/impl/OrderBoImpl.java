package lk.crossorigin.agency.bo.custom.impl;

import lk.crossorigin.agency.bo.custom.OrderBO;
import lk.crossorigin.agency.dao.DAOFactory;
import lk.crossorigin.agency.dao.custom.OrderDAO;
import lk.crossorigin.agency.dto.ItemDTO;
import lk.crossorigin.agency.dto.OrderDTO;
import lk.crossorigin.agency.entity.Item;
import lk.crossorigin.agency.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;
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

    @Override
    public ArrayList<OrderDTO> getAllOrders(String text) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDTO> dtoList = new ArrayList<>();
        ArrayList<Order> entityList= orderDAO.getAllOrders(text);
        for (Order s:entityList) {
            System.out.println("999999999999999999999999999999999999");
            System.out.println(s);
            OrderDTO orderDTO = new OrderDTO(
                    s.getOrderId(),
                    s.getDate(),
                    s.getShopId()
            );
            dtoList.add(orderDTO);
        }
        return dtoList;
    }

    public OrderDTO getOrderByOrderId(String orderId) throws SQLException, ClassNotFoundException{
        Order order = orderDAO.getOrderByOrderId(orderId);
        if(order != null){
            return new OrderDTO(
                    order.getOrderId(),
                    order.getDate(),
                    order.getShopId()
            );
        }
        return null;
    }

    public String getLastOrderIdOrder() throws ClassNotFoundException, SQLException {
        return orderDAO.getLastOrderId();
    }


}
