package lk.crossorigin.agency.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.crossorigin.agency.DataBaseAccessCode;
import lk.crossorigin.agency.dto.ShopDTO;
import lk.crossorigin.agency.view.tm.ShopTM;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class AddShopFormController {
    public AnchorPane addShopContext;
    public TableView<ShopTM> addShoptbl;
    public TableColumn colShopId;
    public TableColumn colShopName;
    public TableColumn colShopAddress;
    public TextField shopNameTxt;
    public TextField shopAddressTxt;
    public JFXButton btnBack;
    public TextField shopIdTxt;
    public TextField searchTxt;
    public JFXButton btnSearch;
    public TableColumn colOption;
    public JFXButton btnSaveShop;
    public JFXButton btnNewShop;


    public void initialize(){
        colShopId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colShopName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colShopAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        loadAllShops("");

        addShoptbl.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                setData(newValue);
            }
        });
    }

    private void setData(ShopTM tm){
        btnSaveShop.setText("Update Shop");
        shopIdTxt.setText(tm.getId());
        shopNameTxt.setText(tm.getName());
        shopAddressTxt.setText(tm.getAddress());
    }
    public void backbtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) addShopContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
    }

    public void SaveShopOnAction(ActionEvent actionEvent) {
        if(btnSaveShop.getText().equalsIgnoreCase("Save Shop")){
            try {
                ShopDTO dto = new ShopDTO(
                        shopIdTxt.getText(),
                        shopNameTxt.getText(),
                        shopAddressTxt.getText(),
                        0.00
                );
                if(new DataBaseAccessCode().saveShop(dto)) {
                    new Alert(Alert.AlertType.CONFIRMATION,"Shop was Saved", ButtonType.OK).show();
                    loadAllShops("");
                }else{
                    new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }else{
            try {
                ShopDTO dto = new ShopDTO(
                        shopIdTxt.getText(),
                        shopNameTxt.getText(),
                        shopAddressTxt.getText()
                );
                if(new DataBaseAccessCode().updateShopWithoutCredit(dto)) {
                    new Alert(Alert.AlertType.CONFIRMATION,"Shop was Updated", ButtonType.OK).show();
                    loadAllShops("");
                }else{
                    new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String idGenerate(){
        
    }

    private void loadAllShops(String searchText){
        ObservableList<ShopTM> obList = FXCollections.observableArrayList();
        try {
            ArrayList<ShopDTO> dtoList = new DataBaseAccessCode().getAllShops("%" + searchText + "%");
            for (ShopDTO dto:dtoList) {
                ShopTM shopTM = new ShopTM(
                        dto.getId(),
                        dto.getName(),
                        dto.getAddress()
                );
                obList.add(shopTM);
            }
            addShoptbl.setItems(obList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchOnAction(ActionEvent actionEvent) {
        loadAllShops(searchTxt.getText());
    }

    public void NewShopOnAction(ActionEvent actionEvent) {
        btnSaveShop.setText("Save Shop");
        shopIdTxt.clear();
        shopNameTxt.clear();
        shopAddressTxt.clear();
    }

    public void deleteOnAction(ActionEvent actionEvent) {

        Alert confirmation = new Alert(
                Alert.AlertType.CONFIRMATION,
                "ARE YOU SURE ?",
                ButtonType.YES,ButtonType.CANCEL
        );
        Optional<ButtonType> confirmState = confirmation.showAndWait();
        if(confirmState.get().equals(ButtonType.YES)){
            try {
                if(new DataBaseAccessCode().deleteShop(shopIdTxt.getText())) {
                    new Alert(Alert.AlertType.CONFIRMATION,"Shop was Deleted", ButtonType.OK).show();
                    loadAllShops("");
                }else{
                    new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                }
            } catch (ClassNotFoundException | SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}
