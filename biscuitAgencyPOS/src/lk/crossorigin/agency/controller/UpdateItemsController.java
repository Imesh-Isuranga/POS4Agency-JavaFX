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
import lk.crossorigin.agency.bo.custom.ItemBO;
import lk.crossorigin.agency.bo.custom.impl.ItemBoImpl;
import lk.crossorigin.agency.dto.ItemDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class UpdateItemsController {
    public JFXButton btnBack;
    public JFXComboBox cmbItems;
    public TextField nametxt;
    public TextField unitBoxPricetxt;
    public TextField itemCounttxt;
    public TextField itemQtytxt;
    public TextField boxQtytxt;
    public JFXButton btnUpdate;
    public AnchorPane itemUpdateContext;
    public String selectedCode;
    public TextField codetxt;
    public JFXButton btnAddItem;
    public JFXButton btnDelete;
    public TextField unitBoxPriceAgencytxt;


    ItemBO itemBO = new ItemBoImpl();

    public void initialize(){
        cmbItems.setItems(loadComboBox(""));

        cmbItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                btnUpdate.setText("Update");
                selectedCode = (String) newValue;
                if(itemBO.getItem(selectedCode) != null){
                    loadTxtFields(selectedCode);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void loadTxtFields(String code) throws SQLException, ClassNotFoundException {
        ItemDTO itemDTO = itemBO.getItem(code);
        codetxt.setText(itemDTO.getCode());
        nametxt.setText(itemDTO.getName());
        unitBoxPricetxt.setText(String.valueOf(itemDTO.getUnitPrice_Box()));
        itemCounttxt.setText(String.valueOf(itemDTO.getItemCountInBox()));
        boxQtytxt.setText(String.valueOf(itemDTO.getBoxQty()));
        itemQtytxt.setText(String.valueOf(itemDTO.getItemQty()));
    }
    private ObservableList<String> loadComboBox(String searchText){
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            ArrayList<ItemDTO> dtoList = itemBO.getAllItems("%" + searchText + "%");
            for (ItemDTO dto: dtoList) {
                obList.add(dto.getCode());
            }
            return obList;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void backbtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) itemUpdateContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoadStockForm.fxml"))));
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ItemDTO itemDTO = new ItemDTO(codetxt.getText(),nametxt.getText(),Double.parseDouble(unitBoxPriceAgencytxt.getText()),Double.parseDouble(unitBoxPricetxt.getText()),Integer.parseInt(itemCounttxt.getText()),Integer.parseInt(boxQtytxt.getText()),Integer.parseInt(itemQtytxt.getText()));
        if(btnUpdate.getText().equalsIgnoreCase("Update")){
            if(itemBO.updateItem(itemDTO)) {
                clearAll();
                new Alert(Alert.AlertType.CONFIRMATION,"Item was Updated", ButtonType.OK).show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
            }
        }else{
            if(itemBO.saveItem(itemDTO)) {
                clearAll();
                new Alert(Alert.AlertType.CONFIRMATION,"Item was Added", ButtonType.OK).show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
            }
        }
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
                if(itemBO.deleteItem(selectedCode)) {
                    new Alert(Alert.AlertType.CONFIRMATION,"Shop was Deleted", ButtonType.OK).show();
                    clearAll();
                    cmbItems.setItems(loadComboBox(""));
                }else{
                    new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                }
            } catch (ClassNotFoundException | SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void clearAll() {
        codetxt.clear();
        nametxt.clear();
        unitBoxPricetxt.clear();
        itemCounttxt.clear();
        boxQtytxt.clear();
        itemQtytxt.clear();
    }

    public void addItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        clearAll();
        btnUpdate.setText("Save");
    }

}
