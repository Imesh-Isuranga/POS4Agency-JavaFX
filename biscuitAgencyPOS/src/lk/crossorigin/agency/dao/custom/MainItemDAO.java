package lk.crossorigin.agency.dao.custom;

import lk.crossorigin.agency.entity.Item;
import lk.crossorigin.agency.entity.MainItem;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MainItemDAO {

    public boolean saveItem(MainItem s) throws SQLException, ClassNotFoundException;
    public boolean updateItem(MainItem i) throws SQLException, ClassNotFoundException;
    public boolean updateItemQtysReduce(MainItem i) throws SQLException, ClassNotFoundException;
    public boolean updateItemQtysIncrease(MainItem i) throws SQLException, ClassNotFoundException;
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException;
    public MainItem getItem(String id) throws SQLException, ClassNotFoundException;
    public MainItem getItemByName(String name) throws SQLException, ClassNotFoundException;
    public ArrayList<MainItem> getAllItems(String text) throws SQLException, ClassNotFoundException;

}
