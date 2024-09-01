package lk.crossorigin.agency.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.crossorigin.agency.bo.custom.*;
import lk.crossorigin.agency.bo.custom.impl.*;
import lk.crossorigin.agency.db.DBConnection;
import lk.crossorigin.agency.dto.*;
import lk.crossorigin.agency.view.tm.MonthlyReportTM;
import lk.crossorigin.agency.view.tm.OrderDetailsTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class OrderDetailsFormController {
    public AnchorPane orderHistoryContext;
    public JFXButton btnBack;
    public TableView<OrderDetailsTM> orderHistoryTbl;
    public TableColumn colNum;
    public TableColumn colInvNo;
    public TableColumn colNameodDealer;
    public TableColumn colTotal;
    public TableColumn colCash;
    public TableColumn colCredit;
    public TableColumn colCheque;
    public TableColumn colChequeNo;
    public TableColumn colMR;
    public TableColumn colDiscount;
    public JFXButton btnSearch;
    public JFXButton btnPrint;
    public TextField txtSearch;


    OrderDetailBO orderDetailBO = new OrderDetailBoImpl();
    OrderBookBO orderBookBO = new OrderBookBoImpl();
    ItemBO itemBO = new ItemBoImpl();
    PaymentBO paymentBO = new PaymentBoImpl();
    ReturnStockBO returnStockBO = new ReturnStockBoImpl();
    DiscountBO discountBO = new DiscountBoImpl();
    OrderHistoryBO orderHistoryBO = new OrderHistoryBoImpl();

    public void initialize(){
        colNum.setCellValueFactory(new PropertyValueFactory<>("no"));
        colInvNo.setCellValueFactory(new PropertyValueFactory<>("invNo"));
        colNameodDealer.setCellValueFactory(new PropertyValueFactory<>("NameofDealer"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colCash.setCellValueFactory(new PropertyValueFactory<>("cash"));
        colCredit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        colCheque.setCellValueFactory(new PropertyValueFactory<>("cheque"));
        colChequeNo.setCellValueFactory(new PropertyValueFactory<>("chequeNum"));
        colMR.setCellValueFactory(new PropertyValueFactory<>("MR"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        loadAllDetails("");
    }

    private void loadAllDetails(String searchText){
        ObservableList<OrderDetailsTM> obList = FXCollections.observableArrayList();
        try {
            double total = 0.00;
            double freeItemTotal = 0.00;

            ArrayList<OrderBookDTO> dtoList = orderBookBO.getAllOrderBooks("%" + searchText + "%");
            for (OrderBookDTO dto:dtoList) {
                ArrayList<OrderDetailsDTO> OrderDetailsDTO = orderDetailBO.getAllOrderDetailsByOrderId(dto.getId());
                /*for (OrderDetailsDTO o:OrderDetailsDTO) {
                    System.out.println("-----------------------------------");
                    System.out.println(o);
                    double unit_boxPrice = o.getUnitPrice_Box();
                    int freebox = o.getBoxQtyFree();
                    int freeitem = o.getItemQtyFree();
                    int itemsCount_in_box = itemBO.getItem(o.getItemCode()).getItemCountInBox();
                    double per_item_Price = 0.0;
                    if(itemsCount_in_box==0){
                        per_item_Price = o.getUnitPrice_Box();
                    }else{
                        per_item_Price = o.getUnitPrice_Box()/itemsCount_in_box;
                    }
                    total=(unit_boxPrice*o.getBoxQty() + per_item_Price*o.getItemQty());
                    System.out.println(freebox);
                    System.out.println(freeitem);
                    System.out.println(unit_boxPrice);
                    freeItemTotal = freebox*unit_boxPrice + freeitem*per_item_Price;
                }*/

                ArrayList<PaymentDTO> paymentDTOS = paymentBO.getPaymentByOrderId(dto.getId());
                double cashAmount = 0.00;
                double chequeAmount = 0.00;
                double creditAmount = 0.00;
                String chequeNum = "";

                for (PaymentDTO p:paymentDTOS) {
                    if(p.getPayment_Way().equals("Cheque")){
                        chequeAmount = p.getAmount();
                        chequeNum = p.getPayment_Details();
                    }else if(p.getPayment_Way().equals("Cash")){
                        cashAmount = p.getAmount();
                    }else{
                        creditAmount = p.getAmount();
                    }
                }

                /*ArrayList<ReturnStockDTO> returnByOrderId = returnStockBO.getReturnByOrderId(dto.getId());
                double returnAmount = 0.00;
                for (ReturnStockDTO r:returnByOrderId) {
                    ItemDTO itemDTO = itemBO.getItem(r.getItemCode());
                    double unit_QTYPrice = r.getPerQty();
                    int boxQTY = r.getBoxQty();
                    int itemQTY = r.getItemQty();
                    int itemsCount_in_box = itemDTO.getItemCountInBox();
                    returnAmount+=(unit_QTYPrice*itemsCount_in_box*boxQTY + unit_QTYPrice*itemQTY);
                }*/

                ArrayList<DiscountDTO> allDiscountByOrderId = discountBO.getAllDiscountByOrderId(dto.getId());
                int dupCount = 1;
                for (DiscountDTO d:allDiscountByOrderId) {
                    if(dupCount!=d.getIdDup()){
                        dupCount++;
                    }
                }
                /*double finalDisValue = 0.00;
                for(int i=0; i<dupCount && allDiscountByOrderId.size()>0; i++){
                    ArrayList<DiscountDTO> allDiscountByIdDup = discountBO.getAllDiscountByIdDup(dto.getId(), String.valueOf(i + 1));
                    double totalDis = 0.00;
                    for (DiscountDTO d:allDiscountByIdDup) {
                        ItemDTO itemDTO = itemBO.getItem(d.getItemCode());
                        double unit_boxPrice = itemDTO.getUnitPrice_Box();
                        int itemsCount_in_box = itemDTO.getItemCountInBox();
                        double per_item_Price = itemDTO.getUnitPrice_Box()/itemsCount_in_box;

                        OrderDetailsDTO orderDetail = orderDetailBO.getOrderDetail(dto.getId(), d.getItemCode());

                        totalDis+=(unit_boxPrice*orderDetail.getBoxQty() + per_item_Price*orderDetail.getItemQty());
                    }
                    System.out.println(allDiscountByIdDup.get(0));
                    finalDisValue+=totalDis*(allDiscountByIdDup.get(0).getDiscountValue())/100;
                }*/


                System.out.println("000000000000000000000000000000000000000");
                System.out.println(dto.getId());
                System.out.println( orderDetailBO.getAllOrderDetailsByOrderId(dto.getId()));
                OrderDetailsTM orderDetailsTM = new OrderDetailsTM(
                        dto.getOb_id(),
                        dto.getInvId(),
                        dto.getShopId(),
                        orderDetailBO.getAllOrderDetailsByOrderId(dto.getId()).get(0).getTotal(),
                        cashAmount,
                        creditAmount,
                        chequeAmount,
                        chequeNum,
                        orderDetailBO.getAllOrderDetailsByOrderId(dto.getId()).get(0).getReturn_tot(),
                        orderDetailBO.getAllOrderDetailsByOrderId(dto.getId()).get(0).getDis_tot() + "+" + orderDetailBO.getAllOrderDetailsByOrderId(dto.getId()).get(0).getFree_total() + " = " + (orderDetailBO.getAllOrderDetailsByOrderId(dto.getId()).get(0).getDis_tot() + orderDetailBO.getAllOrderDetailsByOrderId(dto.getId()).get(0).getFree_total())
                );
                obList.add(orderDetailsTM);
            }
            orderHistoryTbl.setItems(obList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void backbtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) orderHistoryContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
    }

    public void searchOnAction(ActionEvent actionEvent) {
        loadAllDetails(txtSearch.getText());
    }

    public Object getCellValueReturn(int rowIndex, int columnIndex) {
        // Step 1: Retrieve the row's data (item) from the TableView's items
        OrderDetailsTM rowData = orderHistoryTbl.getItems().get(rowIndex);

        // Step 2: Retrieve the TableColumn for the given column index
        TableColumn<OrderDetailsTM, ?> column = orderHistoryTbl.getColumns().get(columnIndex);

        // Step 3: Get the value from the cell using the column's CellDataFeatures
        ObservableValue<?> cellValue = column.getCellObservableValue(rowData);

        // Step 4: Return the cell value
        return cellValue.getValue();
    }

    public void printOnAction(ActionEvent actionEvent) {
        try {
            if(orderHistoryBO.deleteOrderHistory()){
                for (int i=0; i<orderHistoryTbl.getItems().size(); i++){
                    OrderHistoryDTO orderHistoryDTO = new OrderHistoryDTO(
                            getCellValueReturn(i,1).toString(),
                            getCellValueReturn(i,2).toString(),
                            Double.parseDouble(getCellValueReturn(i,3).toString()),
                            Double.parseDouble(getCellValueReturn(i,4).toString()),
                            Double.parseDouble(getCellValueReturn(i,5).toString()),
                            Double.parseDouble(getCellValueReturn(i,6).toString()),
                            getCellValueReturn(i,7).toString(),
                            Double.parseDouble(getCellValueReturn(i,8).toString()),
                            getCellValueReturn(i,9).toString()
                    );
                    if(orderHistoryBO.saveOrderHistory(orderHistoryDTO)){
                        if(i==orderHistoryTbl.getItems().size()-1){
                            JasperDesign design = JRXmlLoader.load("src/lk/crossorigin/agency/reports/Order_History.jrxml");
                            JasperReport jasperReport = JasperCompileManager.compileReport(design);
                            Connection conn = DBConnection.getInstance().getConnection();
                            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), conn);
                            JasperViewer.viewReport(jasperPrint, false);                    }
                    }else {
                        new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                        break;
                    }
                }
            }else{
                new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
            }
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