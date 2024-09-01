package lk.crossorigin.agency.bo.custom;

import lk.crossorigin.agency.dto.SaveListDTO;
import lk.crossorigin.agency.dto.ShopDTO;
import lk.crossorigin.agency.entity.SaveList;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SaveListBO extends SuperBO{
    public boolean saveList(SaveListDTO s) throws SQLException, ClassNotFoundException;
    public boolean deleteList(int id) throws SQLException, ClassNotFoundException;
    public ArrayList<SaveListDTO> getList(String text) throws SQLException, ClassNotFoundException ;
    public String getLastSavedId() throws SQLException, ClassNotFoundException ;

}
