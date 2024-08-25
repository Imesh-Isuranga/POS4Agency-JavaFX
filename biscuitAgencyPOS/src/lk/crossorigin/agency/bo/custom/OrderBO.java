package lk.crossorigin.agency.bo.custom;

import lk.crossorigin.agency.dto.OrderDTO;
import lk.crossorigin.agency.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public interface OrderBO extends SuperBO{
    public boolean saveOrder(OrderDTO dto) throws ClassNotFoundException, SQLException ;
    public boolean deleteOrder(String orderId) throws ClassNotFoundException, SQLException;
    public Date getOrderDate(String orderId) throws ClassNotFoundException, SQLException;

    public ArrayList<OrderDTO> getAllOrders(String text) throws SQLException, ClassNotFoundException ;

    public OrderDTO getOrderByOrderId(String orderId) throws SQLException, ClassNotFoundException;

    public String getLastOrderIdOrder() throws ClassNotFoundException, SQLException ;
}
