package lk.crossorigin.agency.bo.custom;

import lk.crossorigin.agency.dto.OrderDetailsDTO;
import lk.crossorigin.agency.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailBO extends SuperBO{
    public boolean saveOrderDetails(OrderDetailsDTO dto) throws ClassNotFoundException, SQLException ;
    public boolean deleteOrderDetails(String orderId) throws ClassNotFoundException, SQLException ;
    public OrderDetailsDTO getOrderDetail(String orderId,String itemCode) throws SQLException, ClassNotFoundException ;
    public ArrayList<OrderDetailsDTO> getAllOrderDetailsByOrderId(String orderId) throws SQLException, ClassNotFoundException ;
    public ArrayList<OrderDetailsDTO> getAllOrderDetails(String text) throws ClassNotFoundException, SQLException ;
}
