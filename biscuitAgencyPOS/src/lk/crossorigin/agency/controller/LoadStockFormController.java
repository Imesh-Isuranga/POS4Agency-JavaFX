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
    public TableColumn colUnitPrice;
    public TableColumn colQTYOnHand;
    public TextField qtyTxt;
    public JFXComboBox itemsCmbBox;
    public TableColumn colOption;
    public JFXButton btnUpdateStock;

    public String selectedCode;


    public void initialize(){
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQTYOnHand.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        loadAllItems("");

        itemsCmbBox.setItems(loadComboBox(""));

        itemsCmbBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedCode = (String) newValue;
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
                Button btn = new Button("Delete");
                ItemTM itemTM = new ItemTM(
                        dto.getCode(),
                        dto.getName(),
                        dto.getUnitPrice(),
                        dto.getQty(),
                        btn);

                obList.add(itemTM);

                //Delete------------------------
                btn.setOnAction(e->{
                    Alert confirmation = new Alert(
                            Alert.AlertType.CONFIRMATION,
                            "ARE YOU SURE ?",
                            ButtonType.YES,ButtonType.CANCEL
                    );
                    Optional<ButtonType> confirmState = confirmation.showAndWait();
                    if(confirmState.get().equals(ButtonType.YES)){
                        try {
                            if(new DataBaseAccessCode().deleteItem(itemTM.getCode())) {
                                new Alert(Alert.AlertType.CONFIRMATION,"Shop was Deleted", ButtonType.OK).show();
                                loadAllItems("");
                            }else{
                                new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                            }

                        } catch (ClassNotFoundException | SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                //Delete------------------------
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
            ItemDTO dto = new ItemDTO(selectedCode,Integer.parseInt(qtyTxt.getText()));
            if(new DataBaseAccessCode().updateItem(dto)) {
                new Alert(Alert.AlertType.CONFIRMATION,"Shop was Saved", ButtonType.OK).show();
                loadAllItems("");
            }else{
                new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
