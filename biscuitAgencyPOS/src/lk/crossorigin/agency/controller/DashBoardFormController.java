package lk.crossorigin.agency.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.crossorigin.agency.DataBaseAccessCode;

import java.io.IOException;
import java.sql.SQLException;

public class DashBoardFormController {

    public void initialize() throws SQLException, ClassNotFoundException {
        checkTableData();
    }

    private void checkTableData(){
        try {
            String last_order_orderId = new DataBaseAccessCode().getLastOrderIdOrder();
            String last_discount_orderId = new DataBaseAccessCode().getLastDiscountId();
            if(!(last_order_orderId.equals(last_discount_orderId))){
                new DataBaseAccessCode().deleteDiscount(last_discount_orderId);
            }

            String last_orderBook_orderId = new DataBaseAccessCode().getLastOrderId();
            if(!(last_order_orderId.equals(last_orderBook_orderId))){
                new DataBaseAccessCode().deleteOrderBook(last_orderBook_orderId);
            }

            String last_return_orderId = new DataBaseAccessCode().getLastOrderIdReturn();
            if(!(last_order_orderId.equals(last_return_orderId))){
                new DataBaseAccessCode().deleteReturn(last_return_orderId);
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
