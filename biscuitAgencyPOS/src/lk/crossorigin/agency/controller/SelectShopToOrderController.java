package lk.crossorigin.agency.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.crossorigin.agency.bo.custom.OrderBookBO;
import lk.crossorigin.agency.bo.custom.ShopBO;
import lk.crossorigin.agency.bo.custom.impl.OrderBookBoImpl;
import lk.crossorigin.agency.bo.custom.impl.ShopBoImpl;
import lk.crossorigin.agency.dto.OrderBookDTO;
import lk.crossorigin.agency.dto.ShopDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class SelectShopToOrderController {
    public JFXButton btnBack;
    public AnchorPane selectShopToOrderContext;
    public JFXComboBox shopIdcmb;
    public TextField bookNumtxt;
    public TextField invoiceNumtxt;
    public JFXButton btnNext;

    ShopBO shopBO = new ShopBoImpl();
    OrderBookBO orderBookBO = new OrderBookBoImpl();

    public void initialize() throws SQLException, ClassNotFoundException {
        shopIdcmb.setItems(loadAllShopIds());
    }

    private ObservableList<String> loadAllShopIds() throws SQLException, ClassNotFoundException {
        ObservableList<String> shopIdsObList = FXCollections.observableArrayList();;
        ArrayList<ShopDTO> shopDTOArrayList = shopBO.getAllShops("%"+""+"%");

        for (ShopDTO shopDTO:shopDTOArrayList) {
            shopIdsObList.add(shopDTO.getId());
        }

        return shopIdsObList;
    }

    public void nextOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        OrderBookDTO orderBookDTO = new OrderBookDTO(orderBookBO.generateOrderId(bookNumtxt.getText(),invoiceNumtxt.getText()), bookNumtxt.getText(),invoiceNumtxt.getText(),shopIdcmb.getValue().toString());
        if(orderBookBO.saveOrderBook(orderBookDTO)){
            Stage stage = (Stage) selectShopToOrderContext.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/AddOrderForm.fxml"))));
        }else{
            new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.", ButtonType.OK).show();
        }
    }



    public void backbtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) selectShopToOrderContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
    }
}
