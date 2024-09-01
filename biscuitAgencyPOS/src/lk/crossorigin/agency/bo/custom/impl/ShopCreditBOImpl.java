package lk.crossorigin.agency.bo.custom.impl;

import lk.crossorigin.agency.bo.custom.ShopCreditBO;
import lk.crossorigin.agency.dao.DAOFactory;
import lk.crossorigin.agency.dao.custom.ShopCreditDAO;
import lk.crossorigin.agency.dao.custom.ShopDAO;
import lk.crossorigin.agency.dto.ShopCreditDTO;
import lk.crossorigin.agency.dto.ShopDTO;
import lk.crossorigin.agency.entity.Shop;
import lk.crossorigin.agency.entity.ShopCredit;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class ShopCreditBOImpl implements ShopCreditBO {

    ShopCreditDAO shopCreditDAO = DAOFactory.getInstance().getDao(DAOFactory.DaoType.ShopCredit);
    @Override
    public boolean saveCredit(ShopCreditDTO dto) throws ClassNotFoundException, SQLException {
        return shopCreditDAO.saveCredit(new ShopCredit(dto.getShopId(),dto.getDate_paid(),dto.getAmount(),dto.getPaymentDetails()));
    }

    @Override
    public boolean deleteCreditById(int id) throws ClassNotFoundException, SQLException {
        return shopCreditDAO.deleteCreditById(id);
    }

    @Override
    public boolean deleteCreditByshop(String shopId) throws ClassNotFoundException, SQLException {
        return shopCreditDAO.deleteCreditByshop(shopId);
    }

    @Override
    public boolean updateCredit(ShopCreditDTO dto) throws ClassNotFoundException, SQLException {
        return shopCreditDAO.updateCredit(new ShopCredit(dto.getShopId(),dto.getDate_paid(),dto.getAmount(),dto.getPaymentDetails()));
    }

    @Override
    public ArrayList<ShopCreditDTO> getItemByShopId(String shopId) throws SQLException, ClassNotFoundException {
        ArrayList<ShopCreditDTO> dtoList = new ArrayList<>();
        ArrayList<ShopCredit> entityList= shopCreditDAO.getItemByShopId(shopId);
        for (ShopCredit s:entityList) {
            ShopCreditDTO shopCreditDTO = new ShopCreditDTO(
                    s.getId(),
                    s.getShopId(),
                    s.getDate_paid(),
                    s.getAmount(),
                    s.getPaymentDetails()
            );
            dtoList.add(shopCreditDTO);
        }
        return dtoList;
    }

    @Override
    public ArrayList<ShopCreditDTO> getItemByAmount(double amount) throws SQLException, ClassNotFoundException {
        ArrayList<ShopCreditDTO> dtoList = new ArrayList<>();
        ArrayList<ShopCredit> entityList= shopCreditDAO.getItemByAmount(amount);
        for (ShopCredit s:entityList) {
            ShopCreditDTO shopCreditDTO = new ShopCreditDTO(
                    s.getId(),
                    s.getShopId(),
                    s.getDate_paid(),
                    s.getAmount(),
                    s.getPaymentDetails()
            );
            dtoList.add(shopCreditDTO);
        }
        return dtoList;
    }

    @Override
    public ArrayList<ShopCreditDTO> getItemByDate(Date date) throws SQLException, ClassNotFoundException {
        ArrayList<ShopCreditDTO> dtoList = new ArrayList<>();
        ArrayList<ShopCredit> entityList= shopCreditDAO.getItemByDate(date);
        for (ShopCredit s:entityList) {
            ShopCreditDTO shopCreditDTO = new ShopCreditDTO(
                    s.getId(),
                    s.getShopId(),
                    s.getDate_paid(),
                    s.getAmount(),
                    s.getPaymentDetails()
            );
            dtoList.add(shopCreditDTO);
        }
        return dtoList;
    }

    @Override
    public ArrayList<ShopCreditDTO> getAllCreditsShops(String text) throws ClassNotFoundException, SQLException {
        ArrayList<ShopCreditDTO> dtoList = new ArrayList<>();
        ArrayList<ShopCredit> entityList= shopCreditDAO.getAllCreditsShops(text);
        for (ShopCredit s:entityList) {
            ShopCreditDTO shopCreditDTO = new ShopCreditDTO(
                    s.getId(),
                    s.getShopId(),
                    s.getDate_paid(),
                    s.getAmount(),
                    s.getPaymentDetails()
            );
            dtoList.add(shopCreditDTO);
        }
        return dtoList;
    }
}
