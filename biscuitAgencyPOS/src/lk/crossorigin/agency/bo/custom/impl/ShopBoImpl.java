package lk.crossorigin.agency.bo.custom.impl;

import lk.crossorigin.agency.bo.custom.ShopBO;
import lk.crossorigin.agency.dao.DAOFactory;
import lk.crossorigin.agency.dao.custom.ShopDAO;
import lk.crossorigin.agency.dto.ShopDTO;
import lk.crossorigin.agency.entity.Shop;

import java.sql.SQLException;
import java.util.ArrayList;

public class ShopBoImpl implements ShopBO {

    ShopDAO shopDAO = DAOFactory.getInstance().getDao(DAOFactory.DaoType.SHOP);

    public boolean saveShop(ShopDTO dto) throws ClassNotFoundException, SQLException {
        return shopDAO.saveShop(new Shop(dto.getSh_id(),dto.getId(),dto.getName(),dto.getAddress(),dto.getCredit_uptoNow()));
    }
    public boolean updateShop(ShopDTO dto) throws ClassNotFoundException, SQLException {
        return shopDAO.updateShop(new Shop(dto.getSh_id(),dto.getId(),dto.getName(),dto.getAddress(),dto.getCredit_uptoNow()));
    }
    public boolean updateShopCredit(String id,double creditAmount) throws ClassNotFoundException, SQLException {
        return shopDAO.updateShopCredit(id,creditAmount);
    }
    public boolean updateShopWithoutCredit(ShopDTO dto) throws ClassNotFoundException, SQLException {
        return shopDAO.updateShopWithoutCredit(new Shop(dto.getSh_id(),dto.getId(),dto.getName(),dto.getAddress()));
    }
    public boolean deleteShop(String id) throws ClassNotFoundException, SQLException {
        return shopDAO.deleteShop(id);
    }
    public ShopDTO getShop(String id) throws SQLException, ClassNotFoundException {
        Shop shop = shopDAO.getShop(id);
        if(shop != null){
            return new ShopDTO(shop.getSh_id(),shop.getId(),shop.getName(),shop.getAddress(),shop.getCredit_uptoNow());
        }
        return null;
    }
    public ArrayList<ShopDTO> getAllShops(String text) throws ClassNotFoundException, SQLException {
        ArrayList<ShopDTO> dtoList = new ArrayList<>();
        ArrayList<Shop> entityList= shopDAO.getAllShops(text);
        for (Shop s:entityList) {
            ShopDTO shopDTO = new ShopDTO(
                    s.getSh_id(),
                    s.getId(),
                    s.getName(),
                    s.getAddress(),
                    s.getCredit_uptoNow()
            );
            dtoList.add(shopDTO);
        }
        return dtoList;
    }

    public ArrayList<ShopDTO> getAllShopsByAddress(String text) throws ClassNotFoundException, SQLException {
        ArrayList<ShopDTO> dtoList = new ArrayList<>();
        ArrayList<Shop> entityList= shopDAO.getAllShops(text);
        for (Shop s:entityList) {
            ShopDTO shopDTO = new ShopDTO(
                    s.getSh_id(),
                    s.getId(),
                    s.getName(),
                    s.getAddress(),
                    s.getCredit_uptoNow()
            );
            dtoList.add(shopDTO);
        }
        return dtoList;
    }

    public String generateShopId(String name,String address) throws SQLException, ClassNotFoundException {
        return shopDAO.generateShopId(name,address);
    }



}
