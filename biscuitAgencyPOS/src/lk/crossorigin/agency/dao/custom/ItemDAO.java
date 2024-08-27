package lk.crossorigin.agency.dao.custom;

import lk.crossorigin.agency.dto.ItemDTO;
import lk.crossorigin.agency.entity.Item;
import lk.crossorigin.agency.entity.Shop;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO {

    public boolean saveItem(Item s) throws SQLException, ClassNotFoundException;
    public boolean updateItem(Item i) throws SQLException, ClassNotFoundException;
    public boolean updateItemQtyDecrease(Item i) throws SQLException, ClassNotFoundException;
    public boolean updateItemQtysIncrease(Item i) throws SQLException, ClassNotFoundException;
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException;
    public Item getItem(String id) throws SQLException, ClassNotFoundException;
    public Item getItemByName(String name) throws SQLException, ClassNotFoundException;
    public ArrayList<Item> getAllItems(String text) throws SQLException, ClassNotFoundException;

}
