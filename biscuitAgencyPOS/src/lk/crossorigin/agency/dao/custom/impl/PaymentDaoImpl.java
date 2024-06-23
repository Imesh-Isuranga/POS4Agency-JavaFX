package lk.crossorigin.agency.dao.custom.impl;

import lk.crossorigin.agency.dao.CrudUtil;
import lk.crossorigin.agency.dao.custom.PaymentDAO;
import lk.crossorigin.agency.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDaoImpl implements PaymentDAO {
    @Override
    public boolean savePayment(Payment p) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Payment VALUES(?,?,?,?,?)";
        return CrudUtil.executeUpdate(sql,p.getId(),p.getOrderId(),p.getPayment_Details(),p.getPayment_Way(),p.getAmount());
    }

    @Override
    public ArrayList<Payment> getPaymentByOrderId(String orderId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Payment WHERE orderId=?";
        ResultSet rst = CrudUtil.executeQuery(sql,orderId);
        ArrayList<Payment> entityList = new ArrayList<>();
        while (rst.next()) {
            Payment payment = new Payment(
                    rst.getInt(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5)
            );
            entityList.add(payment);
        }
        return entityList;
    }

    @Override
    public ArrayList<Payment> getAllPayments(String text) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Payment WHERE id LIKE ? OR orderId LIKE ? OR paymentDetails LIKE ? OR paymentWay LIKE ? OR amount LIKE ?";

        ResultSet rst = CrudUtil.executeQuery(sql,text,text,text,text,text);
        ArrayList<Payment> entityList = new ArrayList<>();
        while (rst.next()) {
            Payment payment = new Payment(
                    rst.getInt(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5)
            );
            entityList.add(payment);
        }
        return entityList;
    }
}
