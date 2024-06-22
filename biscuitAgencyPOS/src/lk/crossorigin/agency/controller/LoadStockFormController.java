package lk.crossorigin.agency.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import lk.crossorigin.agency.dto.ItemDTO;
import lk.crossorigin.agency.view.tm.ItemTM;
import lk.crossorigin.agency.view.tm.ShopTM;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class LoadStockFormController {
    public AnchorPane loadStockContext;
    public JFXButton btnBack;
    public TableView<ItemTM> itemsTbl;
    public TableColumn colItemCode;
    public TableColumn colItemName;
    public TableColumn colUnitPrice_Box;
    public TableColumn colBoxQty;
    public TableColumn colItemQty;
    public TableColumn colTotal;
    public JFXComboBox itemsCmbBox;
    public JFXButton btnUpdateStock;
    public String selectedCode;
    public JFXButton btnUpdateItem;
    public ItemTM itemTMSelected;
    public TextField boxQtyTxt;
    public TextField itemQtyTxt;
    public TableColumn colUnitPrice_Box_Agency;


    public void initialize(){
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colUnitPrice_Box.setCellValueFactory(new PropertyValueFactory<>("unitPrice_Box"));
        colBoxQty.setCellValueFactory(new PropertyValueFactory<>("boxQty"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("itemQty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colUnitPrice_Box_Agency.setCellValueFactory(new PropertyValueFactory<>("unitPrice_Box_Agency"));

        loadAllItems("");

        itemsCmbBox.setItems(loadComboBox(""));

        itemsCmbBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedCode = (String) newValue;
        });

        itemsTbl.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                itemTMSelected = newValue;
            }
        });

    }

    private ObservableList<String> loadComboBox(String searchText){
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            ArrayList<ItemDTO> dtoList = new DataBaseAccessCode().getAllItems("%" + searchText + "%");
            for (ItemDTO dto: dtoList) {
                obList.add(dto.getCode());
            }
            return obList;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void loadAllItems(String searchText){
        ObservableList<ItemTM> obList = FXCollections.observableArrayList();
        try {
            ArrayList<ItemDTO> dtoList = new DataBaseAccessCode().getAllItems("%" + searchText + "%");

            for (ItemDTO dto: dtoList) {
                double total = dto.getUnitPrice_Box()*dto.getBoxQty() + (dto.getUnitPrice_Box()/dto.getItemCountInBox())*dto.getItemQty();
                ItemTM itemTM = new ItemTM(
                        dto.getCode(),
                        dto.getName(),
                        dto.getUnitPrice_Box_Agency(),
                        dto.getUnitPrice_Box(),
                        dto.getBoxQty(),
                        dto.getItemQty(),
                        total
                        );
                obList.add(itemTM);
            }
            itemsTbl.setItems(obList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void backbtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) loadStockContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
    }

    public void UpdateStockOnAction(ActionEvent actionEvent) {
        try {
            int boxCount;
            int itemCount;
            if(boxQtyTxt.getText().isEmpty()){
                boxCount=-1;
            }else{
                boxCount = Integer.parseInt(boxQtyTxt.getText());
            }

            if(itemQtyTxt.getText().isEmpty()){
                itemCount=-1;
            }else {
                itemCount = Integer.parseInt(itemQtyTxt.getText());
            }

            boxQtyTxt.clear();
            itemQtyTxt.clear();

            ItemDTO dto = new ItemDTO(selectedCode,boxCount,itemCount);
            if(boxCount == -1 && itemCount == -1){
                new Alert(Alert.AlertType.CONFIRMATION,"Please Add Some Stock", ButtonType.OK).show();
            }else if(new DataBaseAccessCode().updateItemQtys(dto)) {
                new Alert(Alert.AlertType.CONFIRMATION,"Item was Saved", ButtonType.OK).show();
                loadAllItems("");
            }else{
                new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateItemOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) loadStockContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/UpdateItems.fxml"))));
    }
}
