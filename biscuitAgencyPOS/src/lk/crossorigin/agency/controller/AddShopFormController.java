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
    public TextField searchTxt;
    public JFXButton btnSearch;
    public TableColumn colOption;
    public JFXButton btnSaveShop;
    public JFXButton btnNewShop;

    private ShopTM shopTM;


    public void initialize(){
        colShopId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colShopName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colShopAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        loadAllShops("");

        addShoptbl.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                shopTM = newValue;
                setData(newValue);
            }
        });
    }

    private void setData(ShopTM tm){
        btnSaveShop.setText("Update Shop");
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
                System.out.println("`````````````````````````");
                String id = new DataBaseAccessCode().generateShopId(shopNameTxt.getText(),shopAddressTxt.getText());

                ShopDTO dto = new ShopDTO(
                        Integer.parseInt(id.substring(0,1)),
                        id,
                        shopNameTxt.getText(),
                        shopAddressTxt.getText(),
                        0.00
                );
                if(new DataBaseAccessCode().saveShop(dto)) {
                    new Alert(Alert.AlertType.CONFIRMATION,"Shop was Saved", ButtonType.OK).show();
                    shopNameTxt.clear();
                    shopAddressTxt.clear();
                    loadAllShops("");
                }else{
                    new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }else{
            try {
                String id = new DataBaseAccessCode().generateShopId(shopNameTxt.getText(),shopAddressTxt.getText());
                ShopDTO dto = new ShopDTO(
                        Integer.parseInt(shopTM.getId().substring(0,1)),
                        shopTM.getId().substring(0,1) + " "+shopNameTxt.getText().substring(0,3) +"-" +shopAddressTxt.getText(),
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
                String id = String.valueOf(shopTM.getId().charAt(0));
                if(new DataBaseAccessCode().deleteShop(id)) {
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
