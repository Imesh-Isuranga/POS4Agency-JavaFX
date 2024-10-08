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
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

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
    public Label lbltotal;
    public Label lblcash;
    public Label lblcredit;
    public Label lblcheque;
    public Label lblmr;
    public Label lbldis;
    public TableColumn colDate;
    public DatePicker datePicker;
    public JFXButton btnDate;

    public Date selected_date;
    public TableColumn colView;
    public JFXButton btnDelete;

    public OrderDetailsTM selectedOrderDetailsTM;

    OrderDetailBO orderDetailBO = new OrderDetailBoImpl();
    OrderBookBO orderBookBO = new OrderBookBoImpl();
    ItemBO itemBO = new ItemBoImpl();
    PaymentBO paymentBO = new PaymentBoImpl();
    ReturnStockBO returnStockBO = new ReturnStockBoImpl();
    DiscountBO discountBO = new DiscountBoImpl();
    OrderHistoryBO orderHistoryBO = new OrderHistoryBoImpl();
    ShopCreditBO shopCreditBO = new ShopCreditBOImpl();
    SelectedOrderBO selectedOrderBO = new SelectedOrderBoImpl();

    public void initialize() throws SQLException, ClassNotFoundException {
        colNum.setCellValueFactory(new PropertyValueFactory<>("no"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colInvNo.setCellValueFactory(new PropertyValueFactory<>("invNo"));
        colNameodDealer.setCellValueFactory(new PropertyValueFactory<>("NameofDealer"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colCash.setCellValueFactory(new PropertyValueFactory<>("cash"));
        colCredit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        colCheque.setCellValueFactory(new PropertyValueFactory<>("cheque"));
        colChequeNo.setCellValueFactory(new PropertyValueFactory<>("chequeNum"));
        colMR.setCellValueFactory(new PropertyValueFactory<>("MR"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colView.setCellValueFactory(new PropertyValueFactory<>("viewBtn"));

        selectedOrderBO.deleteOrderId();

        loadAllDetails("");

        datePicker.setOnAction(event -> {
            selected_date = java.sql.Date.valueOf(datePicker.getValue());
            System.out.println("Selected Date: " + selected_date);
            // You can also call any other methods here if you want to do something with the selected date
        });

        orderHistoryTbl.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                selectedOrderDetailsTM = newValue;
            }
        });
    }


    private double getTotalCreditByDate(Date date){
        try {
            double totalCreditByDate = 0.00;
            ArrayList<ShopCreditDTO> itemByDate = shopCreditBO.getItemByDate(date);
            for (ShopCreditDTO shopCreditDTO:itemByDate) {
                totalCreditByDate += shopCreditDTO.getAmount();
            }
            return totalCreditByDate;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }


    private void loadAllDetailsByDate(Date date){
        ObservableList<OrderDetailsTM> obList = FXCollections.observableArrayList();
        try {
            double total = 0.00;
            double freeItemTotal = 0.00;

            double totalsum = 0.00;
            double cash = 0.00;
            double credit = 0.00;
            double cheque = 0.00;
            double mr = 0.00;
            double discount = 0.00;


            ArrayList<OrderDetailsDTO> OrderDetailsDTO = orderDetailBO.getOrderDetailsByDate(date);
            String or_id = "";
            for (OrderDetailsDTO dto:OrderDetailsDTO) {
                if(!dto.getOrderId().equals("") && dto.getOrderId().equals(or_id)){
                    continue;
                }
                or_id = dto.getOrderId();

                ArrayList<PaymentDTO> paymentDTOS = paymentBO.getPaymentByOrderId(dto.getOrderId());
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

                ArrayList<DiscountDTO> allDiscountByOrderId = discountBO.getAllDiscountByOrderId(dto.getOrderId());
                int dupCount = 1;
                for (DiscountDTO d:allDiscountByOrderId) {
                    if(dupCount!=d.getIdDup()){
                        dupCount++;
                    }
                }

                totalsum += orderDetailBO.getAllOrderDetailsByOrderId(dto.getOrderId()).get(0).getTotal();
                cash += cashAmount;
                credit += creditAmount;
                cheque += chequeAmount;
                mr += orderDetailBO.getAllOrderDetailsByOrderId(dto.getOrderId()).get(0).getReturn_tot();
                discount += orderDetailBO.getAllOrderDetailsByOrderId(dto.getOrderId()).get(0).getDis_tot() + orderDetailBO.getAllOrderDetailsByOrderId(dto.getOrderId()).get(0).getFree_total();


                totalsum = Math.round(totalsum * 100.0) / 100.0;
                cash = Math.round(cash * 100.0) / 100.0;
                credit = Math.round(credit * 100.0) / 100.0;
                cheque = Math.round(cheque * 100.0) / 100.0;
                mr = Math.round(mr * 100.0) / 100.0;
                discount = Math.round(discount * 100.0) / 100.0;

                System.out.println("000000000000000000000000000000000000000");
                System.out.println(dto.getOrderId());
                OrderBookDTO orderBookDTO = orderBookBO.getOrderBook(dto.getOrderId());
                System.out.println(orderDetailBO.getAllOrderDetailsByOrderId(dto.getOrderId()));
                Button viewBtn = new Button("View");
                OrderDetailsTM orderDetailsTM = new OrderDetailsTM(
                        orderBookDTO.getOb_id(),
                        orderDetailBO.getAllOrderDetailsByOrderId(dto.getOrderId()).get(0).getOrderDate(),
                        orderBookDTO.getInvId(),
                        orderBookDTO.getShopId(),
                        orderDetailBO.getAllOrderDetailsByOrderId(dto.getOrderId()).get(0).getTotal(),
                        cashAmount,
                        creditAmount,
                        chequeAmount,
                        chequeNum,
                        orderDetailBO.getAllOrderDetailsByOrderId(dto.getOrderId()).get(0).getReturn_tot(),
                        orderDetailBO.getAllOrderDetailsByOrderId(dto.getOrderId()).get(0).getDis_tot() + "+" + orderDetailBO.getAllOrderDetailsByOrderId(dto.getOrderId()).get(0).getFree_total() + " = " + (orderDetailBO.getAllOrderDetailsByOrderId(dto.getOrderId()).get(0).getDis_tot() + orderDetailBO.getAllOrderDetailsByOrderId(dto.getOrderId()).get(0).getFree_total()),
                        viewBtn,
                        orderBookDTO.getId()
                );
                obList.add(orderDetailsTM);

                //View------------------------
                viewBtn.setOnAction(e->{
                    try {
                        if(selectedOrderBO.saveOrderId(new SelectedOrderDTO(String.valueOf(orderBookDTO.getId())))){
                            Stage stage = (Stage) orderHistoryContext.getScene().getWindow();
                            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ViewOrderForm.fxml"))));
                        }else{
                            new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                        }

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                //View------------------------

            }
            lbltotal.setText(String.valueOf(totalsum));
            lblcash.setText(String.valueOf(cash + getTotalCreditByDate(date)));
            lblcredit.setText(String.valueOf(credit));
            lblcheque.setText(String.valueOf(cheque));
            lblmr.setText(String.valueOf(mr));
            lbldis.setText(String.valueOf(discount));

            orderHistoryTbl.setItems(obList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAllDetails(String searchText){
        ObservableList<OrderDetailsTM> obList = FXCollections.observableArrayList();
        try {
            double total = 0.00;
            double freeItemTotal = 0.00;

            double totalsum = 0.00;
            double cash = 0.00;
            double credit = 0.00;
            double cheque = 0.00;
            double mr = 0.00;
            double discount = 0.00;

            ArrayList<OrderBookDTO> dtoList = orderBookBO.getAllOrderBooks("%" + searchText + "%");
            for (OrderBookDTO dto:dtoList) {
                ArrayList<OrderDetailsDTO> OrderDetailsDTO = orderDetailBO.getAllOrderDetailsByOrderId(dto.getId());


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

                ArrayList<DiscountDTO> allDiscountByOrderId = discountBO.getAllDiscountByOrderId(dto.getId());
                int dupCount = 1;
                for (DiscountDTO d:allDiscountByOrderId) {
                    if(dupCount!=d.getIdDup()){
                        dupCount++;
                    }
                }

                totalsum += orderDetailBO.getAllOrderDetailsByOrderId(dto.getId()).get(0).getTotal();
                cash += cashAmount;
                credit += creditAmount;
                cheque += chequeAmount;
                mr += orderDetailBO.getAllOrderDetailsByOrderId(dto.getId()).get(0).getReturn_tot();
                discount += orderDetailBO.getAllOrderDetailsByOrderId(dto.getId()).get(0).getDis_tot() + orderDetailBO.getAllOrderDetailsByOrderId(dto.getId()).get(0).getFree_total();


                totalsum = Math.round(totalsum * 100.0) / 100.0;
                cash = Math.round(cash * 100.0) / 100.0;
                credit = Math.round(credit * 100.0) / 100.0;
                cheque = Math.round(cheque * 100.0) / 100.0;
                mr = Math.round(mr * 100.0) / 100.0;
                discount = Math.round(discount * 100.0) / 100.0;

                Button viewBtn = new Button("View");
                OrderDetailsTM orderDetailsTM = new OrderDetailsTM(
                        dto.getOb_id(),
                        orderDetailBO.getAllOrderDetailsByOrderId(dto.getId()).get(0).getOrderDate(),
                        dto.getInvId(),
                        dto.getShopId(),
                        orderDetailBO.getAllOrderDetailsByOrderId(dto.getId()).get(0).getTotal(),
                        cashAmount,
                        creditAmount,
                        chequeAmount,
                        chequeNum,
                        orderDetailBO.getAllOrderDetailsByOrderId(dto.getId()).get(0).getReturn_tot(),
                        orderDetailBO.getAllOrderDetailsByOrderId(dto.getId()).get(0).getDis_tot() + "+" + orderDetailBO.getAllOrderDetailsByOrderId(dto.getId()).get(0).getFree_total() + " = " + (orderDetailBO.getAllOrderDetailsByOrderId(dto.getId()).get(0).getDis_tot() + orderDetailBO.getAllOrderDetailsByOrderId(dto.getId()).get(0).getFree_total()),
                        viewBtn,
                        dto.getId()
                );
                obList.add(orderDetailsTM);

                //View------------------------
                viewBtn.setOnAction(e->{
                    try {
                        if(selectedOrderBO.saveOrderId(new SelectedOrderDTO(String.valueOf(dto.getId())))){
                            Stage stage = (Stage) orderHistoryContext.getScene().getWindow();
                            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ViewOrderForm.fxml"))));
                        }else{
                            new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                        }

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                //View------------------------
            }
            lbltotal.setText(String.valueOf(totalsum));
            lblcash.setText(String.valueOf(cash));
            lblcredit.setText(String.valueOf(credit));
            lblcheque.setText(String.valueOf(cheque));
            lblmr.setText(String.valueOf(mr));
            lbldis.setText(String.valueOf(discount));

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

    public void dateOnAction(ActionEvent actionEvent) {
        if(selected_date != null){
            loadAllDetailsByDate(selected_date);
        }else{
            new Alert(Alert.AlertType.WARNING,"Please enter date.",ButtonType.CANCEL).show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String or_id = selectedOrderDetailsTM.getOr_id();
        System.out.println("111111111111111111111111111111111111111111111111111");
        System.out.println(returnStockBO.deleteReturn(or_id));



        int count = 0;
        ArrayList<OrderDetailsDTO> allOrderDetailsByOrderId = orderDetailBO.getAllOrderDetailsByOrderId(or_id);



        Alert confirmation = new Alert(
                Alert.AlertType.CONFIRMATION,
                "ARE YOU SURE ?",
                ButtonType.YES,ButtonType.CANCEL
        );
        Optional<ButtonType> confirmState = confirmation.showAndWait();
        if(confirmState.get().equals(ButtonType.YES)){
            if(orderBookBO.deleteOrderBook(or_id)){
                for (OrderDetailsDTO orderDetailsDTO : allOrderDetailsByOrderId) {
                    count++;
                    int totBoxtoAddtoLorry = 0;
                    int totItemtoAddtoLorry = 0;
                    String itemCode = "";
                    int itemsCountInBox = 0;

                    totBoxtoAddtoLorry += orderDetailsDTO.getBoxQty() + orderDetailsDTO.getBoxQtyFree();
                    totItemtoAddtoLorry += orderDetailsDTO.getItemQty() + orderDetailsDTO.getItemQtyFree();
                    itemCode = orderDetailsDTO.getItemCode();
                    itemsCountInBox = itemBO.getItem(itemCode).getItemCountInBox();

                    if(totItemtoAddtoLorry >= itemsCountInBox){
                        totBoxtoAddtoLorry += (totItemtoAddtoLorry / itemsCountInBox);
                        totItemtoAddtoLorry = (totItemtoAddtoLorry % itemsCountInBox);
                    }

                    ItemDTO itemDTO = new ItemDTO(itemCode,totBoxtoAddtoLorry,totItemtoAddtoLorry);

                    if(itemBO.updateItemQtysIncrease(itemDTO)){
                        if(count==allOrderDetailsByOrderId.size()){
                            if(returnStockBO.getReturnByOrderId(or_id).size()>0){
                                if(returnStockBO.deleteReturn(or_id)){
                                    new Alert(Alert.AlertType.CONFIRMATION,"Succefully deleted Order", ButtonType.OK).show();
                                }else{
                                    new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                                }
                            }else{
                                new Alert(Alert.AlertType.CONFIRMATION,"Succefully deleted Order", ButtonType.OK).show();
                            }
                        }
                    }else{
                        new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                    }

                }            }else{
                new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
            }
        }

        loadAllDetails("");
    }
}