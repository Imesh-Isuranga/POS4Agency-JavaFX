package lk.crossorigin.agency.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.crossorigin.agency.bo.custom.ItemBO;
import lk.crossorigin.agency.bo.custom.MainItemBO;
import lk.crossorigin.agency.bo.custom.impl.ItemBoImpl;
import lk.crossorigin.agency.bo.custom.impl.MainItemBoImpl;
import lk.crossorigin.agency.db.DBConnection;
import lk.crossorigin.agency.dto.ItemDTO;
import lk.crossorigin.agency.dto.MainItemDTO;
import lk.crossorigin.agency.view.tm.ItemTM;
import lk.crossorigin.agency.view.tm.OrderTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
    public JFXButton btnPrint;
    public JFXButton btnMainStock;
    public Label lblTot;

    ItemBO itemBO = new ItemBoImpl();
    MainItemBO mainItemBO = new MainItemBoImpl();


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
                boxQtyTxt.setText(String.valueOf(itemTMSelected.getBoxQty()));
                itemQtyTxt.setText(String.valueOf(itemTMSelected.getItemQty()));
                btnUpdateStock.setText("Update");
            }
        });

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
                //itemCodesObList.add(itemDTO.getCode());
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
            ArrayList<ItemDTO> dtoList = itemBO.getAllItems("%" + searchText + "%");
            double wholeTotal = 0.0;
            for (ItemDTO dto: dtoList) {
                double total = 0.0;
                if(dto.getItemCountInBox()==0){
                    total = dto.getUnitPrice_Box_Agency()*dto.getItemQty();
                }else{
                    total = dto.getUnitPrice_Box_Agency()*dto.getBoxQty() + (dto.getUnitPrice_Box_Agency()/dto.getItemCountInBox())*dto.getItemQty();
                }
                wholeTotal += total;
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
            lblTot.setText(String.valueOf(wholeTotal));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void backbtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) loadStockContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
    }

    public Object getCellValue(int rowIndex, int columnIndex) {
        // Step 1: Retrieve the row's data (item) from the TableView's items
        ItemTM rowData = itemsTbl.getItems().get(rowIndex);

        // Step 2: Retrieve the TableColumn for the given column index
        TableColumn<ItemTM, ?> column = itemsTbl.getColumns().get(columnIndex);

        // Step 3: Get the value from the cell using the column's CellDataFeatures
        ObservableValue<?> cellValue = column.getCellObservableValue(rowData);

        // Step 4: Return the cell value
        return cellValue.getValue();
    }

    private int isAlreadyExists(String itemCode){
        for(int i = 0; i<itemsTbl.getItems().size(); i++){
            if(getCellValue(i,1).toString().equals(itemCode)){
                return i;
            }
        }
        return -1;
    }


    public void UpdateStockOnAction(ActionEvent actionEvent) {
        try {
            int boxCount;
            int itemCount;



            if(boxQtyTxt.getText().isEmpty()){
                boxCount=0;
            }else{
                boxCount = Integer.parseInt(boxQtyTxt.getText());
            }

            if(itemQtyTxt.getText().isEmpty()){
                itemCount=0;
            }else {
                itemCount = Integer.parseInt(itemQtyTxt.getText());
            }

            if (Integer.parseInt(boxQtyTxt.getText()) < 0) {
                boxCount = -2;
            }
            if (Integer.parseInt(itemQtyTxt.getText()) < 0) {
                itemCount = -2;
            }

            boxQtyTxt.clear();
            itemQtyTxt.clear();

            MainItemDTO mainItemDTO;
            ItemDTO dto;
            int boxToUpdate = 0;
            int itemToUpdate = 0;
            if(btnUpdateStock.getText().equalsIgnoreCase("Update")){
                mainItemDTO = mainItemBO.getItem(itemTMSelected.getCode());
                dto = new ItemDTO(itemTMSelected.getCode(),boxCount,itemCount);
                boxToUpdate = Integer.parseInt(getCellValue(isAlreadyExists(mainItemDTO.getName()),3).toString());
                itemToUpdate = Integer.parseInt(getCellValue(isAlreadyExists(mainItemDTO.getName()),4).toString());
                if((itemToUpdate + itemCount) >= mainItemDTO.getItemCountInBox()){
                    int newItemCount = (itemToUpdate + itemCount) % (mainItemDTO.getItemCountInBox()) - itemToUpdate;
                    int newBoxCount = boxCount + (itemToUpdate + itemCount) / (mainItemDTO.getItemCountInBox());
                    dto = new ItemDTO(itemTMSelected.getCode(),newBoxCount,newItemCount);
                }
            }else{
                mainItemDTO = mainItemBO.getItem(itemMap.get(selectedCode));
                dto = new ItemDTO(itemMap.get(selectedCode),selectedCode,mainItemDTO.getUnitPrice_Box_Agency(), mainItemDTO.getUnitPrice_Box(), mainItemDTO.getItemCountInBox(),boxCount,itemCount);
            }


            if(boxCount == -2 || itemCount == -2){
                new Alert(Alert.AlertType.WARNING,"Please Insert more than Zero",ButtonType.CANCEL).show();
            }else if(boxCount == -1 && itemCount == -1){
                new Alert(Alert.AlertType.CONFIRMATION,"Please Add Some Stock", ButtonType.OK).show();
            } else if ((mainItemDTO.getBoxQty() < (boxCount)) || ((mainItemDTO.getItemQty() + mainItemDTO.getItemCountInBox()* mainItemDTO.getBoxQty()) < (itemCount + boxCount* mainItemDTO.getItemCountInBox()))) {
                new Alert(Alert.AlertType.WARNING,"No Stock.",ButtonType.CANCEL).show();
            } else if (itemCount >= mainItemDTO.getItemCountInBox()) {
                new Alert(Alert.AlertType.WARNING,"Please reduce items less than items count in box",ButtonType.CANCEL).show();
            } else if ((btnUpdateStock.getText().equalsIgnoreCase("Add Stock") && itemBO.saveItem(dto)) || itemBO.updateItemQtys(dto)) {
                if((boxCount <= mainItemDTO.getBoxQty()) && (itemCount <= mainItemDTO.getItemQty())){
                    int boxQTY = boxCount*-1;
                    int itemQTY = itemCount*-1;
                    if(boxQTY == 1){
                        boxQTY = 0;
                    }

                    if(itemQTY == 1){
                        itemQTY = 0;
                    }
                    MainItemDTO maindto;
                    if(btnUpdateStock.getText().equalsIgnoreCase("Update")){
                        maindto = new MainItemDTO(itemTMSelected.getCode(),boxQTY,itemQTY);
                    }else{
                        maindto = new MainItemDTO(itemMap.get(selectedCode),boxQTY,itemQTY);
                    }
                    if(mainItemBO.updateItemQtys(maindto)){
                        new Alert(Alert.AlertType.CONFIRMATION,"Item was Added", ButtonType.OK).show();
                        loadAllItems("");
                    }else{
                        new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                    }
                }else{
                    int boxQTY = (boxCount+1)*-1;
                    int itemQTY = mainItemDTO.getItemCountInBox() -itemCount;
                    MainItemDTO maindto;
                    if(btnUpdateStock.getText().equalsIgnoreCase("Update")){
                        maindto = new MainItemDTO(itemTMSelected.getCode(),boxQTY,itemQTY);
                    }else{
                        maindto = new MainItemDTO(itemMap.get(selectedCode),boxQTY,itemQTY);
                    }
                    if(mainItemBO.updateItemQtys(maindto)){
                        new Alert(Alert.AlertType.CONFIRMATION,"Item was Added", ButtonType.OK).show();
                        loadAllItems("");
                    }else{
                        new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                    }
                }
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

    public void printOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load("src/lk/crossorigin/agency/reports/items.jrxml");
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


    public void MainStockOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) loadStockContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/MainStockForm.fxml"))));
    }
}
