package lk.crossorigin.agency.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.crossorigin.agency.dto.*;
import lk.crossorigin.agency.view.tm.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class ViewOrderFormController {
    public AnchorPane addOrderContext;
    public JFXButton btnBack;
    public TableView<OrderTM> addOrderTbl;
    public TableColumn colItemCode;
    public TableColumn colBoxQty;
    public TableColumn colItemQty;
    public TableColumn colTotal;
    public Label lblOrderId;
    public Label lblDate;
    public Label lblTotal;
    public TableColumn colFreeItemCode;
    public TableColumn colFreeBoxCount;
    public TableColumn colFreeItemCount;
    public TableColumn colFreeItemTotal;
    public TableView<FreeItemsTM> addFreeTbl;
    public TableView<DiscountItemsinViewTM> tblDiscount;
    public TableColumn colDiscountItemCode;
    public Label lblCredit;
    public Label paidAmountlbl;
    public Label balanceAmountlbl;
    public Label shopCreditUptoNowlbl;
    public Label shopIdlbl;
    public TableView<ReturnStockTM> tblReturn;
    public TableColumn colItemCodeReturn;
    public TableColumn colReturnBoxQty;
    public TableColumn colReturnItemQty;
    public Label returnTotallbl;
    public Label lblFreeDis;
    public Label lblTotalwithoutAny;
    public Label lblFree;
    public Label lbldis;
    public TableColumn colPer_Qty;
    public TableColumn colPercentage;
    public Label lblCash;
    public Label lblCheque;
    public Label lblChequeDetails;

    DiscountBO discountBO = new DiscountBoImpl();
    ItemBO itemBO = new ItemBoImpl();
    MainItemBO mainItemBO = new MainItemBoImpl();
    OrderBO orderBO = new OrderBoImpl();
    OrderBookBO orderBookBO = new OrderBookBoImpl();
    OrderDetailBO orderDetailBO = new OrderDetailBoImpl();
    PaymentBO paymentBO = new PaymentBoImpl();
    ReturnStockBO returnStockBO = new ReturnStockBoImpl();
    ShopBO shopBO = new ShopBoImpl();
    SelectedOrderBO selectedOrderBO = new SelectedOrderBoImpl();

    String orderIdtoView = "";

    public void initialize() throws SQLException, ClassNotFoundException {
        orderIdtoView = selectedOrderBO.getOrderId();


        shopIdlbl.setText(orderBookBO.getOrderBook(orderIdtoView).getShopId());



        lblOrderId.setText(orderIdtoView);

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colBoxQty.setCellValueFactory(new PropertyValueFactory<>("boxQty"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("itemQty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        colFreeItemCode.setCellValueFactory(new PropertyValueFactory<>("freeItemCode"));
        colFreeBoxCount.setCellValueFactory(new PropertyValueFactory<>("freeBoxQty"));
        colFreeItemCount.setCellValueFactory(new PropertyValueFactory<>("freeItemQty"));
        colFreeItemTotal.setCellValueFactory(new PropertyValueFactory<>("discountItemTotal"));

        colDiscountItemCode.setCellValueFactory(new PropertyValueFactory<>("discountItemCode"));
        colPercentage.setCellValueFactory(new PropertyValueFactory<>("percentage"));


        colItemCodeReturn.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colReturnBoxQty.setCellValueFactory(new PropertyValueFactory<>("boxQty"));
        colReturnItemQty.setCellValueFactory(new PropertyValueFactory<>("itemQty"));
        colPer_Qty.setCellValueFactory(new PropertyValueFactory<>("perQty"));


        loadAllItems();
        loadAllDiscount();
        loadAllFree();
        loadAllReturn();
        calValues();


    }


    private void calValues() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailsDTO> dtoList = orderDetailBO.getAllOrderDetailsByOrderId(orderIdtoView);
        if(dtoList.size() > 0){
            lbldis.setText(String.valueOf(dtoList.get(0).getDis_tot()));
            returnTotallbl.setText(String.valueOf(dtoList.get(0).getReturn_tot()));
            double total = (Double.parseDouble(lblTotalwithoutAny.getText()))-((dtoList.get(0).getDis_tot())+(dtoList.get(0).getReturn_tot()));
            lblTotal.setText(String.valueOf(total));

            double paymentTot = 0.00;

            ArrayList<PaymentDTO> paymentByOrderId = paymentBO.getPaymentByOrderId(orderIdtoView);
            for (PaymentDTO dto:paymentByOrderId) {
                if(dto.getPayment_Way().equalsIgnoreCase("Cheque")){
                    lblCheque.setText(String.valueOf(dto.getAmount()));
                    lblChequeDetails.setText(dto.getPayment_Details());
                } else if (dto.getPayment_Way().equalsIgnoreCase("Cash")) {
                    lblCash.setText(String.valueOf(dto.getAmount()));
                }else{
                    lblCredit.setText(String.valueOf(dto.getAmount()));
                }
                paymentTot += dto.getAmount();
            }

            paidAmountlbl.setText(String.valueOf(paymentTot));
            balanceAmountlbl.setText(String.valueOf(total-paymentTot));

            lblFree.setText(String.valueOf(dtoList.get(0).getFree_total()));
            lblFreeDis.setText(String.valueOf((dtoList.get(0).getFree_total())+(dtoList.get(0).getDis_tot())));
        }
    }


    public void backbtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) addOrderContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/OrderDetailsForm.fxml"))));
    }

    public void loadAllItems(){
        ObservableList<OrderTM> obList = FXCollections.observableArrayList();
        try {
            ArrayList<OrderDetailsDTO> dtoList = orderDetailBO.getAllOrderDetailsByOrderId(orderIdtoView);
            double wholeTotal = 0.0;
            for (OrderDetailsDTO dto: dtoList) {
                double total = 0.0;
                if(itemBO.getItem(dto.getItemCode()).getItemCountInBox()==0){
                    total = dto.getUnitPrice_Box()*dto.getItemQty();
                }else{
                    total = dto.getUnitPrice_Box()*dto.getBoxQty() + (dto.getUnitPrice_Box()/itemBO.getItem(dto.getItemCode()).getItemCountInBox())*dto.getItemQty();
                }
                total = Math.round(total * 100.0) / 100.0;
                wholeTotal += total;

                OrderTM orderTM = new OrderTM(
                        dto.getItemCode(),
                        dto.getBoxQty(),
                        dto.getItemQty(),
                        total
                );
                obList.add(orderTM);
            }
            addOrderTbl.setItems(obList);
            wholeTotal = Math.round(wholeTotal * 100.0) / 100.0;
            lblTotalwithoutAny.setText(String.valueOf(wholeTotal));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    public void loadAllDiscount(){
        ObservableList<DiscountItemsinViewTM> obList = FXCollections.observableArrayList();
        try {
            ArrayList<DiscountDTO> dtoList = discountBO.getAllDiscountByOrderId(orderIdtoView);

            for (DiscountDTO dto:dtoList) {
                DiscountItemsinViewTM discountItemsinViewTM = new DiscountItemsinViewTM(
                        itemBO.getItem(dto.getItemCode()).getName(),
                        dto.getDiscountValue()
                );
                obList.add(discountItemsinViewTM);
            }
            tblDiscount.setItems(obList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadAllFree(){
        ObservableList<FreeItemsTM> obList = FXCollections.observableArrayList();
        try {
            ArrayList<OrderDetailsDTO> dtoList = orderDetailBO.getAllOrderDetailsByOrderId(orderIdtoView);
            for (OrderDetailsDTO dto: dtoList) {
                double total = 0.0;

                if(dto.getFree_total() > 0.00){
                    if(itemBO.getItem(dto.getItemCode()).getItemCountInBox()==0){
                        total = dto.getUnitPrice_Box()*dto.getItemQty();
                    }else{
                        total = dto.getUnitPrice_Box()*dto.getBoxQty() + (dto.getUnitPrice_Box()/itemBO.getItem(dto.getItemCode()).getItemCountInBox())*dto.getItemQty();
                    }
                    total = Math.round(total * 100.0) / 100.0;

                    FreeItemsTM freeItemsTM = new FreeItemsTM(
                            dto.getItemCode(),
                            dto.getBoxQtyFree(),
                            dto.getItemQtyFree(),
                            total
                    );
                    obList.add(freeItemsTM);
                }
            }
            addFreeTbl.setItems(obList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    public void loadAllReturn(){
        ObservableList<ReturnStockTM> obList = FXCollections.observableArrayList();
        try {
            ArrayList<ReturnStockDTO> dtoList = returnStockBO.getReturnByOrderId(orderIdtoView);

            for (ReturnStockDTO dto:dtoList) {
                ReturnStockTM returnStockTM = new ReturnStockTM(
                        itemBO.getItem(dto.getItemCode()).getName(),
                        dto.getBoxQty(),
                        dto.getItemQty(),
                        dto.getPerQty()
                );
                obList.add(returnStockTM);
            }
            tblReturn.setItems(obList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


}