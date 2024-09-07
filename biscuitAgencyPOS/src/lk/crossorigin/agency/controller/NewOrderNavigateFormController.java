package lk.crossorigin.agency.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class NewOrderNavigateFormController {
    public AnchorPane newOrderContext;
    public TextField txtInvoiceNum;
    public JFXButton btnCancel;
    public JFXButton btnNeworder;
    public JFXComboBox cmbItemCode;

    public void cancelbtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) newOrderContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/SelectShopToOrder.fxml"))));
    }

    public void saveOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) newOrderContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/AddOrderForm.fxml"))));
    }
}
