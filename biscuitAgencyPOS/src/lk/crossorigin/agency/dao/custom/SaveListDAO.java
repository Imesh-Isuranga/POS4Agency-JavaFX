package lk.crossorigin.agency.dao.custom;

import lk.crossorigin.agency.entity.SaveList;
import lk.crossorigin.agency.entity.Shop;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SaveListDAO {
    public boolean saveList(SaveList s) throws SQLException, ClassNotFoundException;
    public boolean deleteList(int id) throws SQLException, ClassNotFoundException;
    public ArrayList<SaveList> getList(String text) throws SQLException, ClassNotFoundException ;
    public String getLastSavedId() throws SQLException, ClassNotFoundException ;

}
