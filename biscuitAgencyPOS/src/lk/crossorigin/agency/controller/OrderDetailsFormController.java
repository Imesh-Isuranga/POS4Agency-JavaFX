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
    public TableColumn colOrderId;
    public TableColumn colShopId;
    public TableColumn colShopName;
    public TableColumn colItemName;
    public TableColumn colQTY;
    public TableColumn colDate;
    public TableColumn colTotal;

    public void initialize(){
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colShopId.setCellValueFactory(new PropertyValueFactory<>("shopId"));
        colShopName.setCellValueFactory(new PropertyValueFactory<>("shopName"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        loadAllDetails("");
    }

    private void loadAllDetails(String searchText){
        ObservableList<OrderDetailsTM> obList = FXCollections.observableArrayList();
        try {
            ArrayList<OrderDetailsDTO> dtoList = new DataBaseAccessCode().getAllOrderDetails("%" + searchText + "%");
            for (OrderDetailsDTO dto:dtoList) {
                OrderDetailsTM orderDetailsTM = new OrderDetailsTM(
                        dto.getOrderId(),
                        dto.getShopId(),
                        new DataBaseAccessCode().getShop(dto.getShopId()).getName(),
                        new DataBaseAccessCode().getItem(dto.getItemCode()).getName(),
                        dto.getQty(),
                        new DataBaseAccessCode().getOrderDate(dto.getOrderId()),
                        dto.getQty()*dto.getUnitPrice()
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
}
