package lk.crossorigin.agency.bo.custom.impl;

import lk.crossorigin.agency.bo.custom.SaveListBO;
import lk.crossorigin.agency.dao.DAOFactory;
import lk.crossorigin.agency.dao.custom.OrderDAO;
import lk.crossorigin.agency.dao.custom.SaveListDAO;
import lk.crossorigin.agency.dto.ItemDTO;
import lk.crossorigin.agency.dto.SaveListDTO;
import lk.crossorigin.agency.entity.Item;
import lk.crossorigin.agency.entity.Order;
import lk.crossorigin.agency.entity.SaveList;

import java.sql.SQLException;
import java.util.ArrayList;

public class SavelistBoImpl implements SaveListBO {

    SaveListDAO saveListDAO = DAOFactory.getInstance().getDao(DAOFactory.DaoType.SAVELIST);
    @Override
    public boolean saveList(SaveListDTO s) throws SQLException, ClassNotFoundException {
        return saveListDAO.saveList(new SaveList(s.getId(),s.getDescription()));
    }

    @Override
    public boolean deleteList(int id) throws SQLException, ClassNotFoundException {
        return saveListDAO.deleteList(id);
    }

    @Override
    public ArrayList<SaveListDTO> getList(String text) throws SQLException, ClassNotFoundException {
        ArrayList<SaveListDTO> dtoList = new ArrayList<>();
        ArrayList<SaveList> entityList= saveListDAO.getList(text);
        for (SaveList s:entityList) {
            SaveListDTO saveListDTO = new SaveListDTO(
                    s.getId(),
                    s.getDescription()
            );
            dtoList.add(saveListDTO);
        }
        return dtoList;
    }

    public String getLastSavedId() throws SQLException, ClassNotFoundException {
        return saveListDAO.getLastSavedId();
    }
}
