package lk.crossorigin.agency.bo.custom.impl;

import lk.crossorigin.agency.bo.custom.ItemBO;
import lk.crossorigin.agency.bo.custom.MainItemBO;
import lk.crossorigin.agency.dao.DAOFactory;
import lk.crossorigin.agency.dao.custom.ItemDAO;
import lk.crossorigin.agency.dao.custom.MainItemDAO;
import lk.crossorigin.agency.dto.ItemDTO;
import lk.crossorigin.agency.dto.MainItemDTO;
import lk.crossorigin.agency.entity.Item;
import lk.crossorigin.agency.entity.MainItem;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainItemBoImpl implements MainItemBO {

    MainItemDAO itemDAO = DAOFactory.getInstance().getDao(DAOFactory.DaoType.MainITEM);
    public boolean saveItem(MainItemDTO dto) throws ClassNotFoundException, SQLException {
        return itemDAO.saveItem(new MainItem(dto.getCode(),dto.getName(), dto.getUnitPrice_Box_Agency(),dto.getUnitPrice_Box(), dto.getItemCountInBox(), dto.getBoxQty(),dto.getItemQty()));
    }
    public boolean deleteItem(String code) throws ClassNotFoundException, SQLException {
        return itemDAO.deleteItem(code);
    }
    public boolean updateItem(MainItemDTO dto) throws ClassNotFoundException, SQLException {
        return itemDAO.updateItem(new MainItem(dto.getCode(),dto.getName(), dto.getUnitPrice_Box_Agency(),dto.getUnitPrice_Box(),dto.getItemCountInBox(),dto.getBoxQty(),dto.getItemQty()));
    }
    public boolean updateItemQtysReduce(MainItemDTO dto) throws ClassNotFoundException, SQLException {
        return itemDAO.updateItemQtysReduce(new MainItem(dto.getCode(),dto.getBoxQty(),dto.getItemQty()));
    }

    public boolean updateItemQtysIncrease(MainItemDTO dto) throws ClassNotFoundException, SQLException {
        return itemDAO.updateItemQtysIncrease(new MainItem(dto.getCode(),dto.getBoxQty(),dto.getItemQty()));
    }
    public MainItemDTO getItem(String id) throws SQLException, ClassNotFoundException {
        MainItem mainItem = itemDAO.getItem(id);
        if(mainItem != null){
            return new MainItemDTO(
                    mainItem.getCode(),
                    mainItem.getName(),
                    mainItem.getUnitPrice_Box_Agency(),
                    mainItem.getUnitPrice_Box(),
                    mainItem.getItemCountInBox(),
                    mainItem.getBoxQty(),
                    mainItem.getItemQty()
            );
        }
        return null;
    }
    public MainItemDTO getItemByName(String name) throws SQLException, ClassNotFoundException {
        MainItem mainItem = itemDAO.getItem(name);
        if(mainItem != null){
            return new MainItemDTO(
                    mainItem.getCode(),
                    mainItem.getName(),
                    mainItem.getUnitPrice_Box_Agency(),
                    mainItem.getUnitPrice_Box(),
                    mainItem.getItemCountInBox(),
                    mainItem.getBoxQty(),
                    mainItem.getItemQty()
            );
        }
        return null;
    }
    public ArrayList<MainItemDTO> getAllItems(String text) throws ClassNotFoundException, SQLException {
        ArrayList<MainItemDTO> dtoList = new ArrayList<>();
        ArrayList<MainItem> entityList= itemDAO.getAllItems(text);
        for (MainItem s:entityList) {
            MainItemDTO mainItemDTO = new MainItemDTO(
                    s.getCode(),
                    s.getName(),
                    s.getUnitPrice_Box_Agency(),
                    s.getUnitPrice_Box(),
                    s.getItemCountInBox(),
                    s.getBoxQty(),
                    s.getItemQty()
            );
            dtoList.add(mainItemDTO);
        }
        return dtoList;
    }


}
