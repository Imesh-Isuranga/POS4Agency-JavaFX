package lk.crossorigin.agency.dao.custom.impl;

import lk.crossorigin.agency.dao.CrudUtil;
import lk.crossorigin.agency.dao.custom.SaveListDAO;
import lk.crossorigin.agency.dao.custom.ShopDAO;
import lk.crossorigin.agency.entity.Item;
import lk.crossorigin.agency.entity.SaveList;
import lk.crossorigin.agency.entity.Shop;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SaveListDaoImpl implements SaveListDAO {
    @Override
    public boolean saveList(SaveList s) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO SaveList VALUES(?,?)";
        return CrudUtil.executeUpdate(sql,s.getId(),s.getDescription());
    }

    @Override
    public boolean deleteList(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM SaveList WHERE id=?";
        return CrudUtil.executeUpdate(sql,id);
    }

    @Override
    public ArrayList<SaveList> getList(String text) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM SaveList WHERE description LIKE ?";

        ResultSet rst = CrudUtil.executeQuery(sql,text);
        ArrayList<SaveList> entityList = new ArrayList<>();
        while (rst.next()) {
            SaveList saveList = new SaveList(
                    rst.getInt(1),
                    rst.getString(2)
            );
            entityList.add(saveList);
        }
        return entityList;
    }

    @Override
    public String getLastSavedId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT id FROM SaveList ORDER BY id DESC LIMIT 1";
        ResultSet rst = CrudUtil.executeQuery(sql);

        if (rst.next()) {
            return rst.getString(1);
        } else {
            return "1";
        }
    }
}
