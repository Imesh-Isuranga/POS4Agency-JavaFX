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
    public JFXButton btnDelete;

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
            btnUpdateStock.setText("Add Stock");
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
                    total = dto.getUnitPrice_Box()*dto.getItemQty();
                }else{
                    total = dto.getUnitPrice_Box()*dto.getBoxQty() + (dto.getUnitPrice_Box()/dto.getItemCountInBox())*dto.getItemQty();
                }
                total = Math.round(total * 100.0) / 100.0;
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
            wholeTotal = Math.round(wholeTotal * 100.0) / 100.0;
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
                boxCount=-1;
            }else if(Integer.parseInt(boxQtyTxt.getText()) < 0){
                boxCount = -2;
            }else{
                boxCount = Integer.parseInt(boxQtyTxt.getText());
            }

            if(itemQtyTxt.getText().isEmpty()){
                itemCount=-1;
            }else if(Integer.parseInt(itemQtyTxt.getText()) < 0){
                itemCount = -2;
            }else {
                itemCount = Integer.parseInt(itemQtyTxt.getText());
            }


            MainItemDTO mainItem = null;
            MainItemDTO mainItemTemp ;
            if(btnUpdateStock.getText().equalsIgnoreCase("Update")){
                mainItem = mainItemBO.getItem(itemTMSelected.getCode());
                if(mainItem.getItemCountInBox()==0 && !(boxQtyTxt.getText().equalsIgnoreCase("0"))){
                    boxCount = -3;
                }
            }else{
                try {
                    mainItem = mainItemBO.getItem(itemMap.get(selectedCode));
                    if(selectedCode != null){
                        if(mainItem.getItemCountInBox()==0 && !(boxQtyTxt.getText().equalsIgnoreCase("0"))){
                            boxCount = -3;
                        }
                    }
                }catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            }

            MainItemDTO mainItemDTO;
            ItemDTO dto;
            ItemDTO item;
            if(selectedCode != null){
                dto = new ItemDTO(itemMap.get(selectedCode),boxCount,itemCount);
            }



            if(selectedCode == null && !(btnUpdateStock.getText().equalsIgnoreCase("Update"))){
                new Alert(Alert.AlertType.WARNING,"Please select item",ButtonType.CANCEL).show();
            }else if(boxCount == -2 || itemCount == -2){
                new Alert(Alert.AlertType.WARNING,"Please Insert more than Zero",ButtonType.CANCEL).show();
            }else if(boxCount == -1 || itemCount == -1){
                new Alert(Alert.AlertType.CONFIRMATION,"Please Add Some Stock", ButtonType.OK).show();
            }else if(boxCount == -3){
                new Alert(Alert.AlertType.CONFIRMATION,"Please make boxQTY to 0", ButtonType.OK).show();
            }else if(mainItem.getItemCountInBox()!=0 && (Integer.parseInt(itemQtyTxt.getText())>=mainItem.getItemCountInBox())){
                new Alert(Alert.AlertType.CONFIRMATION,"Please insert items less than Item count in box.", ButtonType.OK).show();
            }else if(boxCount > mainItem.getBoxQty() || ((boxCount*mainItem.getItemCountInBox())+itemCount > (mainItem.getBoxQty()*mainItem.getItemCountInBox()+mainItem.getItemQty()))){
                new Alert(Alert.AlertType.CONFIRMATION,"No Stock", ButtonType.OK).show();
            }else if(btnUpdateStock.getText().equalsIgnoreCase("Update")){
                mainItem = mainItemBO.getItem(itemTMSelected.getCode());
                item = itemBO.getItem(itemTMSelected.getCode());
                dto = new ItemDTO(itemTMSelected.getCode(),boxCount,itemCount);
                if((item.getItemQty() + itemCount) >= item.getItemCountInBox() && !(mainItem.getItemCountInBox()==0 && mainItem.getBoxQty()==0 && boxQtyTxt.getText().equalsIgnoreCase("0"))){
                    dto = new ItemDTO(itemTMSelected.getCode(),(boxCount+1),(item.getItemQty() + itemCount)-item.getItemCountInBox() - item.getItemQty());
                }
                //boxToUpdate = Integer.parseInt(getCellValue(isAlreadyExists(mainItemDTO.getName()),3).toString());
                //itemToUpdate = Integer.parseInt(getCellValue(isAlreadyExists(mainItemDTO.getName()),4).toString());
                if(mainItem.getItemCountInBox()>itemCount  || (mainItem.getItemCountInBox()==0 && mainItem.getBoxQty()==0 && boxQtyTxt.getText().equalsIgnoreCase("0"))){
                    if(mainItem.getItemQty() >= itemCount || (mainItem.getItemCountInBox()==0 && mainItem.getBoxQty()==0 && boxQtyTxt.getText().equalsIgnoreCase("0"))){
                        mainItemDTO = new MainItemDTO(itemTMSelected.getCode(),boxCount,itemCount);
                    }else{
                        mainItemDTO = new MainItemDTO(itemTMSelected.getCode(),(boxCount+1),(-mainItem.getItemCountInBox())+itemCount - mainItem.getItemQty()+ mainItem.getItemQty());
                    }
                    if(itemBO.updateItemQtysIncrease(dto)){
                        if(mainItemBO.updateItemQtysReduce(mainItemDTO)){
                            new Alert(Alert.AlertType.CONFIRMATION,"Item was Updated", ButtonType.OK).show();
                            loadAllItems("");
                        }else{
                            new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                        }
                    }else{
                        new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                    }
                }else{
                    if(mainItem.getItemCountInBox()==0 && mainItem.getBoxQty()==0 && !(boxQtyTxt.getText().equalsIgnoreCase("0"))){
                        new Alert(Alert.AlertType.WARNING,"Please make boxQTY to 0",ButtonType.CANCEL).show();
                    }else{
                        new Alert(Alert.AlertType.WARNING,"Please enter items less than items count in box",ButtonType.CANCEL).show();
                    }                }


                /*if(mainItemDTO.getItemQty() < itemCount){
                    boxCount++;
                }
                if((itemToUpdate + itemCount) >= mainItemDTO.getItemCountInBox()){
                    int newItemCount = (itemToUpdate + itemCount) % (mainItemDTO.getItemCountInBox()) - itemToUpdate;
                    int newBoxCount = boxCount + (itemToUpdate + itemCount) / (mainItemDTO.getItemCountInBox());
                    dto = new ItemDTO(itemTMSelected.getCode(),newBoxCount,newItemCount);
                }*/
            }else{
                dto = new ItemDTO(itemMap.get(selectedCode),selectedCode,mainItem.getUnitPrice_Box_Agency(), mainItem.getUnitPrice_Box(), mainItem.getItemCountInBox(),boxCount,itemCount);
                if((mainItem.getItemCountInBox()>itemCount || (mainItem.getItemCountInBox()==0 && mainItem.getBoxQty()==0 && boxQtyTxt.getText().equalsIgnoreCase("0"))) && (itemCount <= mainItem.getItemQty())){
                    if(itemBO.saveItem(dto)){
                        mainItemDTO = new MainItemDTO(itemMap.get(selectedCode),boxCount,itemCount);
                        if(mainItemBO.updateItemQtysReduce(mainItemDTO)){
                            new Alert(Alert.AlertType.CONFIRMATION,"Item was Added", ButtonType.OK).show();
                            loadAllItems("");
                        }else{
                            new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                        }
                    }else{
                        new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                    }
                }else{
                    if(itemBO.saveItem(dto)){
                        mainItemDTO = new MainItemDTO(itemMap.get(selectedCode),(boxCount+1),((-mainItem.getItemQty() - mainItem.getItemCountInBox())+itemCount+mainItem.getItemQty()));
                        if(mainItemBO.updateItemQtysReduce(mainItemDTO)){
                            new Alert(Alert.AlertType.CONFIRMATION,"Item was Added", ButtonType.OK).show();
                            loadAllItems("");
                        }else{
                            new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                        }
                    }else{
                        new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                    }
                }
            }

            boxQtyTxt.clear();
            itemQtyTxt.clear();


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
            JasperDesign design = JRXmlLoader.load("src/lk/crossorigin/agency/reports/Stock_Lorry.jrxml");
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

    public void DeleteOnAction(ActionEvent actionEvent) {
        Alert confirmation = new Alert(
                Alert.AlertType.CONFIRMATION,
                "ARE YOU SURE ?",
                ButtonType.YES,ButtonType.CANCEL
        );
        Optional<ButtonType> confirmState = confirmation.showAndWait();
        if(confirmState.get().equals(ButtonType.YES)){
            try {
                MainItemDTO mainItem = mainItemBO.getItem(itemTMSelected.getCode());
                int boxToDelete = Integer.parseInt(getCellValue(isAlreadyExists(mainItem.getName()),3).toString());
                int itemToDelete = Integer.parseInt(getCellValue(isAlreadyExists(mainItem.getName()),4).toString());
                if(itemBO.deleteItem(itemTMSelected.getCode())) {
                    MainItemDTO mainItemDTO =  new MainItemDTO(itemTMSelected.getCode(),boxToDelete,itemToDelete);
                    if((mainItem.getItemQty() + itemToDelete) >= mainItem.getItemCountInBox()){
                        int newBoxQTY;
                        int newItemQTY;
                        if(mainItem.getItemCountInBox()!=0){
                            newBoxQTY = boxToDelete + 1;
                        }else{
                            newBoxQTY = 0;
                        }
                        if(mainItem.getItemCountInBox()!=0){
                            newItemQTY = (itemToDelete + mainItem.getItemQty()) % (mainItem.getItemCountInBox()) - mainItem.getItemQty();
                        }else{
                            newItemQTY = (itemToDelete + mainItem.getItemQty()) - mainItem.getItemQty();
                        }
                        mainItemDTO =  new MainItemDTO(itemTMSelected.getCode(),newBoxQTY,newItemQTY);
                    }
                    if(mainItemBO.updateItemQtysIncrease(mainItemDTO)){
                        new Alert(Alert.AlertType.CONFIRMATION,"Shop was Deleted", ButtonType.OK).show();
                        itemsCmbBox.setItems(loadComboBox(""));
                        loadAllItems("");
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

}
