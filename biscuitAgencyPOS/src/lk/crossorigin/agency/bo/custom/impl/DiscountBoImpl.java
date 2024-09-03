package lk.crossorigin.agency.bo.custom.impl;

import lk.crossorigin.agency.bo.custom.DiscountBO;
import lk.crossorigin.agency.dao.DAOFactory;
import lk.crossorigin.agency.dao.custom.DiscountDAO;
import lk.crossorigin.agency.dto.DiscountDTO;
import lk.crossorigin.agency.entity.Discount;

import java.sql.SQLException;
import java.util.ArrayList;

public class DiscountBoImpl implements DiscountBO {

    DiscountDAO discountDAO = DAOFactory.getInstance().getDao(DAOFactory.DaoType.DISCOUNT);

    public boolean saveDiscount(DiscountDTO d) throws SQLException, ClassNotFoundException {
        return discountDAO.saveDiscount(new Discount(d.getIdDup(),d.getOrderId(),d.getItemCode(),d.getDiscountValue()));
    }
    public boolean updateDiscount(DiscountDTO d) throws SQLException, ClassNotFoundException {
        return discountDAO.updateDiscount(new Discount(d.getIdDup(),d.getOrderId(),d.getItemCode(),d.getDiscountValue()));
    }
    public boolean deleteDiscount(String orderId) throws SQLException, ClassNotFoundException {
        return discountDAO.deleteDiscount(orderId);
    }

    public boolean deleteDiscountByIdDup(int idDup) throws SQLException, ClassNotFoundException {
        return discountDAO.deleteDiscountByIdDup(idDup);
    }
    public DiscountDTO getItemByOrderId(String disId) throws SQLException, ClassNotFoundException {
        Discount discount = discountDAO.getItemByOrderId(disId);
        if(discount != null){
            return new DiscountDTO(discount.getIdDup(),discount.getOrderId(),discount.getItemCode(),discount.getDiscountValue());
        }
        return null;
    }
    public ArrayList<DiscountDTO> getAllDiscountByIdDup(String orderId, String orderIdDup) throws SQLException, ClassNotFoundException {
        ArrayList<DiscountDTO> dtoList = new ArrayList<>();
        ArrayList<Discount> entityList= discountDAO.getAllDiscountByIdDup(orderId,orderIdDup);
        for (Discount d:entityList) {
            DiscountDTO discountDTO = new DiscountDTO(
                    d.getIdDup(),
                    d.getOrderId(),
                    d.getItemCode(),
                    d.getDiscountValue()
            );
            dtoList.add(discountDTO);
        }
        return dtoList;
    }
    public ArrayList<DiscountDTO> getAllDiscountByOrderId(String orderId) throws SQLException, ClassNotFoundException{
        ArrayList<DiscountDTO> dtoList = new ArrayList<>();
        ArrayList<Discount> entityList= discountDAO.getAllDiscountByOrderId(orderId);
        for (Discount d:entityList) {
            DiscountDTO discountDTO = new DiscountDTO(
                    d.getIdDup(),
                    d.getOrderId(),
                    d.getItemCode(),
                    d.getDiscountValue()
            );
            dtoList.add(discountDTO);
        }
        return dtoList;
    }
    public ArrayList<DiscountDTO> getAllDiscount(String text) throws SQLException, ClassNotFoundException {
        ArrayList<DiscountDTO> dtoList = new ArrayList<>();
        ArrayList<Discount> entityList= discountDAO.getAllDiscount(text);
        for (Discount d:entityList) {
            DiscountDTO discountDTO = new DiscountDTO(
                    d.getIdDup(),
                    d.getOrderId(),
                    d.getItemCode(),
                    d.getDiscountValue()
            );
            dtoList.add(discountDTO);
        }
        return dtoList;
    }
    public String getLastDiscountId() throws SQLException, ClassNotFoundException {
        return discountDAO.getLastDiscountId();
    }

}
