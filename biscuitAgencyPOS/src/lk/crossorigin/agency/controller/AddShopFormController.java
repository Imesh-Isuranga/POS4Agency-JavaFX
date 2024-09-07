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
import lk.crossorigin.agency.bo.custom.*;
import lk.crossorigin.agency.bo.custom.impl.*;
import lk.crossorigin.agency.db.DBConnection;
import lk.crossorigin.agency.dto.ShopDTO;
import lk.crossorigin.agency.view.tm.ShopTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
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
    public JFXButton btnPrint;

    private ShopTM shopTM;

    ShopBO shopBO = new ShopBoImpl();


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
        if(shopNameTxt.getText().length()<3 && shopAddressTxt.getText().length()<3){
            new Alert(Alert.AlertType.WARNING,"Minimum letter count is 4 letters",ButtonType.CANCEL).show();
        }else if(btnSaveShop.getText().equalsIgnoreCase("Save Shop")){
            try {
                System.out.println("`````````````````````````");
                System.out.println("`````````````````````````");
                String id = shopBO.generateShopId(shopNameTxt.getText(),shopAddressTxt.getText());
                System.out.println(id);
                System.out.println("seeeeeeeeeeeeeeeeeee");
                int numLength = 0;

                // Loop through the string and count numeric characters at the beginning
                for (int i = 0; i < id.length(); i++) {
                    if (Character.isDigit(id.charAt(i))) {
                        numLength++;
                    } else {
                        break; // Stop counting when a non-numeric character is found
                    }
                }
                System.out.println(id.length());
                System.out.println(numLength);
                int new_id = 1;
                if(numLength==1){
                    new_id = Integer.parseInt(id.substring(0,1));
                }else if(numLength==2){
                    new_id = Integer.parseInt(id.substring(0,2));
                }else if(numLength==3){
                    new_id = Integer.parseInt(id.substring(0,3));
                }else if(numLength==4){
                    new_id = Integer.parseInt(id.substring(0,4));
                }else{
                    new_id = Integer.parseInt(id.substring(0,5));
                }

                ShopDTO dto = new ShopDTO(
                        new_id,
                        id,
                        shopNameTxt.getText(),
                        shopAddressTxt.getText(),
                        0.00
                );
                if(shopBO.saveShop(dto)) {
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
                String id = shopBO.getShop(shopTM.getId()).getId();
                int numLength = 0;

                // Loop through the string and count numeric characters at the beginning
                for (int i = 0; i < id.length(); i++) {
                    if (Character.isDigit(id.charAt(i))) {
                        numLength++;
                    } else {
                        break; // Stop counting when a non-numeric character is found
                    }
                }
                int new_id = 1;
                if(numLength==1){
                    new_id = Integer.parseInt(id.substring(0,1));
                }else if(numLength==2){
                    new_id = Integer.parseInt(id.substring(0,2));
                }else if(numLength==3){
                    new_id = Integer.parseInt(id.substring(0,3));
                }else if(numLength==4){
                    new_id = Integer.parseInt(id.substring(0,4));
                }else{
                    new_id = Integer.parseInt(id.substring(0,5));
                }

                System.out.println("111111111111111111111111++++");
                System.out.println(id);
                System.out.println(new_id);

                ShopDTO dto = new ShopDTO(
                        new_id,
                        new_id + " "+shopNameTxt.getText().substring(0,3) +"-" +shopAddressTxt.getText(),
                        shopNameTxt.getText(),
                        shopAddressTxt.getText()
                );



                if(shopBO.updateShopWithoutCredit(dto)) {
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
            ArrayList<ShopDTO> dtoList = shopBO.getAllShops("%" + searchText + "%");
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
                String id = String.valueOf(shopTM.getId());
                int numLength = 0;

                // Loop through the string and count numeric characters at the beginning
                for (int i = 0; i < id.length(); i++) {
                    if (Character.isDigit(id.charAt(i))) {
                        numLength++;
                    } else {
                        break; // Stop counting when a non-numeric character is found
                    }
                }
                int new_id = 1;
                if(numLength==1){
                    new_id = Integer.parseInt(id.substring(0,1));
                }else if(numLength==2){
                    new_id = Integer.parseInt(id.substring(0,2));
                }else if(numLength==3){
                    new_id = Integer.parseInt(id.substring(0,3));
                }else if(numLength==4){
                    new_id = Integer.parseInt(id.substring(0,4));
                }else{
                    new_id = Integer.parseInt(id.substring(0,5));
                }
                if(shopBO.deleteShop(String.valueOf(new_id))) {
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

    public void printOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load("src/lk/crossorigin/agency/reports/Shop_list.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            Connection conn = DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), conn);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "JRException: " + e.getMessage(), ButtonType.OK).show();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQLException: " + e.getMessage(), ButtonType.OK).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "ClassNotFoundException: " + e.getMessage(), ButtonType.OK).show();
        }
    }
}
