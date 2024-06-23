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
import lk.crossorigin.agency.DataBaseAccessCode;
import lk.crossorigin.agency.dao.CrudUtil;
import lk.crossorigin.agency.db.DBConnection;
import lk.crossorigin.agency.dto.*;
import lk.crossorigin.agency.view.tm.OrderDetailsTM;
import lk.crossorigin.agency.view.tm.ShopTM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            System.out.println("111111111111111");
            double total = 0.00;
            ArrayList<OrderBookDTO> dtoList = new DataBaseAccessCode().getAllOrderBooks("%" + searchText + "%");
            for (OrderBookDTO dto:dtoList) {
                System.out.println("qqqqqqqqqq");
                ArrayList<OrderDetailsDTO> OrderDetailsDTO = new DataBaseAccessCode().getAllOrderDetailsByOrderId(dto.getId());
                for (OrderDetailsDTO o:OrderDetailsDTO) {
                    System.out.println(o);
                    double unit_boxPrice = o.getUnitPrice_Box();
                    System.out.println(new DataBaseAccessCode().getItem(o.getItemCode()).getItemCountInBox());
                    int itemsCount_in_box = new DataBaseAccessCode().getItem(o.getItemCode()).getItemCountInBox();
                    double per_item_Price = o.getUnitPrice_Box()/itemsCount_in_box;
                    total+=(unit_boxPrice*o.getBoxQty() + per_item_Price*o.getItemQty());
                }

                ArrayList<PaymentDTO> paymentDTOS = new DataBaseAccessCode().getPaymentByOrderId(dto.getId());
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

                ArrayList<ReturnStockDTO> returnByOrderId = new DataBaseAccessCode().getReturnByOrderId(dto.getId());
                double returnAmount = 0.00;
                for (ReturnStockDTO r:returnByOrderId) {
                    ItemDTO itemDTO = new DataBaseAccessCode().getItem(r.getItemCode());
                    double unit_boxPrice = itemDTO.getUnitPrice_Box();
                    int itemsCount_in_box = itemDTO.getItemCountInBox();
                    double per_item_Price = itemDTO.getUnitPrice_Box()/itemsCount_in_box;
                    returnAmount+=(unit_boxPrice*r.getBoxQty() + per_item_Price*r.getItemQty());
                }


                ArrayList<DiscountDTO> allDiscountByOrderId = new DataBaseAccessCode().getAllDiscountByOrderId(dto.getId());
                int dupCount = 1;
                for (DiscountDTO d:allDiscountByOrderId) {
                    if(dupCount!=d.getIdDup()){
                        dupCount++;
                    }
                }
                System.out.println("99999999999999999999");
                System.out.println(dupCount);
                System.out.println(allDiscountByOrderId);
                double finalDisValue = 0.00;
                for(int i=0; i<dupCount && allDiscountByOrderId.size()>0; i++){
                    ArrayList<DiscountDTO> allDiscountByIdDup = new DataBaseAccessCode().getAllDiscountByIdDup(dto.getId(), String.valueOf(i + 1));
                    System.out.println("11111111111111111111111111");
                    System.out.println(allDiscountByIdDup);
                    double totalDis = 0.00;
                    for (DiscountDTO d:allDiscountByIdDup) {
                        ItemDTO itemDTO = new DataBaseAccessCode().getItem(d.getItemCode());
                        double unit_boxPrice = itemDTO.getUnitPrice_Box();
                        int itemsCount_in_box = itemDTO.getItemCountInBox();
                        double per_item_Price = itemDTO.getUnitPrice_Box()/itemsCount_in_box;

                        OrderDetailsDTO orderDetail = new DataBaseAccessCode().getOrderDetail(dto.getId(), d.getItemCode());

                        totalDis+=(unit_boxPrice*orderDetail.getBoxQty() + per_item_Price*orderDetail.getItemQty());
                    }
                    System.out.println(allDiscountByIdDup.get(0));
                    finalDisValue+=totalDis*(allDiscountByIdDup.get(0).getDiscountValue())/100;
                }



                OrderDetailsTM orderDetailsTM = new OrderDetailsTM(
                        dto.getOb_id(),
                        dto.getInvId(),
                        dto.getShopId(),
                        total,
                        cashAmount,
                        creditAmount,
                        chequeAmount,
                        chequeNum,
                        returnAmount,
                        finalDisValue
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
    }
}