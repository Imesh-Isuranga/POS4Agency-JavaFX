package lk.crossorigin.agency;

import lk.crossorigin.agency.dao.custom.impl.ItemDaoImpl;
import lk.crossorigin.agency.dao.custom.impl.ShopDaoImpl;
import lk.crossorigin.agency.db.DBConnection;
import lk.crossorigin.agency.dto.ItemDTO;
import lk.crossorigin.agency.dto.ShopDTO;
import lk.crossorigin.agency.entity.Item;
import lk.crossorigin.agency.entity.Shop;

import java.sql.*;
import java.util.ArrayList;

public class DataBaseAccessCode {

    //Shop Management
    public boolean saveShop(ShopDTO dto) throws ClassNotFoundException, SQLException {
        return new ShopDaoImpl().saveShop(new Shop(dto.getId(),dto.getName(),dto.getAddress()));
    }

    public boolean updateShop(ShopDTO dto) throws ClassNotFoundException, SQLException {
        return new ShopDaoImpl().updateShop(new Shop(dto.getId(),dto.getName(),dto.getAddress()));
    }

    public boolean deleteShop(String id) throws ClassNotFoundException, SQLException {
        return new ShopDaoImpl().deleteShop(id);
    }

    /*public boolean getShop(String id){
        //get Shop
    }*/
    public ArrayList<ShopDTO> getAllShops(String text) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM Shop WHERE id LIKE ? OR name LIKE ? OR address LIKE ?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setObject(1,text);
        stm.setObject(2,text);
        stm.setObject(3,text);

        ResultSet rst = stm.executeQuery();
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



    public ArrayList<ItemDTO> getAllItems(String text) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM Item WHERE code LIKE ? OR name LIKE ? OR unitPrice LIKE ? OR qty LIKE ?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setObject(1,text);
        stm.setObject(2,text);
        stm.setObject(3,text);
        stm.setObject(4,text);

        ResultSet rst = stm.executeQuery();
        ArrayList<ItemDTO> dtoList = new ArrayList<>();
        ArrayList<Item> entityList= new ItemDaoImpl().getAllItems(text);
        for (Item i:entityList) {
            ItemDTO itemDTO = new ItemDTO(
                    i.getCode(),
                    i.getName(),
                    i.getUnitPrice(),
                    i.getQty()
            );
            dtoList.add(itemDTO);
        }
        return dtoList;
    }

    public boolean deleteItem(String code) throws ClassNotFoundException, SQLException {
        return new ItemDaoImpl().deleteItem(code);
    }

    public boolean updateItem(ItemDTO dto) throws ClassNotFoundException, SQLException {
        return new ItemDaoImpl().updateItem(new Item(dto.getCode(),dto.getQty()));
    }
}
