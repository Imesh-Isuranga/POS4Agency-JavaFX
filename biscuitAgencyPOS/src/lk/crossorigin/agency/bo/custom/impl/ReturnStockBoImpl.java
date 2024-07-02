package lk.crossorigin.agency.bo.custom.impl;

import lk.crossorigin.agency.bo.custom.ReturnStockBO;
import lk.crossorigin.agency.dao.DAOFactory;
import lk.crossorigin.agency.dao.custom.impl.ReturnDapImpl;
import lk.crossorigin.agency.dto.ReturnStockDTO;
import lk.crossorigin.agency.entity.ReturnStock;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnStockBoImpl implements ReturnStockBO {

    ReturnDapImpl returnDao = DAOFactory.getInstance().getDao(DAOFactory.DaoType.RETURN);

    public boolean saveReturn(ReturnStockDTO dto) throws SQLException, ClassNotFoundException{
        return returnDao.saveReturn(
                new ReturnStock(
                        dto.getId(),
                        dto.getOrderId(),
                        dto.getItemCode(),
                        dto.getBoxQty()
                        ,dto.getItemQty()
                )
        );

    }
    public boolean updateReturn(ReturnStockDTO dto) throws SQLException, ClassNotFoundException{
        return returnDao.updateReturn(
                new ReturnStock(
                        dto.getId(),
                        dto.getOrderId(),
                        dto.getItemCode(),
                        dto.getBoxQty(),
                        dto.getItemQty()
                )
        );

    }
    public boolean deleteReturn(String orderId) throws SQLException, ClassNotFoundException{
        return returnDao.deleteReturn(orderId);

    }
    public ReturnStockDTO getReturn(String orderId,String itemCode) throws SQLException, ClassNotFoundException{
        ReturnStock returnStock = returnDao.getReturn(orderId,itemCode);
        if(returnStock != null){
            return new ReturnStockDTO(
                    returnStock.getId(),
                    returnStock.getOrderId(),
                    returnStock.getItemCode(),
                    returnStock.getBoxQty(),
                    returnStock.getItemQty()
            );
        }
        return null;
    }
    public ArrayList<ReturnStockDTO> getReturnByOrderId(String text) throws SQLException, ClassNotFoundException{
        ArrayList<ReturnStockDTO> dtoList = new ArrayList<>();
        ArrayList<ReturnStock> entityList= returnDao.getReturnByOrderId(text);
        for (ReturnStock returnStock:entityList) {
            ReturnStockDTO returnStockDTO = new ReturnStockDTO(
                    returnStock.getId(),
                    returnStock.getOrderId(),
                    returnStock.getItemCode(),
                    returnStock.getBoxQty(),
                    returnStock.getItemQty()
            );
            dtoList.add(returnStockDTO);
        }
        return dtoList;
    }
    public String getLastOrderIdReturn() throws SQLException, ClassNotFoundException {
        return returnDao.getLastOrderIdReturn();
    }

}
