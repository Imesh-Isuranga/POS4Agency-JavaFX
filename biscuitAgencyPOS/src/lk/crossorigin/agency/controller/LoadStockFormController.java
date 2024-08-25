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
import lk.crossorigin.agency.bo.custom.ItemBO;
import lk.crossorigin.agency.bo.custom.MainItemBO;
import lk.crossorigin.agency.bo.custom.impl.ItemBoImpl;
import lk.crossorigin.agency.bo.custom.impl.MainItemBoImpl;
import lk.crossorigin.agency.db.DBConnection;
import lk.crossorigin.agency.dto.ItemDTO;
import lk.crossorigin.agency.dto.MainItemDTO;
import lk.crossorigin.agency.view.tm.ItemTM;
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

            for (ItemDTO dto: dtoList) {
                double total = 0.0;
                if(dto.getItemCountInBox()==0){
                    total = dto.getUnitPrice_Box()*dto.getItemQty();
                }else{
                    total = dto.getUnitPrice_Box()*dto.getBoxQty() + (dto.getUnitPrice_Box()/dto.getItemCountInBox())*dto.getItemQty();
                }
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

            MainItemDTO mainItemDTO = mainItemBO.getItem(itemMap.get(selectedCode));
            ItemDTO dto = new ItemDTO(itemMap.get(selectedCode),selectedCode,mainItemDTO.getUnitPrice_Box_Agency(), mainItemDTO.getUnitPrice_Box(),mainItemDTO.getItemCountInBox(), boxCount,itemCount);
            if(boxCount == -1 && itemCount == -1){
                new Alert(Alert.AlertType.CONFIRMATION,"Please Add Some Stock", ButtonType.OK).show();
            }else if(itemBO.saveItem(dto)) {
                System.out.println(mainItemDTO.getBoxQty());
                System.out.println(mainItemDTO.getItemQty());
                System.out.println(boxCount);
                System.out.println(itemCount);
                int boxQTY = boxCount*-1;
                int itemQTY = itemCount*-1;
                System.out.println(boxQTY);
                System.out.println(itemQTY);
                MainItemDTO maindto = new MainItemDTO(itemMap.get(selectedCode),boxQTY,itemQTY);
                if(mainItemBO.updateItemQtys(maindto)){
                    new Alert(Alert.AlertType.CONFIRMATION,"Item was Added", ButtonType.OK).show();
                    loadAllItems("");
                }else{
                    new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
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
