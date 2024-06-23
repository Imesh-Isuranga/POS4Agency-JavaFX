package lk.crossorigin.agency.dao.custom;

import lk.crossorigin.agency.entity.Item;
import lk.crossorigin.agency.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentDAO {
    public boolean savePayment(Payment p) throws SQLException, ClassNotFoundException;
    public ArrayList<Payment> getPaymentByOrderId(String orderId) throws SQLException, ClassNotFoundException;
    public ArrayList<Payment> getAllPayments(String text) throws SQLException, ClassNotFoundException;
}
