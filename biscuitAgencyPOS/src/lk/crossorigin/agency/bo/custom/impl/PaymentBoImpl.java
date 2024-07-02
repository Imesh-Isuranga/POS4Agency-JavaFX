package lk.crossorigin.agency.bo.custom.impl;

import lk.crossorigin.agency.bo.custom.PaymentBO;
import lk.crossorigin.agency.dao.DAOFactory;
import lk.crossorigin.agency.dao.custom.PaymentDAO;
import lk.crossorigin.agency.dto.PaymentDTO;
import lk.crossorigin.agency.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentBoImpl implements PaymentBO {

    PaymentDAO paymentDAO = DAOFactory.getInstance().getDao(DAOFactory.DaoType.PAYMENT);

    public boolean savePayment(PaymentDTO p) throws SQLException, ClassNotFoundException{
        return paymentDAO.savePayment(new Payment(p.getId(),p.getOrderId(),p.getPayment_Details(),p.getPayment_Way(),p.getAmount()));
    }
    public ArrayList<PaymentDTO> getPaymentByOrderId(String orderId) throws SQLException, ClassNotFoundException{
        ArrayList<PaymentDTO> dtoList = new ArrayList<>();
        ArrayList<Payment> entityList= paymentDAO.getPaymentByOrderId(orderId);
        for (Payment p:entityList) {
            PaymentDTO paymentDTO = new PaymentDTO(
                    p.getId(),
                    p.getOrderId(),
                    p.getPayment_Details(),
                    p.getPayment_Way(),
                    p.getAmount()
            );
            dtoList.add(paymentDTO);
        }
        return dtoList;
    }
    public ArrayList<PaymentDTO> getAllPayments(String text) throws SQLException, ClassNotFoundException{
        ArrayList<PaymentDTO> dtoList = new ArrayList<>();
        ArrayList<Payment> entityList= paymentDAO.getAllPayments(text);
        for (Payment p:entityList) {
            PaymentDTO paymentDTO = new PaymentDTO(
                    p.getId(),
                    p.getOrderId(),
                    p.getPayment_Details(),
                    p.getPayment_Way(),
                    p.getAmount()
            );
            dtoList.add(paymentDTO);
        }
        return dtoList;
    }
}
