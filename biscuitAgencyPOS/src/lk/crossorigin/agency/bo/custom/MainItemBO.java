package lk.crossorigin.agency.bo.custom;

import lk.crossorigin.agency.dto.ItemDTO;
import lk.crossorigin.agency.dto.MainItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MainItemBO extends SuperBO{
    public boolean saveItem(MainItemDTO dto) throws ClassNotFoundException, SQLException ;
    public boolean deleteItem(String code) throws ClassNotFoundException, SQLException ;
    public boolean updateItem(MainItemDTO dto) throws ClassNotFoundException, SQLException ;
    public boolean updateItemQtys(MainItemDTO dto) throws ClassNotFoundException, SQLException ;
    public MainItemDTO getItem(String id) throws SQLException, ClassNotFoundException ;
    public MainItemDTO getItemByName(String name) throws SQLException, ClassNotFoundException ;
    public ArrayList<MainItemDTO> getAllItems(String text) throws ClassNotFoundException, SQLException ;

}
