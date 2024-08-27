package lk.crossorigin.agency.bo.custom.impl;

import lk.crossorigin.agency.bo.custom.ItemBO;
import lk.crossorigin.agency.dao.DAOFactory;
import lk.crossorigin.agency.dao.custom.ItemDAO;
import lk.crossorigin.agency.dto.ItemDTO;
import lk.crossorigin.agency.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBoImpl implements ItemBO {

    ItemDAO itemDAO = DAOFactory.getInstance().getDao(DAOFactory.DaoType.ITEM);
    public boolean saveItem(ItemDTO dto) throws ClassNotFoundException, SQLException {
        return itemDAO.saveItem(new Item(dto.getCode(),dto.getName(), dto.getUnitPrice_Box_Agency(),dto.getUnitPrice_Box(), dto.getItemCountInBox(), dto.getBoxQty(),dto.getItemQty()));
    }
    public boolean deleteItem(String code) throws ClassNotFoundException, SQLException {
        return itemDAO.deleteItem(code);
    }
    public boolean updateItem(ItemDTO dto) throws ClassNotFoundException, SQLException {
        return itemDAO.updateItem(new Item(dto.getCode(),dto.getName(), dto.getUnitPrice_Box_Agency(),dto.getUnitPrice_Box(),dto.getItemCountInBox(),dto.getBoxQty(),dto.getItemQty()));
    }
    public boolean updateItemQtyDecrease(ItemDTO dto) throws ClassNotFoundException, SQLException {
        return itemDAO.updateItemQtyDecrease(new Item(dto.getCode(),dto.getBoxQty(),dto.getItemQty()));
    }

    public boolean updateItemQtysIncrease(ItemDTO dto) throws ClassNotFoundException, SQLException {
        return itemDAO.updateItemQtysIncrease(new Item(dto.getCode(),dto.getBoxQty(),dto.getItemQty()));
    }
    public ItemDTO getItem(String id) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.getItem(id);
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
        Item item = itemDAO.getItem(name);
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
        ArrayList<Item> entityList= itemDAO.getAllItems(text);
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


}
