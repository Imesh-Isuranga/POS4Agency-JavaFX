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
import lk.crossorigin.agency.dto.ItemDTO;
import lk.crossorigin.agency.dto.OrderDetailsDTO;
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
            ArrayList<OrderDetailsDTO> dtoList = new DataBaseAccessCode().getAllOrderDetails("%" + searchText + "%");
            for (OrderDetailsDTO dto:dtoList) {
                OrderDetailsTM orderDetailsTM = new OrderDetailsTM(

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