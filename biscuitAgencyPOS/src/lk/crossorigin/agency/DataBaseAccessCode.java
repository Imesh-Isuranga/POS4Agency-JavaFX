package lk.crossorigin.agency;

import lk.crossorigin.agency.dao.custom.impl.*;
import lk.crossorigin.agency.dto.*;
import lk.crossorigin.agency.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class DataBaseAccessCode {

    //Shop Management----------------
    public boolean saveShop(ShopDTO dto) throws ClassNotFoundException, SQLException {
        return new ShopDaoImpl().saveShop(new Shop(dto.getSh_id(),dto.getId(),dto.getName(),dto.getAddress(),dto.getCredit_uptoNow()));
    }

    public boolean updateShop(ShopDTO dto) throws ClassNotFoundException, SQLException {
        return new ShopDaoImpl().updateShop(new Shop(dto.getSh_id(),dto.getId(),dto.getName(),dto.getAddress(),dto.getCredit_uptoNow()));
    }
    public boolean updateShopCredit(String id,double creditAmount) throws ClassNotFoundException, SQLException {
        return new ShopDaoImpl().updateShopCredit(id,creditAmount);
    }
    public boolean updateShopWithoutCredit(ShopDTO dto) throws ClassNotFoundException, SQLException {
        return new ShopDaoImpl().updateShopWithoutCredit(new Shop(dto.getSh_id(),dto.getId(),dto.getName(),dto.getAddress()));
    }

    public boolean deleteShop(String id) throws ClassNotFoundException, SQLException {
        return new ShopDaoImpl().deleteShop(id);
    }

    public ShopDTO getShop(String id) throws SQLException, ClassNotFoundException {
        Shop shop = new ShopDaoImpl().getShop(id);
        if(shop != null){
            return new ShopDTO(shop.getSh_id(),shop.getId(),shop.getName(),shop.getAddress(),shop.getCredit_uptoNow());
        }
        return null;
    }

    public ArrayList<ShopDTO> getAllShops(String text) throws ClassNotFoundException, SQLException {
        ArrayList<ShopDTO> dtoList = new ArrayList<>();
        ArrayList<Shop> entityList= new ShopDaoImpl().getAllShops(text);
        for (Shop s:entityList) {
            ShopDTO shopDTO = new ShopDTO(
                    s.getSh_id(),
                    s.getId(),
                    s.getName(),
                    s.getAddress(),
                    s.getCredit_uptoNow()
            );
            dtoList.add(shopDTO);
        }
        return dtoList;
    }

    public String generateShopId(String name,String address) throws SQLException, ClassNotFoundException {
        return new ShopDaoImpl().generateShopId(name,address);
    }



    //Item Management----------------
    public boolean saveItem(ItemDTO dto) throws ClassNotFoundException, SQLException {
        return new ItemDaoImpl().saveItem(new Item(dto.getCode(),dto.getName(), dto.getUnitPrice_Box_Agency(),dto.getUnitPrice_Box(), dto.getItemCountInBox(), dto.getBoxQty(),dto.getItemQty()));
    }

    public boolean deleteItem(String code) throws ClassNotFoundException, SQLException {
        return new ItemDaoImpl().deleteItem(code);
    }

    public boolean updateItem(ItemDTO dto) throws ClassNotFoundException, SQLException {
        return new ItemDaoImpl().updateItem(new Item(dto.getCode(),dto.getName(), dto.getUnitPrice_Box_Agency(),dto.getUnitPrice_Box(),dto.getItemCountInBox(),dto.getBoxQty(),dto.getItemQty()));
    }

    public boolean updateItemQtys(ItemDTO dto) throws ClassNotFoundException, SQLException {
        return new ItemDaoImpl().updateItemQtys(new Item(dto.getCode(),dto.getBoxQty(),dto.getItemQty()));
    }

    public ItemDTO getItem(String id) throws SQLException, ClassNotFoundException {
        Item item = new ItemDaoImpl().getItem(id);
        if(item != null){
            return new ItemDTO(
                    item.getCode(),
                    item.getName(),
                    item.getUnitPrice_Box_Agency(),
                    item.getUnitPrice_Box(),
                    item.getItemCountInBox(),
                    item.getBoxQty(),
                    item.getItemQty()
            );
        }
        return null;
    }

    public ItemDTO getItemByName(String name) throws SQLException, ClassNotFoundException {
        Item item = new ItemDaoImpl().getItem(name);
        if(item != null){
            return new ItemDTO(
                    item.getCode(),
                    item.getName(),
                    item.getUnitPrice_Box_Agency(),
                    item.getUnitPrice_Box(),
                    item.getItemCountInBox(),
                    item.getBoxQty(),
                    item.getItemQty()
            );
        }
        return null;
    }

    public ArrayList<ItemDTO> getAllItems(String text) throws ClassNotFoundException, SQLException {
        ArrayList<ItemDTO> dtoList = new ArrayList<>();
        ArrayList<Item> entityList= new ItemDaoImpl().getAllItems(text);
        for (Item s:entityList) {
            ItemDTO itemDTO = new ItemDTO(
                    s.getCode(),
                    s.getName(),
                    s.getUnitPrice_Box_Agency(),
                    s.getUnitPrice_Box(),
                    s.getItemCountInBox(),
                    s.getBoxQty(),
                    s.getItemQty()
            );
            dtoList.add(itemDTO);
        }
        return dtoList;
    }


    //Order Management----------------
    public boolean saveOrder(OrderDTO dto) throws ClassNotFoundException, SQLException {
        return new OrderDaoImpl().saveOrder(new Order(dto.getOrderId(),dto.getDate(),dto.getShopId()));
    }

    public boolean deleteOrder(String orderId) throws ClassNotFoundException, SQLException {
        return new OrderDaoImpl().deleteOrder(orderId);
    }

    public Date getOrderDate(String orderId) throws ClassNotFoundException, SQLException {
        return new OrderDaoImpl().getOrderDate(orderId);
    }

   public String getLastOrderIdOrder() throws ClassNotFoundException, SQLException {
        return new OrderDaoImpl().getLastOrderId();
    }



    //OrderDetails Management----------------
    public boolean saveOrderDetails(OrderDetailsDTO dto) throws ClassNotFoundException, SQLException {
        return new OrderDetailsDaoImpl().saveOrderDetails(new OrderDetail(dto.getOrderId(),dto.getItemCode(),dto.getUnitPrice_Box(),dto.getBoxQty(),dto.getItemQty(),dto.getBoxQtyFree(),dto.getItemQtyFree()));
    }

    public boolean deleteOrderDetails(String orderId) throws ClassNotFoundException, SQLException {
        return new OrderDetailsDaoImpl().deleteOrderDelete(orderId);
    }

    public OrderDetailsDTO getOrderDetail(String orderId,String itemCode) throws SQLException, ClassNotFoundException {
        OrderDetail orderDetail = new OrderDetailsDaoImpl().getOrderDetail(orderId,itemCode);
        if(orderDetail != null){
            return new OrderDetailsDTO(orderDetail.getOrderId(),orderDetail.getItemCode(),orderDetail.getUnitPrice_Box(),orderDetail.getBoxQty(),orderDetail.getItemQty(),orderDetail.getBoxQtyFree(),orderDetail.getItemQtyFree());
        }
        return null;
    }

    public ArrayList<OrderDetailsDTO> getAllOrderDetailsByOrderId(String orderId) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailsDTO> dtoList = new ArrayList<>();
        ArrayList<OrderDetail> entityList= new OrderDetailsDaoImpl().getAllOrderDetailsByOrderId(orderId);
        for (OrderDetail s:entityList) {
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(
                    s.getOrderId(),
                    s.getItemCode(),
                    s.getUnitPrice_Box(),
                    s.getBoxQty(),
                    s.getItemQty(),
                    s.getBoxQtyFree(),
                    s.getItemQtyFree()
            );
            dtoList.add(orderDetailsDTO);
        }
        return dtoList;
    }

    public ArrayList<OrderDetailsDTO> getAllOrderDetails(String text) throws ClassNotFoundException, SQLException {
        ArrayList<OrderDetailsDTO> dtoList = new ArrayList<>();
        ArrayList<OrderDetail> entityList= new OrderDetailsDaoImpl().getAllOrderDetails(text);
        for (OrderDetail s:entityList) {
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(
                    s.getOrderId(),
                    s.getItemCode(),
                    s.getUnitPrice_Box(),
                    s.getBoxQty(),
                    s.getItemQty(),
                    s.getBoxQtyFree(),
                    s.getItemQtyFree()
            );
            dtoList.add(orderDetailsDTO);
        }
        return dtoList;
    }



    //Discount Management----------------
    public boolean saveDiscount(DiscountDTO d) throws SQLException, ClassNotFoundException {
        return new DiscountDaoImpl().saveDiscount(new Discount(d.getIdDup(),d.getOrderId(),d.getItemCode(),d.getDiscountValue()));
    }

    public boolean updateDiscount(DiscountDTO d) throws SQLException, ClassNotFoundException {
        return new DiscountDaoImpl().updateDiscount(new Discount(d.getIdDup(),d.getOrderId(),d.getItemCode(),d.getDiscountValue()));
    }

    public boolean deleteDiscount(String orderId) throws SQLException, ClassNotFoundException {
        return new DiscountDaoImpl().deleteDiscount(orderId);
    }

    public DiscountDTO getItemByOrderId(String disId) throws SQLException, ClassNotFoundException {
        Discount discount = new DiscountDaoImpl().getItemByOrderId(disId);
        if(discount != null){
            return new DiscountDTO(discount.getIdDup(),discount.getOrderId(),discount.getItemCode(),discount.getDiscountValue());
        }
        return null;
    }

    public ArrayList<DiscountDTO> getAllDiscountByIdDup(String orderId, String orderIdDup) throws SQLException, ClassNotFoundException {
        ArrayList<DiscountDTO> dtoList = new ArrayList<>();
        ArrayList<Discount> entityList= new DiscountDaoImpl().getAllDiscountByIdDup(orderId,orderIdDup);
        for (Discount d:entityList) {
            DiscountDTO discountDTO = new DiscountDTO(
                    d.getIdDup(),
                    d.getOrderId(),
                    d.getItemCode(),
                    d.getDiscountValue()
            );
            dtoList.add(discountDTO);
        }
        return dtoList;
    }

    public ArrayList<DiscountDTO> getAllDiscountByOrderId(String orderId) throws SQLException, ClassNotFoundException{
        ArrayList<DiscountDTO> dtoList = new ArrayList<>();
        ArrayList<Discount> entityList= new DiscountDaoImpl().getAllDiscountByOrderId(orderId);
        for (Discount d:entityList) {
            DiscountDTO discountDTO = new DiscountDTO(
                    d.getIdDup(),
                    d.getOrderId(),
                    d.getItemCode(),
                    d.getDiscountValue()
            );
            dtoList.add(discountDTO);
        }
        return dtoList;
    }

    public ArrayList<DiscountDTO> getAllDiscount(String text) throws SQLException, ClassNotFoundException {
        ArrayList<DiscountDTO> dtoList = new ArrayList<>();
        ArrayList<Discount> entityList= new DiscountDaoImpl().getAllDiscount(text);
        for (Discount d:entityList) {
            DiscountDTO discountDTO = new DiscountDTO(
                    d.getIdDup(),
                    d.getOrderId(),
                    d.getItemCode(),
                    d.getDiscountValue()
            );
            dtoList.add(discountDTO);
        }
        return dtoList;
    }

    public String getLastDiscountId() throws SQLException, ClassNotFoundException {
        return new DiscountDaoImpl().getLastDiscountId();
    }



    //Payment Management----------------
    public boolean savePayment(PaymentDTO p) throws SQLException, ClassNotFoundException{
        return new PaymentDaoImpl().savePayment(new Payment(p.getId(),p.getOrderId(),p.getPayment_Details(),p.getPayment_Way(),p.getAmount()));
    }
    public ArrayList<PaymentDTO> getPaymentByOrderId(String orderId) throws SQLException, ClassNotFoundException{
        ArrayList<PaymentDTO> dtoList = new ArrayList<>();
        ArrayList<Payment> entityList= new PaymentDaoImpl().getPaymentByOrderId(orderId);
        for (Payment p:entityList) {
            PaymentDTO paymentDTO = new PaymentDTO(
                    p.getId(),
                    p.getOrderId(),
                    p.getPayment_Details(),
                    p.getPayment_Way(),
                    p.getAmount()
            );
            dtoList.add(paymentDTO);
        }
        return dtoList;
    }
    public ArrayList<PaymentDTO> getAllPayments(String text) throws SQLException, ClassNotFoundException{
        ArrayList<PaymentDTO> dtoList = new ArrayList<>();
        ArrayList<Payment> entityList= new PaymentDaoImpl().getAllPayments(text);
        for (Payment p:entityList) {
            PaymentDTO paymentDTO = new PaymentDTO(
                    p.getId(),
                    p.getOrderId(),
                    p.getPayment_Details(),
                    p.getPayment_Way(),
                    p.getAmount()
            );
            dtoList.add(paymentDTO);
        }
        return dtoList;
    }


    //OrderBook Management----------------
    public boolean saveOrderBook(OrderBookDTO s) throws SQLException, ClassNotFoundException{
        return new OrderBookDaoimpl().saveOrderBook(new OrderBook(s.getId(),s.getBookId(),s.getInvId(),s.getShopId()));
    }
    public boolean deleteOrderBook(String id) throws SQLException, ClassNotFoundException{
        return new OrderBookDaoimpl().deleteOrderBook(id);
    }
    public OrderBookDTO getOrderBook(String orderId) throws SQLException, ClassNotFoundException{
        OrderBook orderBook = new OrderBookDaoimpl().getOrderBook(orderId);
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
        ArrayList<OrderBook> entityList= new OrderBookDaoimpl().getAllOrderBooks(text);
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
        return new OrderBookDaoimpl().generateOrderId(bookName,invNum);
    }
    public String getLastOrderId() throws SQLException, ClassNotFoundException {
        return new OrderBookDaoimpl().getLastOrderId();
    }



    //ReturnStock Management----------------


    public boolean saveReturn(ReturnStockDTO dto) throws SQLException, ClassNotFoundException{
        return new ReturnDapImpl().saveReturn(
                new ReturnStock(
                        dto.getId(),
                        dto.getOrderId(),
                        dto.getItemCode(),
                        dto.getBoxQty()
                        ,dto.getItemQty()
                )
        );

    }
    public boolean updateReturn(ReturnStockDTO dto) throws SQLException, ClassNotFoundException{
        return new ReturnDapImpl().updateReturn(
                new ReturnStock(
                        dto.getId(),
                        dto.getOrderId(),
                        dto.getItemCode(),
                        dto.getBoxQty(),
                        dto.getItemQty()
                )
        );

    }
    public boolean deleteReturn(String orderId) throws SQLException, ClassNotFoundException{
        return new ReturnDapImpl().deleteReturn(orderId);

    }
    public ReturnStockDTO getReturn(String orderId,String itemCode) throws SQLException, ClassNotFoundException{
        ReturnStock returnStock = new ReturnDapImpl().getReturn(orderId,itemCode);
        if(returnStock != null){
            return new ReturnStockDTO(
                    returnStock.getId(),
                    returnStock.getOrderId(),
                    returnStock.getItemCode(),
                    returnStock.getBoxQty(),
                    returnStock.getItemQty()
            );
        }
        return null;
    }
    public ArrayList<ReturnStockDTO> getReturnByOrderId(String text) throws SQLException, ClassNotFoundException{
        ArrayList<ReturnStockDTO> dtoList = new ArrayList<>();
        ArrayList<ReturnStock> entityList= new ReturnDapImpl().getReturnByOrderId(text);
        for (ReturnStock returnStock:entityList) {
            ReturnStockDTO returnStockDTO = new ReturnStockDTO(
                    returnStock.getId(),
                    returnStock.getOrderId(),
                    returnStock.getItemCode(),
                    returnStock.getBoxQty(),
                    returnStock.getItemQty()
            );
            dtoList.add(returnStockDTO);
        }
        return dtoList;
    }

    public String getLastOrderIdReturn() throws SQLException, ClassNotFoundException {
        return new ReturnDapImpl().getLastOrderIdReturn();
    }



}
