package lk.crossorigin.agency.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.crossorigin.agency.bo.custom.*;
import lk.crossorigin.agency.bo.custom.impl.*;
import lk.crossorigin.agency.dto.OrderDetailsDTO;

import java.io.IOException;
import java.sql.SQLException;

public class DashBoardFormController {


    public JFXButton btnMonthlyReport;
    public JFXButton btnReturnStock;
    public JFXButton btnShopsCredit;
    public JFXButton btnSave;
    OrderBO orderBO = new OrderBoImpl();
    OrderBookBO orderBookBO = new OrderBookBoImpl();
    ReturnStockBO returnStockBO = new ReturnStockBoImpl();
    DiscountBO discountBO = new DiscountBoImpl();
    OrderDetailBO orderDetailBO = new OrderDetailBoImpl();
    ItemBO itemBO = new ItemBoImpl();

    public void initialize() throws SQLException, ClassNotFoundException {
        checkTableData();
    }

    private void checkTableData(){
        try {
            String last_order_orderId = orderBO.getLastOrderIdOrder();
            OrderDetailsDTO orderDetailsDTO = orderDetailBO.getAllOrderDetailsByOrderId(last_order_orderId).get(0);

            if(orderDetailsDTO.getDis_tot() != 0.00){
                String last_discount_orderId = discountBO.getLastDiscountId();
                if(!(last_order_orderId.equals(last_discount_orderId))){
                    discountBO.deleteDiscount(last_discount_orderId);
                }
            }


            String last_orderBook_orderId = orderBookBO.getLastOrderId();
            if(!(last_order_orderId.equals(last_orderBook_orderId))){
                orderBookBO.deleteOrderBook(last_orderBook_orderId);
            }

            if(orderDetailsDTO.getDis_tot() != 0.00){
                String last_return_orderId = returnStockBO.getLastOrderIdReturn();
                if(!(last_order_orderId.equals(last_return_orderId))){
                    returnStockBO.deleteReturn(last_return_orderId);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public AnchorPane dashboardContext;

    public void loadOrderDetailsForm(MouseEvent mouseEvent) throws IOException {
        setUi("OrderDetailsForm");
    }

    public void loadStockForm(MouseEvent mouseEvent) throws IOException {
        setUi("LoadStockForm");
    }

    public void loadAddOrderForm(MouseEvent mouseEvent) throws IOException {
        setUi("SelectShopToOrder");
    }

    public void loadAddShopForm(MouseEvent mouseEvent) throws IOException {
        setUi("AddShopForm");
    }

    public void monthlyReportOnAction(ActionEvent actionEvent) throws IOException {
        setUi("MonthlyReportForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) dashboardContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
    }

    public void returnStockOnAction(ActionEvent actionEvent) throws IOException {
        setUi("Returnitems");
    }

    public void returnShopCredit(ActionEvent actionEvent) throws IOException {
        setUi("Shopscredit");
    }

    public void saveOnAction(ActionEvent actionEvent) throws IOException {
        setUi("Save");
    }
}
