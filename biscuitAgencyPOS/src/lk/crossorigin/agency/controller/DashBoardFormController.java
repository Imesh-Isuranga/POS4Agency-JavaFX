package lk.crossorigin.agency.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.crossorigin.agency.bo.custom.*;
import lk.crossorigin.agency.bo.custom.impl.*;

import java.io.IOException;
import java.sql.SQLException;

public class DashBoardFormController {


    OrderBO orderBO = new OrderBoImpl();
    OrderBookBO orderBookBO = new OrderBookBoImpl();
    ReturnStockBO returnStockBO = new ReturnStockBoImpl();
    DiscountBO discountBO = new DiscountBoImpl();

    public void initialize() throws SQLException, ClassNotFoundException {
        checkTableData();
    }

    private void checkTableData(){
        try {
            String last_order_orderId = orderBO.getLastOrderIdOrder();
            String last_discount_orderId = discountBO.getLastDiscountId();
            if(!(last_order_orderId.equals(last_discount_orderId))){
                discountBO.deleteDiscount(last_discount_orderId);
            }

            String last_orderBook_orderId = orderBookBO.getLastOrderId();
            if(!(last_order_orderId.equals(last_orderBook_orderId))){
                orderBookBO.deleteOrderBook(last_orderBook_orderId);
            }

            String last_return_orderId = returnStockBO.getLastOrderIdReturn();
            if(!(last_order_orderId.equals(last_return_orderId))){
                returnStockBO.deleteReturn(last_return_orderId);
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

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) dashboardContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
    }
}
