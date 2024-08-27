package lk.crossorigin.agency.bo.custom;

import lk.crossorigin.agency.dto.ItemDTO;
import lk.crossorigin.agency.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO{
    public boolean saveItem(ItemDTO dto) throws ClassNotFoundException, SQLException ;
    public boolean deleteItem(String code) throws ClassNotFoundException, SQLException ;
    public boolean updateItem(ItemDTO dto) throws ClassNotFoundException, SQLException ;
    public boolean updateItemQtyDecrease(ItemDTO dto) throws ClassNotFoundException, SQLException ;
    public boolean updateItemQtysIncrease(ItemDTO dto) throws ClassNotFoundException, SQLException ;
    public ItemDTO getItem(String id) throws SQLException, ClassNotFoundException ;
    public ItemDTO getItemByName(String name) throws SQLException, ClassNotFoundException ;
    public ArrayList<ItemDTO> getAllItems(String text) throws ClassNotFoundException, SQLException ;

}
