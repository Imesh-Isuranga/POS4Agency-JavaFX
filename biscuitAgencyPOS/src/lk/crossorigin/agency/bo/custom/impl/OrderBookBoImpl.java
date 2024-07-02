package lk.crossorigin.agency.bo.custom.impl;

import lk.crossorigin.agency.bo.custom.OrderBookBO;
import lk.crossorigin.agency.dao.DAOFactory;
import lk.crossorigin.agency.dao.custom.OrderBookDAO;
import lk.crossorigin.agency.dto.OrderBookDTO;
import lk.crossorigin.agency.entity.OrderBook;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBookBoImpl implements OrderBookBO {

    OrderBookDAO orderBookDAO = DAOFactory.getInstance().getDao(DAOFactory.DaoType.ORDERBOOK);

    public boolean saveOrderBook(OrderBookDTO s) throws SQLException, ClassNotFoundException{
        return orderBookDAO.saveOrderBook(new OrderBook(s.getId(),s.getBookId(),s.getInvId(),s.getShopId()));
    }
    public boolean deleteOrderBook(String id) throws SQLException, ClassNotFoundException{
        return orderBookDAO.deleteOrderBook(id);
    }
    public OrderBookDTO getOrderBook(String orderId) throws SQLException, ClassNotFoundException{
        OrderBook orderBook = orderBookDAO.getOrderBook(orderId);
        if(orderBook != null){
            return new OrderBookDTO(
                    orderBook.getOb_id(),
                    orderBook.getId(),
                    orderBook.getBookId(),
                    orderBook.getInvId(),
                    orderBook.getShopId()
            );
        }
        return null;
    }
    public ArrayList<OrderBookDTO> getAllOrderBooks(String text) throws SQLException, ClassNotFoundException {
        ArrayList<OrderBookDTO> dtoList = new ArrayList<>();
        ArrayList<OrderBook> entityList= orderBookDAO.getAllOrderBooks(text);
        for (OrderBook orderBook:entityList) {
            OrderBookDTO OrderBookDTO = new OrderBookDTO(
                    orderBook.getOb_id(),
                    orderBook.getId(),
                    orderBook.getBookId(),
                    orderBook.getInvId(),
                    orderBook.getShopId()
            );
            dtoList.add(OrderBookDTO);
        }
        return dtoList;
    }
    public String generateOrderId(String bookName,String invNum) throws SQLException, ClassNotFoundException {
        return orderBookDAO.generateOrderId(bookName,invNum);
    }
    public String getLastOrderId() throws SQLException, ClassNotFoundException {
        return orderBookDAO.getLastOrderId();
    }

}
