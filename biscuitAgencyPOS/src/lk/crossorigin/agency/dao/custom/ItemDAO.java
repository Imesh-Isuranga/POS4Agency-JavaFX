package lk.crossorigin.agency.dao.custom;

import lk.crossorigin.agency.dto.ItemDTO;
import lk.crossorigin.agency.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO {

    public ArrayList<Item> getAllItems(String text) throws SQLException, ClassNotFoundException;
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException;
    public boolean updateItem(Item i) throws SQLException, ClassNotFoundException;
}
