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
import lk.crossorigin.agency.bo.custom.MainItemBO;
import lk.crossorigin.agency.bo.custom.impl.ItemBoImpl;
import lk.crossorigin.agency.bo.custom.impl.MainItemBoImpl;
import lk.crossorigin.agency.dto.ItemDTO;
import lk.crossorigin.agency.dto.MainItemDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MainUpdateItemsController {
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


    MainItemBO mainItemBO = new MainItemBoImpl();
    ItemBO itemBO = new ItemBoImpl();

    public void initialize(){
        cmbItems.setItems(loadComboBox(""));

        /*codetxt.textProperty().addListener((observable, oldValue, newValue) -> {
            btnUpdate.setText("Save");
        });*/

        cmbItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                btnUpdate.setText("Update");
                selectedCode = (String) newValue;
                if(mainItemBO.getItem(itemMap.get(selectedCode)) != null){
                    loadTxtFields(itemMap.get(selectedCode));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void loadTxtFields(String code) throws SQLException, ClassNotFoundException {
        MainItemDTO mainItemDTO = mainItemBO.getItem(code);
        codetxt.setText(mainItemDTO.getCode());
        nametxt.setText(mainItemDTO.getName());
        unitBoxPriceAgencytxt.setText(String.valueOf(mainItemDTO.getUnitPrice_Box_Agency()));
        unitBoxPricetxt.setText(String.valueOf(mainItemDTO.getUnitPrice_Box()));
        itemCounttxt.setText(String.valueOf(mainItemDTO.getItemCountInBox()));
        boxQtytxt.setText(String.valueOf(mainItemDTO.getBoxQty()));
        itemQtytxt.setText(String.valueOf(mainItemDTO.getItemQty()));
    }

    private Map<String, String> itemMap = new HashMap<>();  // Map to store item name and code

    private ObservableList<String> loadComboBox(String searchText){
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            ArrayList<MainItemDTO> dtoList = mainItemBO.getAllItems("%" + searchText + "%");
            for (MainItemDTO dto: dtoList) {
                //obList.add(dto.getCode());
                obList.add(dto.getName());
                itemMap.put(dto.getName(), dto.getCode());  // Store the item name and code in the map
            }
            return obList;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void backbtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) itemUpdateContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/MainStockForm.fxml"))));
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        MainItemDTO mainItemDTO = null;
        if(!codetxt.getText().isEmpty() && !nametxt.getText().isEmpty() && !unitBoxPriceAgencytxt.getText().isEmpty() && !unitBoxPricetxt.getText().isEmpty() && !itemCounttxt.getText().isEmpty() && !boxQtytxt.getText().isEmpty() && !itemQtytxt.getText().isEmpty()){
            mainItemDTO = new MainItemDTO(codetxt.getText(),nametxt.getText(),Double.parseDouble(unitBoxPriceAgencytxt.getText()),Double.parseDouble(unitBoxPricetxt.getText()),Integer.parseInt(itemCounttxt.getText()),Integer.parseInt(boxQtytxt.getText()),Integer.parseInt(itemQtytxt.getText()));
        }

        if(codetxt.getText().isEmpty() || nametxt.getText().isEmpty() || unitBoxPriceAgencytxt.getText().isEmpty() || unitBoxPricetxt.getText().isEmpty() || itemCounttxt.getText().isEmpty() || boxQtytxt.getText().isEmpty() || itemQtytxt.getText().isEmpty() || (itemCounttxt.getText().equalsIgnoreCase("0") && (itemQtytxt.getText().equalsIgnoreCase("0")) && (boxQtytxt.getText().equalsIgnoreCase("0")))){
            new Alert(Alert.AlertType.WARNING,"Please fill all fields.",ButtonType.CANCEL).show();
        }else if(itemCounttxt.getText().equalsIgnoreCase("0") && !(boxQtytxt.getText().equalsIgnoreCase("0"))){
            new Alert(Alert.AlertType.WARNING,"Item count in box cant be zero as box count is not zero.",ButtonType.CANCEL).show();
        }else if(Integer.parseInt(itemCounttxt.getText()) == 0 && Integer.parseInt(boxQtytxt.getText()) != 0){
            new Alert(Alert.AlertType.WARNING,"Please make box count to 0",ButtonType.CANCEL).show();
        }else if(Integer.parseInt(itemCounttxt.getText()) <= Integer.parseInt(itemQtytxt.getText()) && (Integer.parseInt(itemCounttxt.getText()) != 0)){
            new Alert(Alert.AlertType.WARNING,"Please reduce item count less than itemCount in box.",ButtonType.CANCEL).show();
        }else if(btnUpdate.getText().equalsIgnoreCase("Update")){
            if(mainItemBO.updateItem(mainItemDTO)) {
                ItemDTO item = itemBO.getItem(codetxt.getText());

                if(item != null){
                    ItemDTO itemDTO = new ItemDTO(codetxt.getText(),nametxt.getText(),Double.parseDouble(unitBoxPriceAgencytxt.getText()),Double.parseDouble(unitBoxPricetxt.getText()),Integer.parseInt(itemCounttxt.getText()),item.getBoxQty(),item.getItemQty());
                    if(itemBO.updateItem(itemDTO)){
                        clearAll();
                        new Alert(Alert.AlertType.CONFIRMATION,"Item was Updated", ButtonType.OK).show();
                    }else{
                        new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                    }
                }else{
                    clearAll();
                    new Alert(Alert.AlertType.CONFIRMATION,"Item was Updated", ButtonType.OK).show();
                }

            }else{
                new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
            }
        }else{
            if(mainItemBO.saveItem(mainItemDTO)) {
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
                if(mainItemBO.deleteItem(itemMap.get(selectedCode))) {
                    ItemDTO item = itemBO.getItem(itemMap.get(selectedCode));
                    if(((item != null)  && (itemBO.deleteItem(itemMap.get(selectedCode)))) || item == null){
                        new Alert(Alert.AlertType.CONFIRMATION,"Item was Deleted", ButtonType.OK).show();
                        clearAll();
                        cmbItems.setItems(loadComboBox(""));
                    }else {
                        new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                    }
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
        unitBoxPriceAgencytxt.clear();
        itemCounttxt.clear();
        boxQtytxt.clear();
        itemQtytxt.clear();
    }

    public void addItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        clearAll();
        btnUpdate.setText("Save");
    }

}
