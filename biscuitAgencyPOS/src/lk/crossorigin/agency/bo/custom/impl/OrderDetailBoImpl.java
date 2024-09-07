package lk.crossorigin.agency.bo.custom.impl;

import lk.crossorigin.agency.bo.custom.OrderDetailBO;
import lk.crossorigin.agency.bo.custom.PaymentBO;
import lk.crossorigin.agency.dao.DAOFactory;
import lk.crossorigin.agency.dao.custom.OrderDetailsDAO;
import lk.crossorigin.agency.dto.OrderDetailsDTO;
import lk.crossorigin.agency.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class OrderDetailBoImpl implements OrderDetailBO {

    OrderDetailsDAO orderDetailsDAO = DAOFactory.getInstance().getDao(DAOFactory.DaoType.ORDERDETAILS);

    public boolean saveOrderDetails(OrderDetailsDTO dto) throws ClassNotFoundException, SQLException {
        return orderDetailsDAO.saveOrderDetails(new OrderDetail(dto.getOrderId(),dto.getItemCode(),dto.getUnitPrice_Box(),dto.getTotal(),dto.getFree_total(),dto.getDis_tot(),dto.getReturn_tot(),dto.getBoxQty(),dto.getItemQty(),dto.getBoxQtyFree(),dto.getItemQtyFree(),dto.getOrderDate()));
    }
    public boolean deleteOrderDetails(String orderId) throws ClassNotFoundException, SQLException {
        return orderDetailsDAO.deleteOrderDelete(orderId);
    }
    public OrderDetailsDTO getOrderDetail(String orderId,String itemCode) throws SQLException, ClassNotFoundException {
        OrderDetail orderDetail = orderDetailsDAO.getOrderDetail(orderId,itemCode);
        if(orderDetail != null){
            return new OrderDetailsDTO(orderDetail.getOrderId(),orderDetail.getItemCode(),orderDetail.getUnitPrice_Box(),orderDetail.getTotal(),orderDetail.getFree_total(),orderDetail.getDis_total(),orderDetail.getReturn_total(),orderDetail.getBoxQty(),orderDetail.getItemQty(),orderDetail.getBoxQtyFree(),orderDetail.getItemQtyFree(),orderDetail.getOrderDate());
        }
        return null;
    }

    public ArrayList<OrderDetailsDTO> getOrderDetailsByDate(Date date) throws SQLException, ClassNotFoundException{
        ArrayList<OrderDetailsDTO> dtoList = new ArrayList<>();
        ArrayList<OrderDetail> entityList= orderDetailsDAO.getOrderDetailsByDate(date);
        for (OrderDetail s:entityList) {
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(
                    s.getOrderId(),
                    s.getItemCode(),
                    s.getUnitPrice_Box(),
                    s.getTotal(),
                    s.getFree_total(),
                    s.getDis_total(),
                    s.getReturn_total(),
                    s.getBoxQty(),
                    s.getItemQty(),
                    s.getBoxQtyFree(),
                    s.getItemQtyFree(),
                    s.getOrderDate()
            );
            dtoList.add(orderDetailsDTO);
        }
        return dtoList;
    }


    public ArrayList<OrderDetailsDTO> getAllOrderDetailsByOrderId(String orderId) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailsDTO> dtoList = new ArrayList<>();
        ArrayList<OrderDetail> entityList= orderDetailsDAO.getAllOrderDetailsByOrderId(orderId);
        for (OrderDetail s:entityList) {
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(
                    s.getOrderId(),
                    s.getItemCode(),
                    s.getUnitPrice_Box(),
                    s.getTotal(),
                    s.getFree_total(),
                    s.getDis_total(),
                    s.getReturn_total(),
                    s.getBoxQty(),
                    s.getItemQty(),
                    s.getBoxQtyFree(),
                    s.getItemQtyFree(),
                    s.getOrderDate()
            );
            dtoList.add(orderDetailsDTO);
        }
        return dtoList;
    }
    public ArrayList<OrderDetailsDTO> getAllOrderDetails(String text) throws ClassNotFoundException, SQLException {
        ArrayList<OrderDetailsDTO> dtoList = new ArrayList<>();
        ArrayList<OrderDetail> entityList= orderDetailsDAO.getAllOrderDetails(text);
        for (OrderDetail s:entityList) {
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(
                    s.getOrderId(),
                    s.getItemCode(),
                    s.getUnitPrice_Box(),
                    s.getTotal(),
                    s.getDis_total(),
                    s.getReturn_total(),
                    s.getFree_total(),
                    s.getBoxQty(),
                    s.getItemQty(),
                    s.getBoxQtyFree(),
                    s.getItemQtyFree(),
                    s.getOrderDate()
            );
            dtoList.add(orderDetailsDTO);
        }
        return dtoList;
    }

}
