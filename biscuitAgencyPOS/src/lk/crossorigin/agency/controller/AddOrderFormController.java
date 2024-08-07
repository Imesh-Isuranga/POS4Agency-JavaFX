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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.crossorigin.agency.bo.custom.*;
import lk.crossorigin.agency.bo.custom.impl.*;
import lk.crossorigin.agency.dto.*;
import lk.crossorigin.agency.entity.OrderDetail;
import lk.crossorigin.agency.view.tm.DiscountItemsTM;
import lk.crossorigin.agency.view.tm.FreeItemsTM;
import lk.crossorigin.agency.view.tm.OrderTM;
import lk.crossorigin.agency.view.tm.ReturnStockTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class AddOrderFormController {
    public AnchorPane addOrderContext;
    public JFXButton btnBack;
    public TableView<OrderTM> addOrderTbl;
    public TableColumn colItemCode;
    public TableColumn colBoxQty;
    public TableColumn colItemQty;
    public TableColumn colTotal;
    public JFXButton btnAddtoCart;
    public JFXButton btnPlaceOrder;
    public Label lblOrderId;
    public Label lblDate;
    public JFXButton btnRemove;
    public Label lblTotal;
    public TextField txtBoxQty;;
    public TextField txtItemQty;
    public JFXComboBox cmbItemCode;
    public TextField txtBoxQtyFree;
    public TextField txtItemQtyFree;
    public JFXComboBox cmbFreeItemCode;
    public TableColumn colFreeItemCode;
    public TableColumn colFreeBoxCount;
    public TableColumn colFreeItemCount;
    public TableColumn colRemove;
    public TableColumn colFreeItemTotal;
    public JFXButton btnAddFree;
    public TableView<FreeItemsTM> addFreeTbl;
    public TableView<DiscountItemsTM> tblDiscount;
    public JFXButton btnAddDiscountToTable;
    public JFXComboBox cmbDiscount;
    public TextField txtDiscount;
    public TableColumn colDiscountItemCode;
    public TableColumn colRemoveDis;
    public JFXButton btnAddDiscount;
    public TextField banktxt;
    public TextField cashAmounttxt;
    public Label lblCredit;
    public Label paidAmountlbl;
    public Label balanceAmountlbl;
    public CheckBox cashcbx;
    public CheckBox creditcbx;
    public CheckBox chequecbx;
    public TextField chequeAmounttxt;
    public TextField chequeNumtxt;
    public Label shopCreditUptoNowlbl;
    public Label shopIdlbl;
    public TableView<ReturnStockTM> tblReturn;
    public TableColumn colItemCodeReturn;
    public TableColumn colReturnBoxQty;
    public TableColumn colReturnItemQty;
    public TableColumn colReturnRemove;
    public JFXButton btnAddReturnToTable;
    public JFXComboBox cmbReturnItem;
    public JFXButton btnAddReurnStock;
    public TextField txtBoxQtyReturn;
    public TextField txtItemQtyReturn;
    public Label returnTotallbl;
    private OrderTM orderTM;
    private FreeItemsTM freeItemsTM;
    private DiscountItemsTM discountItemsTM;
    private ObservableList<OrderTM> obList = FXCollections.observableArrayList();
    private ObservableList<ReturnStockTM> obListReturn = FXCollections.observableArrayList();
    private ObservableList<FreeItemsTM> obFreeList = FXCollections.observableArrayList();
    private ObservableList<DiscountItemsTM> obDisList = FXCollections.observableArrayList();
    ObservableList<String> itemDisCodesObList = FXCollections.observableArrayList();;
    int discountGeneratedId = 1;

    private double returnTotal = 0.00;


    DiscountBO discountBO = new DiscountBoImpl();
    ItemBO itemBO = new ItemBoImpl();
    OrderBO orderBO = new OrderBoImpl();
    OrderBookBO orderBookBO = new OrderBookBoImpl();
    OrderDetailBO orderDetailBO = new OrderDetailBoImpl();
    PaymentBO paymentBO = new PaymentBoImpl();
    ReturnStockBO returnStockBO = new ReturnStockBoImpl();
    ShopBO shopBO = new ShopBoImpl();

    public void initialize() throws SQLException, ClassNotFoundException {
        shopIdlbl.setText(orderBookBO.getOrderBook(orderBookBO.getLastOrderId()).getShopId());
        String formatDate = formatDate(new Date());
        lblDate.setText(formatDate);

        lblOrderId.setText(orderBookBO.getLastOrderId());


        cmbItemCode.setItems(loadAllItemCodes());
        cmbFreeItemCode.setItems(itemDisCodesObList);
        cmbDiscount.setItems(itemDisCodesObList);

        cmbReturnItem.setItems(loadAllItemCodes());


        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colBoxQty.setCellValueFactory(new PropertyValueFactory<>("boxQty"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("itemQty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        colFreeItemCode.setCellValueFactory(new PropertyValueFactory<>("freeItemCode"));
        colFreeBoxCount.setCellValueFactory(new PropertyValueFactory<>("freeBoxQty"));
        colFreeItemCount.setCellValueFactory(new PropertyValueFactory<>("freeItemQty"));
        colFreeItemTotal.setCellValueFactory(new PropertyValueFactory<>("discountItemTotal"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("btn"));

        colDiscountItemCode.setCellValueFactory(new PropertyValueFactory<>("discountItemCode"));
        colRemoveDis.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));


        colItemCodeReturn.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colReturnBoxQty.setCellValueFactory(new PropertyValueFactory<>("boxQty"));
        colReturnItemQty.setCellValueFactory(new PropertyValueFactory<>("itemQty"));
        colReturnRemove.setCellValueFactory(new PropertyValueFactory<>("btn"));

        addOrderTbl.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                orderTM = newValue;
            }
        });

        addFreeTbl.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                freeItemsTM = newValue;
            }
        });

        tblDiscount.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                discountItemsTM = newValue;
            }
        });

        chequeAmounttxt.textProperty().addListener((observable, oldValue, newValue) -> {
            if(chequecbx.isSelected()){
                double cashAmount ;
                if(cashAmounttxt.getText().isEmpty()){
                    cashAmount = 0;
                }else {
                    cashAmount = Double.parseDouble(cashAmounttxt.getText());
                }

                if(newValue.isEmpty()){
                    newValue = 0.00+"";
                }

                paidAmountlbl.setText(String.valueOf(cashAmount + Double.parseDouble(newValue)));

                double balanceAmount = Double.parseDouble(lblTotal.getText())-Double.parseDouble(paidAmountlbl.getText());
                balanceAmountlbl.setText(String.valueOf(balanceAmount));
                editCreditBalance();
            }
        });

        cashAmounttxt.textProperty().addListener((observable, oldValue, newValue) -> {
            if(cashcbx.isSelected()){
                double chequeAmount ;
                double cashAmount ;
                if(chequeAmounttxt.getText().isEmpty()){
                    chequeAmount = 0;
                }else {
                    chequeAmount = Double.parseDouble(chequeAmounttxt.getText());
                }
                if(newValue.isEmpty()){
                    newValue = 0.00+"";
                }
                paidAmountlbl.setText(String.valueOf(chequeAmount + Double.parseDouble(newValue)));
                double balanceAmount = Double.parseDouble(lblTotal.getText())-Double.parseDouble(paidAmountlbl.getText());
                balanceAmountlbl.setText(String.valueOf(balanceAmount));
                editCreditBalance();
            }
        });

        shopCreditUptoNowlbl.setText(String.valueOf(shopBO.getShop(shopIdlbl.getText()).getCredit_uptoNow()));


    }


    private void editCreditBalance(){
        lblCredit.setText(String.valueOf(Double.parseDouble(lblTotal.getText())-Double.parseDouble(paidAmountlbl.getText())));
    }


    public void backbtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) addOrderContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
    }

    public void addtoCartOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        int boxCount;
        int itemCount;

        if(txtBoxQty.getText().isEmpty()){
            boxCount=0;
        }else{
            boxCount = Integer.parseInt(txtBoxQty.getText());
        }

        if(txtItemQty.getText().isEmpty()){
            itemCount=0;
        }else {
            itemCount = Integer.parseInt(txtItemQty.getText());
        }

        if(boxCount==0 && itemCount==0){
            new Alert(Alert.AlertType.CONFIRMATION,"Please Add Some Stock", ButtonType.OK).show();
        }else {
            double unitPrice_Box = itemBO.getItemByName(cmbItemCode.getValue().toString()).getUnitPrice_Box();
            int itemCountInBox = itemBO.getItemByName(cmbItemCode.getValue().toString()).getItemCountInBox();
            double total = (unitPrice_Box * boxCount) + (unitPrice_Box/itemCountInBox)*itemCount;

            int rowIndex = isAlreadyExists(cmbItemCode.getValue().toString());

            if(rowIndex!=-1) {
                // Update the quantity and total of the existing item
                OrderTM existingItem = obList.get(rowIndex);
                existingItem.setBoxQty(existingItem.getBoxQty() + boxCount);
                existingItem.setItemQty(existingItem.getItemQty() + itemCount);
                existingItem.setTotal(existingItem.getTotal() + total);

                // Force the table to refresh by setting the item to itself
                obList.set(rowIndex, existingItem);
            }else{
                OrderTM orderTM = new OrderTM(cmbItemCode.getValue().toString(),boxCount,itemCount,total);
                obList.add(orderTM);
                addOrderTbl.setItems(obList);

                itemDisCodesObList.add(cmbItemCode.getValue().toString());
            }
            lblTotal.setText(String.valueOf(calculateTotalValue()));
        }
    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, ParseException {
        if((Double.parseDouble(lblTotal.getText()) <= Double.parseDouble(paidAmountlbl.getText())) || ((Double.parseDouble(lblTotal.getText()) > Double.parseDouble(paidAmountlbl.getText())) && creditcbx.isSelected()) && Double.parseDouble(lblTotal.getText())!=0.00){
            ArrayList <OrderDetail>orderDetailList=new ArrayList<>();


            for (int i = 0; i < addOrderTbl.getItems().size(); i++) {
                String itemCode = getCellValue(i,0).toString();
                double unitPrice_Box = itemBO.getItem(getCellValue(i,0).toString()).getUnitPrice_Box();
                int orderBoxQty=Integer.parseInt(getCellValue(i,1).toString());
                int orderItemQty=Integer.parseInt(getCellValue(i,2).toString());
                int freeBoxCount = 0;
                int freeItemCount = 0;

                int indexFreeBox = isAlreadyExistsInFreeItems(itemCode);

                if(indexFreeBox!=-1){
                    for (int k=0; k<addFreeTbl.getItems().size(); k++){
                        FreeItemsTM rowDisData = addFreeTbl.getItems().get(k);
                        freeBoxCount = rowDisData.getFreeBoxQty();
                        freeItemCount = rowDisData.getFreeItemQty();
                    }
                }
                OrderDetail orderDetail=new OrderDetail(lblOrderId.getText(), itemCode, unitPrice_Box, orderBoxQty, orderItemQty, freeBoxCount, freeItemCount);
                orderDetailList.add(orderDetail);
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            OrderDTO orderDTO=new OrderDTO(lblOrderId.getText(), format.parse(lblDate.getText()),shopIdlbl.getText());
            boolean isAdded = orderBO.saveOrder(orderDTO);

            if (isAdded) {
                int k = 0;
                for (OrderDetail orderDetail:orderDetailList) {
                    k++;
                    OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(
                            lblOrderId.getText(),
                            orderDetail.getItemCode(),
                            orderDetail.getUnitPrice_Box(),
                            orderDetail.getBoxQty(),
                            orderDetail.getItemQty(),
                            orderDetail.getBoxQtyFree(),
                            orderDetail.getItemQtyFree()
                    );
                    if(orderDetailBO.saveOrderDetails(orderDetailsDTO)){
                        if(k==(orderDetailList.size())){
                            if(savePayment()){
                                if(shopBO.updateShopCredit(shopIdlbl.getText(),Double.parseDouble(shopCreditUptoNowlbl.getText()))){
                                    new Alert(Alert.AlertType.CONFIRMATION,"Order was Added", ButtonType.OK).show();
                                }else {
                                    new Alert(Alert.AlertType.WARNING,"Something went wrong about Credit! Please try again.",ButtonType.CANCEL).show();
                                }
                            }else {
                                new Alert(Alert.AlertType.WARNING,"Something went wrong about Payment! Please try again.",ButtonType.CANCEL).show();
                            }
                        }
                    }else {
                        new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                    }
                }
                discountGeneratedId=1;
            }
        }else {
            new Alert(Alert.AlertType.WARNING,"Please Add Sufficient Payment to Proceed...",ButtonType.OK).show();
        }
    }

    private boolean savePayment() throws SQLException, ClassNotFoundException {
        boolean rst = true;
        if(chequecbx.isSelected()){
            String paymentDetailsCheque = "Bank : " + banktxt.getText() + '\'' + "Cheque Num" + chequeNumtxt.getText();
            PaymentDTO paymentDTO = new PaymentDTO(lblOrderId.getText(),paymentDetailsCheque,"Cheque",Double.parseDouble(chequeAmounttxt.getText()));
            rst = paymentBO.savePayment(paymentDTO);
            if(rst==false) return false;
        }

        if(cashcbx.isSelected()){
            String paymentDetailsCash = "";
            PaymentDTO paymentDTO = new PaymentDTO(lblOrderId.getText(),paymentDetailsCash,"Cash",Double.parseDouble(cashAmounttxt.getText()));
            rst = paymentBO.savePayment(paymentDTO);
            if(rst==false) return false;
        }

        if(creditcbx.isSelected()){
            String paymentDetailsCash = "";
            PaymentDTO paymentDTO = new PaymentDTO(lblOrderId.getText(),paymentDetailsCash,"Credit",Double.parseDouble(lblCredit.getText()));
            rst = paymentBO.savePayment(paymentDTO);
            if(rst==false) return false;
        }
        return true;
    }

    public void removeOnAction(ActionEvent actionEvent) {
        obList.remove(orderTM);
        lblTotal.setText(String.valueOf(calculateTotalValue()));
    }

    private String formatDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    private ObservableList<String> loadAllItemCodes() throws SQLException, ClassNotFoundException {
        ObservableList<String> itemCodesObList = FXCollections.observableArrayList();;
        ArrayList<ItemDTO> itemDTOArrayList = itemBO.getAllItems("%"+""+"%");

        for (ItemDTO itemDTO:itemDTOArrayList) {
            itemCodesObList.add(itemDTO.getCode());
        }

        return itemCodesObList;
    }

    private int isAlreadyExists(String itemCode){
        for(int i = 0; i<addOrderTbl.getItems().size(); i++){
            if(getCellValue(i,0).toString().equals(itemCode)){
                return i;
            }
        }
        return -1;
    }

    private int isAlreadyExistsInReturnTbl(String itemCode){
        for(int i = 0; i<tblReturn.getItems().size(); i++){
            if(getCellValueReturn(i,0).toString().equals(itemCode)){
                return i;
            }
        }
        return -1;
    }

    private int isAlreadyExistsInFreeItems(String itemCode){
        for(int i = 0; i<addFreeTbl.getItems().size(); i++){
            if(getCellValue(i,0).toString().equals(itemCode)){
                return i;
            }
        }
        return -1;
    }

    private int isAlreadyExistsInFreeDiscount(String itemCode){
        for(int i = 0; i<tblDiscount.getItems().size(); i++){
            if(getCellValue(i,0).toString().equals(itemCode)){
                return i;
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


    public Object getCellValueReturn(int rowIndex, int columnIndex) {
        // Step 1: Retrieve the row's data (item) from the TableView's items
        ReturnStockTM rowData = tblReturn.getItems().get(rowIndex);

        // Step 2: Retrieve the TableColumn for the given column index
        TableColumn<ReturnStockTM, ?> column = tblReturn.getColumns().get(columnIndex);

        // Step 3: Get the value from the cell using the column's CellDataFeatures
        ObservableValue<?> cellValue = column.getCellObservableValue(rowData);

        // Step 4: Return the cell value
        return cellValue.getValue();
    }

    public Object getCellValueDiscount(int rowIndex, int columnIndex) {
        // Step 1: Retrieve the row's data (item) from the TableView's items
        DiscountItemsTM rowData = tblDiscount.getItems().get(rowIndex);

        // Step 2: Retrieve the TableColumn for the given column index
        TableColumn<DiscountItemsTM, ?> column = tblDiscount.getColumns().get(columnIndex);

        // Step 3: Get the value from the cell using the column's CellDataFeatures
        ObservableValue<?> cellValue = column.getCellObservableValue(rowData);

        // Step 4: Return the cell value
        return cellValue.getValue();
    }

    public double calculateTotalValue(){
        double total = 0.00;
        for(int i = 0; i<addOrderTbl.getItems().size(); i++){
            total += Double.parseDouble(getCellValue(i,3).toString());
        }
        total -= calculateTotalValueFree();
        total -= calculateTotalDiscount();
        return total;
    }

    public double calculateTotalValueFree(){
        double total = 0.00;
        for(int i = 0; i<addFreeTbl.getItems().size(); i++){
            total += Double.parseDouble(getCellValue(i,3).toString());
        }
        return total;
    }

    public double calculateTotalDiscount(){
        double total = 0.00;
        System.out.println("discountGeneratedId   " +discountGeneratedId);
        for(int i=1; i<discountGeneratedId; i++){
            System.out.println("iiii    "   + i);
            try {
                ArrayList<DiscountDTO> discountDTOArrayList = new ArrayList<>();
                discountDTOArrayList = discountBO.getAllDiscountByIdDup(lblOrderId.getText().toString(),String.valueOf(i));
                double disPercent = discountDTOArrayList.get(0).getDiscountValue();
                for (DiscountDTO discountDTO:discountDTOArrayList) {
                    System.out.println("discountDTO        " + discountDTO);
                    System.out.println("isAlreadyExists(discountDTO.getItemCode())        " + isAlreadyExists(discountDTO.getItemCode()));
                    System.out.println("isAlreadyExists(discountDTO.getItemCode())        " + discountDTO.getItemCode());
                    int boxCountInTable = Integer.parseInt(getCellValue(isAlreadyExists(discountDTO.getItemCode()),1).toString());
                    System.out.println("boxCountInTable   "  +boxCountInTable);
                    int itemsCountInTable = Integer.parseInt(getCellValue(isAlreadyExists(discountDTO.getItemCode()),2).toString());
                    System.out.println("itemsCountInTable   " + itemsCountInTable);
                    ItemDTO itemDTO = itemBO.getItem(getCellValue(isAlreadyExists(discountDTO.getItemCode()),0).toString());
                    int items_per_Box = itemDTO.getItemCountInBox();
                    System.out.println("items_per_Box  " + items_per_Box);
                    double per_box_price = itemDTO.getUnitPrice_Box();
                    System.out.println("per_box_price   " +per_box_price);
                    OrderDetailsDTO orderDetailsDTO = orderDetailBO.getOrderDetail(lblOrderId.getText(),itemDTO.getCode());
                    int freeBoxcount = 0;
                    int freeItemcount = 0;
                    if(orderDetailsDTO != null){
                        System.out.println("orderDetailsDTO   " + orderDetailsDTO);
                        System.out.println("lblOrderId.getText()   " + lblOrderId.getText());
                        System.out.println("itemDTO.getCode()   " + itemDTO.getCode());
                        freeBoxcount = orderDetailsDTO.getBoxQtyFree();
                        System.out.println("freeBoxcount   " + freeBoxcount);
                        freeItemcount = orderDetailsDTO.getItemQtyFree();
                    }
                    System.out.println("freeItemcount   " +freeItemcount);
                    System.out.println("boxCountInTable  " + boxCountInTable);
                    System.out.println("itemsCountInTable   " + itemsCountInTable);
                    System.out.println("items_per_Box   " + items_per_Box);
                    System.out.println("per_box_price   " + per_box_price);
                    System.out.println("freeBoxcount   " + freeBoxcount);
                    System.out.println("freeItemcount   " + freeItemcount);
                    total += ((per_box_price*(boxCountInTable-freeBoxcount)) + (per_box_price/items_per_Box)*(itemsCountInTable-freeItemcount))*((disPercent)/100);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(total + "     111111111111");
        return total;
    }
    public void addFreeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        int freeboxCount;
        int freeitemCount;

        if(txtBoxQtyFree.getText().isEmpty()){
            freeboxCount=0;
        }else{
            freeboxCount = Integer.parseInt(txtBoxQtyFree.getText());
        }

        if(txtItemQtyFree.getText().isEmpty()){
            freeitemCount=0;
        }else {
            freeitemCount = Integer.parseInt(txtItemQtyFree.getText());
        }

        double unitPrice_Box = itemBO.getItemByName(cmbFreeItemCode.getValue().toString()).getUnitPrice_Box();
        int itemCountInBox = itemBO.getItemByName(cmbFreeItemCode.getValue().toString()).getItemCountInBox();
        double total = (unitPrice_Box * freeboxCount) + (unitPrice_Box/itemCountInBox)*freeitemCount;

        if(freeboxCount==0 && freeitemCount==0){
            new Alert(Alert.AlertType.WARNING,"Please Add Some Count",ButtonType.CANCEL).show();
        }else {
            int rowIndex = isAlreadyExistsInFreeItems(cmbFreeItemCode.getValue().toString());
            Button btn = new Button("Remove");


            if(rowIndex!=-1) {
                // Update the quantity and total of the existing item
                FreeItemsTM existingDiscountItem = obFreeList.get(rowIndex);
                existingDiscountItem.setFreeBoxQty(existingDiscountItem.getFreeBoxQty() + freeboxCount);
                existingDiscountItem.setFreeItemQty(existingDiscountItem.getFreeItemQty() + freeitemCount);
                existingDiscountItem.setDiscountItemTotal(existingDiscountItem.getDiscountItemTotal() + total);

                if(calculateTotalValue() < total){
                    new Alert(Alert.AlertType.WARNING,"Total will Minus",ButtonType.CANCEL).show();
                }else{
                    if((existingDiscountItem.getFreeBoxQty()+freeboxCount)>Integer.parseInt(getCellValue(isAlreadyExists(cmbFreeItemCode.getValue().toString()),1).toString())
                            || (existingDiscountItem.getFreeItemQty()+freeitemCount)>Integer.parseInt(getCellValue(isAlreadyExists(cmbFreeItemCode.getValue().toString()),2).toString())){
                        new Alert(Alert.AlertType.WARNING,"Cart Count Minumum",ButtonType.CANCEL).show();
                    }else{
                        // Force the table to refresh by setting the item to itself
                        obFreeList.set(rowIndex, existingDiscountItem);
                    }
                }
            }else{
                FreeItemsTM discountTM = new FreeItemsTM(cmbFreeItemCode.getValue().toString(),freeboxCount,freeitemCount,total,btn);
                if(calculateTotalValue() < total){
                    new Alert(Alert.AlertType.WARNING,"Total will Minus",ButtonType.CANCEL).show();
                }else{
                    if((freeboxCount)>Integer.parseInt(getCellValue(isAlreadyExists(cmbFreeItemCode.getValue().toString()),1).toString())
                            || (freeitemCount)>Integer.parseInt(getCellValue(isAlreadyExists(cmbFreeItemCode.getValue().toString()),2).toString())){
                        new Alert(Alert.AlertType.WARNING,"Cart Count Minumum",ButtonType.CANCEL).show();
                    }else{
                        obFreeList.add(discountTM);

                        //Delete------------------------
                        btn.setOnAction(e->{
                            Alert confirmation = new Alert(
                                    Alert.AlertType.CONFIRMATION,
                                    "ARE YOU SURE ?",
                                    ButtonType.YES,ButtonType.CANCEL
                            );
                            Optional<ButtonType> confirmState = confirmation.showAndWait();
                            if(confirmState.get().equals(ButtonType.YES)){
                                if(removeItemByCodeFree(freeItemsTM.getFreeItemCode())) {
                                    lblTotal.setText(String.valueOf(calculateTotalValue()));
                                    new Alert(Alert.AlertType.CONFIRMATION,"Free Item was Deleted", ButtonType.OK).show();

                                }else{
                                    new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                                }

                            }
                        });
                        //Delete------------------------

                        addFreeTbl.setItems(obFreeList);
                    }
                }
            }
            lblTotal.setText(String.valueOf(calculateTotalValue()));
        }
    }

    public boolean removeItemByCodeFree(String itemCode) {
        FreeItemsTM itemToRemove = null;
        for (FreeItemsTM item : obFreeList) {
            if (item.getFreeItemCode().equals(itemCode)) {
                itemToRemove = item;
                break;
            }
        }
        if (itemToRemove != null) {
            obFreeList.remove(itemToRemove);
            return true;
        }
        return false;
    }

    public boolean removeItemByCodeDiscount(String itemCode) {
        DiscountItemsTM itemToRemove = null;
        for (DiscountItemsTM item : obDisList) {
            if (item.getDiscountItemCode().equals(itemCode)) {
                itemToRemove = item;
                break;
            }
        }
        if (itemToRemove != null) {
            obDisList.remove(itemToRemove);
            return true;
        }
        return false;
    }

    public boolean removeItemByCodeReturnStock(String itemCode) {
        ReturnStockTM itemToRemove = null;
        for (ReturnStockTM item : obListReturn) {
            if (item.getItemCode().equals(itemCode)) {
                itemToRemove = item;
                break;
            }
        }
        if (itemToRemove != null) {
            obListReturn.remove(itemToRemove);
            return true;
        }
        return false;
    }



    public void addDiscountToTableOnAction(ActionEvent actionEvent) {
        int rowIndex = isAlreadyExistsInFreeDiscount(cmbDiscount.getValue().toString());

        if(rowIndex!=-1) {
            new Alert(Alert.AlertType.CONFIRMATION,"Already Added", ButtonType.OK).show();
        }else{
            Button btn = new Button("Remove");
            DiscountItemsTM discountItemsTM = new DiscountItemsTM(cmbDiscount.getValue().toString(),btn);
            obDisList.add(discountItemsTM);

            //Delete------------------------
            btn.setOnAction(e->{
                Alert confirmation = new Alert(
                        Alert.AlertType.CONFIRMATION,
                        "ARE YOU SURE ?",
                        ButtonType.YES,ButtonType.CANCEL
                );
                Optional<ButtonType> confirmState = confirmation.showAndWait();
                if(confirmState.get().equals(ButtonType.YES)){
                    if(removeItemByCodeDiscount(discountItemsTM.getDiscountItemCode())) {
                        new Alert(Alert.AlertType.CONFIRMATION,"Shop was Deleted", ButtonType.OK).show();
                    }else{
                        new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                    }

                }
            });
            //Delete------------------------

            tblDiscount.setItems(obDisList);
        }
    }

    public void addDiscountOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        for(int i=0; i<tblDiscount.getItems().size(); i++){
            System.out.println(tblDiscount.getItems().size());
            String itemCode = getCellValueDiscount(i,0).toString();
            ItemDTO itemDTO = itemBO.getItem(itemCode);
            DiscountDTO discountDTO = new DiscountDTO(discountGeneratedId,lblOrderId.getText(),itemDTO.getCode(),Double.parseDouble(txtDiscount.getText()));
            if(discountBO.saveDiscount(discountDTO)){
                if(i==(tblDiscount.getItems().size()-1)){
                    discountGeneratedId++;
                    obDisList.clear();
                    new Alert(Alert.AlertType.CONFIRMATION,"Discount was Added", ButtonType.OK).show();
                }
            }else {
                new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
            }
        }
        lblTotal.setText(String.valueOf(calculateTotalValue()));
    }


    public void chequeAmountMouse(MouseEvent mouseEvent) {
    }

    public void cashAmountMouse(MouseEvent mouseEvent) {
    }

    public void chequeOnAction(ActionEvent actionEvent) {
        if(!chequeAmounttxt.getText().isEmpty()){
            if(!chequecbx.isSelected()){
                paidAmountlbl.setText(String.valueOf(Double.parseDouble(paidAmountlbl.getText()) - Double.parseDouble(chequeAmounttxt.getText())));
                double balanceAmount = Double.parseDouble(lblTotal.getText())-Double.parseDouble(paidAmountlbl.getText());
                balanceAmountlbl.setText(String.valueOf(balanceAmount));
                editCreditBalance();

                chequeAmounttxt.clear();
            }else {
                double temp = 0.0;
                if(cashcbx.isSelected()){
                    temp = Double.parseDouble(cashAmounttxt.getText());
                }
                paidAmountlbl.setText(String.valueOf(Double.parseDouble(chequeAmounttxt.getText()) + temp));
                double balanceAmount = Double.parseDouble(lblTotal.getText())-Double.parseDouble(paidAmountlbl.getText());
                balanceAmountlbl.setText(String.valueOf(balanceAmount));
                editCreditBalance();
                }
        }
    }

    public void cashOnAction(ActionEvent actionEvent) {
        if(!cashAmounttxt.getText().isEmpty()){
            if(!cashcbx.isSelected()){
                paidAmountlbl.setText(String.valueOf(Double.parseDouble(paidAmountlbl.getText()) - Double.parseDouble(cashAmounttxt.getText())));
                double balanceAmount = Double.parseDouble(lblTotal.getText())-Double.parseDouble(paidAmountlbl.getText());
                balanceAmountlbl.setText(String.valueOf(balanceAmount));
                editCreditBalance();

                cashAmounttxt.clear();
            }else{
                double temp = 0.0;
                if(chequecbx.isSelected()){
                    temp = Double.parseDouble(chequeAmounttxt.getText());
                }
                paidAmountlbl.setText(String.valueOf(Double.parseDouble(cashAmounttxt.getText()) + temp));
                double balanceAmount = Double.parseDouble(lblTotal.getText())-Double.parseDouble(paidAmountlbl.getText());
                balanceAmountlbl.setText(String.valueOf(balanceAmount));
                editCreditBalance();
                }
        }
    }

    public void creditOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        double uptoNowCredit = shopBO.getShop(shopIdlbl.getText()).getCredit_uptoNow();;
        if(creditcbx.isSelected()){
            lblCredit.setText(String.valueOf(Double.parseDouble(lblTotal.getText()) - Double.parseDouble(paidAmountlbl.getText())));
            shopCreditUptoNowlbl.setText(String.valueOf(uptoNowCredit + Double.parseDouble(lblCredit.getText())));
        }else {
            lblCredit.setText("0.00");
            shopCreditUptoNowlbl.setText(String.valueOf(uptoNowCredit));
        }
    }

    public void addReturnToTableOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        int boxCount;
        int itemCount;

        if(txtBoxQtyReturn.getText().isEmpty()){
            boxCount=0;
        }else{
            boxCount = Integer.parseInt(txtBoxQtyReturn.getText());
        }

        if(txtItemQtyReturn.getText().isEmpty()){
            itemCount=0;
        }else {
            itemCount = Integer.parseInt(txtItemQtyReturn.getText());
        }

        if(boxCount==0 && itemCount==0){
            new Alert(Alert.AlertType.CONFIRMATION,"Please Add Some Stock", ButtonType.OK).show();
        }else {
            double unitPrice_Box = itemBO.getItemByName(cmbReturnItem.getValue().toString()).getUnitPrice_Box();
            int itemCountInBox = itemBO.getItemByName(cmbReturnItem.getValue().toString()).getItemCountInBox();
            returnTotal += (unitPrice_Box * boxCount) + (unitPrice_Box/itemCountInBox)*itemCount;

            int rowIndex = isAlreadyExistsInReturnTbl(cmbReturnItem.getValue().toString());

            if(rowIndex!=-1) {
                // Update the quantity and total of the existing item
                ReturnStockTM existingItem = obListReturn.get(rowIndex);
                existingItem.setBoxQty(existingItem.getBoxQty() + boxCount);
                existingItem.setItemQty(existingItem.getItemQty() + itemCount);

                // Force the table to refresh by setting the item to itself
                obListReturn.set(rowIndex, existingItem);
            }else{
                Button btn = new Button("Remove");
                ReturnStockTM returnStockTM = new ReturnStockTM(cmbReturnItem.getValue().toString(),boxCount,itemCount,btn);

                //Delete------------------------
                btn.setOnAction(e->{
                    Alert confirmation = new Alert(
                            Alert.AlertType.CONFIRMATION,
                            "ARE YOU SURE ?",
                            ButtonType.YES,ButtonType.CANCEL
                    );
                    Optional<ButtonType> confirmState = confirmation.showAndWait();
                    if(confirmState.get().equals(ButtonType.YES)){
                        if(removeItemByCodeReturnStock(returnStockTM.getItemCode())) {
                            returnTotal-=((unitPrice_Box * returnStockTM.getBoxQty()) + (unitPrice_Box/itemCountInBox)*returnStockTM.getItemQty());
                            returnTotallbl.setText(String.valueOf(returnTotal));
                            new Alert(Alert.AlertType.CONFIRMATION,"Return Item was Deleted", ButtonType.OK).show();
                        }else{
                            new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                        }

                    }
                });
                //Delete------------------------

                obListReturn.add(returnStockTM);
                tblReturn.setItems(obListReturn);

            }
            returnTotallbl.setText(String.valueOf(returnTotal));
        }
    }

    public void addReurnStockOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        for (int i=0; i<tblReturn.getItems().size(); i++){
            ReturnStockDTO returnStockDTO = new ReturnStockDTO(
                    lblOrderId.getText(),
                    getCellValueReturn(i,0).toString(),
                    Integer.parseInt(getCellValueReturn(i,1).toString()),
                    Integer.parseInt(getCellValueReturn(i,2).toString())
            );
            if(returnStockBO.saveReturn(returnStockDTO)){
                if(i==tblReturn.getItems().size()-1){
                    new Alert(Alert.AlertType.CONFIRMATION,"Return Item was Added...", ButtonType.OK).show();
                    obListReturn.clear();
                }
            }else {
                new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                break;
            }
        }
    }
}