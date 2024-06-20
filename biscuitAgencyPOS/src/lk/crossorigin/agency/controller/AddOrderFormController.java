package lk.crossorigin.agency.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.crossorigin.agency.DataBaseAccessCode;
import lk.crossorigin.agency.db.DBConnection;
import lk.crossorigin.agency.dto.ItemDTO;
import lk.crossorigin.agency.dto.OrderDTO;
import lk.crossorigin.agency.dto.ShopDTO;
import lk.crossorigin.agency.entity.Item;
import lk.crossorigin.agency.entity.Order;
import lk.crossorigin.agency.entity.OrderDetail;
import lk.crossorigin.agency.entity.Shop;
import lk.crossorigin.agency.view.tm.ItemTM;
import lk.crossorigin.agency.view.tm.OrderTM;
import lk.crossorigin.agency.view.tm.ShopTM;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddOrderFormController {
    public AnchorPane addOrderContext;
    public JFXButton btnBack;
    public TableView<OrderTM> addOrderTbl;
    public TableColumn colShopName;
    public TableColumn colItemName;
    public TableColumn colQTY;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public JFXButton btnAddtoCart;
    public JFXButton btnPlaceOrder;
    public Label lblOrderId;
    public Label lblDate;
    public JFXButton btnRemove;
    public JFXComboBox cmbShopName;
    public JFXComboBox cmbItemName;
    public TextField txtqty;
    public Label lblTotal;

    private OrderTM orderTM;

    private ObservableList<OrderTM> obList = FXCollections.observableArrayList();

    public void initialize() throws SQLException, ClassNotFoundException {
        String formatDate = formatDate(new Date());
        lblDate.setText(formatDate);

        lblOrderId.setText(new DataBaseAccessCode().getLastOrderId());

        cmbShopName.setItems(loadAllShopIds());
        cmbItemName.setItems(loadAllItemCodes());

        colShopName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("code"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        addOrderTbl.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                orderTM = newValue;
            }
        });
    }
    public void backbtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) addOrderContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
    }

    public void addtoCartOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        int qty = Integer.parseInt(txtqty.getText());
        double unitPrice = new DataBaseAccessCode().getItemByName(cmbItemName.getValue().toString()).getUnitPrice();
        double total = qty * unitPrice;

        int rowIndex = isAlreadyExists(cmbShopName.getValue().toString(),cmbItemName.getValue().toString());

        System.out.println(rowIndex);
        if(rowIndex!=-1) {
            // Update the quantity and total of the existing item
            OrderTM existingItem = obList.get(rowIndex);
            existingItem.setQty(existingItem.getQty() + qty);
            existingItem.setTotal(existingItem.getQty() * existingItem.getUnitPrice());

            // Force the table to refresh by setting the item to itself
            obList.set(rowIndex, existingItem);
        }else{
            OrderTM orderTM = new OrderTM(cmbShopName.getValue().toString(),cmbItemName.getValue().toString(),qty,unitPrice,total);
            obList.add(orderTM);
            addOrderTbl.setItems(obList);
        }
        lblTotal.setText(String.valueOf(calculateTotalValue()));
    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, ParseException {
        ArrayList <OrderDetail>orderDetailList=new ArrayList<>();


        for (int i = 0; i < addOrderTbl.getItems().size(); i++) {
            String shopId = getCellValue(i,0).toString();
            String itemCode = getCellValue(i,1).toString();
            int orderQty=Integer.parseInt(getCellValue(i,2).toString());
            double unitPrice=Double.parseDouble(getCellValue(i,3).toString());
            OrderDetail orderDetail=new OrderDetail(lblOrderId.getText(), shopId, itemCode, orderQty, unitPrice);
            orderDetailList.add(orderDetail);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        OrderDTO orderDTO=new OrderDTO(lblOrderId.getText(), format.parse(lblDate.getText()));
        boolean isAdded = new DataBaseAccessCode().saveOrder(orderDTO);

        if (isAdded) {
            System.out.println("----------------------------------------------------------");
        }
    }

    public void removeOnAction(ActionEvent actionEvent) {
        obList.remove(orderTM);
        lblTotal.setText(String.valueOf(calculateTotalValue()));
    }

    private String formatDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
    private ObservableList<String> loadAllShopIds() throws SQLException, ClassNotFoundException {
        ObservableList<String> shopIdsObList = FXCollections.observableArrayList();;
        ArrayList<ShopDTO> shopDTOArrayList = new DataBaseAccessCode().getAllShops("%"+""+"%");

        for (ShopDTO shopDTO:shopDTOArrayList) {
            shopIdsObList.add(shopDTO.getId());
        }

        return shopIdsObList;
    }

    private ObservableList<String> loadAllItemCodes() throws SQLException, ClassNotFoundException {
        ObservableList<String> itemCodesObList = FXCollections.observableArrayList();;
        ArrayList<ItemDTO> itemDTOArrayList = new DataBaseAccessCode().getAllItems("%"+""+"%");

        for (ItemDTO itemDTO:itemDTOArrayList) {
            itemCodesObList.add(itemDTO.getCode());
        }

        return itemCodesObList;
    }

    private int isAlreadyExists(String shopName,String itemName){
        for(int i = 0; i<addOrderTbl.getItems().size(); i++){
            if(getCellValue(i,0).toString().equals(shopName)){
                if(getCellValue(i,1).toString().equals(itemName)){
                    return i;
                }
            }
        }
        return -1;
    }

    public Object getCellValue(int rowIndex, int columnIndex) {
        // Step 1: Retrieve the row's data (item) from the TableView's items
        OrderTM rowData = addOrderTbl.getItems().get(rowIndex);

        // Step 2: Retrieve the TableColumn for the given column index
        TableColumn<OrderTM, ?> column = addOrderTbl.getColumns().get(columnIndex);

        // Step 3: Get the value from the cell using the column's CellDataFeatures
        ObservableValue<?> cellValue = column.getCellObservableValue(rowData);

        // Step 4: Return the cell value
        return cellValue.getValue();
    }

    public double calculateTotalValue(){
        double total = 0.00;
        for(int i = 0; i<addOrderTbl.getItems().size(); i++){
            total += Double.parseDouble(getCellValue(i,4).toString());
        }
        return total;
    }
}
