package lk.crossorigin.agency;

import lk.crossorigin.agency.dao.custom.impl.ItemDaoImpl;
import lk.crossorigin.agency.dao.custom.impl.OrderDaoImpl;
import lk.crossorigin.agency.dao.custom.impl.OrderDetailsDaoImpl;
import lk.crossorigin.agency.dao.custom.impl.ShopDaoImpl;
import lk.crossorigin.agency.db.DBConnection;
import lk.crossorigin.agency.dto.ItemDTO;
import lk.crossorigin.agency.dto.OrderDTO;
import lk.crossorigin.agency.dto.OrderDetailsDTO;
import lk.crossorigin.agency.dto.ShopDTO;
import lk.crossorigin.agency.entity.Item;
import lk.crossorigin.agency.entity.Order;
import lk.crossorigin.agency.entity.OrderDetail;
import lk.crossorigin.agency.entity.Shop;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class DataBaseAccessCode {

    //Shop Management----------------
    public boolean saveShop(ShopDTO dto) throws ClassNotFoundException, SQLException {
        return new ShopDaoImpl().saveShop(new Shop(dto.getId(),dto.getName(),dto.getAddress()));
    }

    public boolean updateShop(ShopDTO dto) throws ClassNotFoundException, SQLException {
        return new ShopDaoImpl().updateShop(new Shop(dto.getId(),dto.getName(),dto.getAddress()));
    }

    public boolean deleteShop(String id) throws ClassNotFoundException, SQLException {
        return new ShopDaoImpl().deleteShop(id);
    }

    public ShopDTO getShop(String id) throws SQLException, ClassNotFoundException {
        Shop shop = new ShopDaoImpl().getShop(id);
        if(shop != null){
            return new ShopDTO(shop.getId(),shop.getName(),shop.getAddress());
        }
        return null;
    }

    public ArrayList<ShopDTO> getAllShops(String text) throws ClassNotFoundException, SQLException {
        ArrayList<ShopDTO> dtoList = new ArrayList<>();
        ArrayList<Shop> entityList= new ShopDaoImpl().getAllShops(text);
        for (Shop s:entityList) {
            ShopDTO shopDTO = new ShopDTO(
                    s.getId(),
                    s.getName(),
                    s.getAddress()
            );
            dtoList.add(shopDTO);
        }
        return dtoList;
    }



    //Item Management----------------
    public boolean saveItem(ItemDTO dto) throws ClassNotFoundException, SQLException {
        return new ItemDaoImpl().saveItem(new Item(dto.getCode(),dto.getName(),dto.getUnitPrice(),dto.getQty()));
    }

    public boolean deleteItem(String code) throws ClassNotFoundException, SQLException {
        return new ItemDaoImpl().deleteItem(code);
    }

    public boolean updateItem(ItemDTO dto) throws ClassNotFoundException, SQLException {
        return new ItemDaoImpl().updateItem(new Item(dto.getCode(),dto.getQty()));
    }

    public ItemDTO getItem(String id) throws SQLException, ClassNotFoundException {
        Item item = new ItemDaoImpl().getItem(id);
        if(item != null){
            return new ItemDTO(item.getCode(),item.getName(),item.getUnitPrice(),item.getQty());
        }
        return null;
    }

    public ItemDTO getItemByName(String name) throws SQLException, ClassNotFoundException {
        Item item = new ItemDaoImpl().getItem(name);
        if(item != null){
            return new ItemDTO(item.getCode(),item.getName(),item.getUnitPrice(),item.getQty());
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
                    s.getUnitPrice(),
                    s.getQty()
            );
            dtoList.add(itemDTO);
        }
        return dtoList;
    }



    //Order Management----------------
    public boolean saveOrder(OrderDTO dto) throws ClassNotFoundException, SQLException {
        return new OrderDaoImpl().saveOrder(new Order(dto.getId(),dto.getDate()));
    }

    public boolean deleteOrder(String orderId) throws ClassNotFoundException, SQLException {
        return new OrderDaoImpl().deleteOrder(orderId);
    }

    public Date getOrderDate(String orderId) throws ClassNotFoundException, SQLException {
        return new OrderDaoImpl().getOrderDate(orderId);
    }

    public String getLastOrderId() throws ClassNotFoundException, SQLException {
        return new OrderDaoImpl().getLastOrderId();
    }



    //OrderDetails Management----------------
    public boolean saveOrderDetails(OrderDetailsDTO dto) throws ClassNotFoundException, SQLException {
        return new OrderDetailsDaoImpl().saveOrderDetails(new OrderDetail(dto.getOrderId(),dto.getShopId(),dto.getItemCode(),dto.getQty(),dto.getUnitPrice()));
    }

    public ArrayList<OrderDetailsDTO> getAllOrderDetails(String text) throws ClassNotFoundException, SQLException {
        ArrayList<OrderDetailsDTO> dtoList = new ArrayList<>();
        ArrayList<OrderDetail> entityList= new OrderDetailsDaoImpl().getAllOrderDetails(text);
        for (OrderDetail s:entityList) {
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(
                    s.getOrderId(),
                    s.getShopId(),
                    s.getItemCode(),
                    s.getQty(),
                    s.getUnitPrice()
            );
            dtoList.add(orderDetailsDTO);
        }
        return dtoList;
    }

}
