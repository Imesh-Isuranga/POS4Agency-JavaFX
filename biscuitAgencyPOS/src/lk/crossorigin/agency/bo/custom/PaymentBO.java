package lk.crossorigin.agency.bo.custom;

import lk.crossorigin.agency.dto.PaymentDTO;
import lk.crossorigin.agency.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBO extends SuperBO{
    public boolean savePayment(PaymentDTO p) throws SQLException, ClassNotFoundException;
    public ArrayList<PaymentDTO> getPaymentByOrderId(String orderId) throws SQLException, ClassNotFoundException;
    public ArrayList<PaymentDTO> getAllPayments(String text) throws SQLException, ClassNotFoundException;

}
