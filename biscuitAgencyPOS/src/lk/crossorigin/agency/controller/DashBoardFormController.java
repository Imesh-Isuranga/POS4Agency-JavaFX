package lk.crossorigin.agency.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class DashBoardFormController {
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
