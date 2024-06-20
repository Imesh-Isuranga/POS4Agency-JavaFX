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
import lk.crossorigin.agency.entity.Item;
import lk.crossorigin.agency.entity.Order;
import lk.crossorigin.agency.entity.OrderDetail;
import lk.crossorigin.agency.entity.Shop;
import lk.crossorigin.agency.view.tm.ItemTM;
import lk.crossorigin.agency.view.tm.OrderDetailsTM;

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

    private Shop getShop(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Shop WHERE id=?";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setObject(1,id);
        ResultSet rst = stm.executeQuery();

        if(rst.next()){
            return new Shop(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
        }
        return null;
    }

    private Item getItem(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Item WHERE code=?";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setObject(1,code);
        ResultSet rst = stm.executeQuery();

        if(rst.next()){
            return new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4)
            );
        }
        return null;
    }

    private Order getOrder(String orderId) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Orders WHERE id=?";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setObject(1,orderId);
        ResultSet rst = stm.executeQuery();

        if(rst.next()){
            return new Order(
                    rst.getString(1),
                    rst.getDate(2)
            );
        }
        return null;
    }
    private void loadAllDetails(String searchText){
        ObservableList<OrderDetailsTM> obList = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM OrderDetail WHERE orderId LIKE ? OR shopId LIKE ? OR itemCode LIKE ? OR qty LIKE ? OR unitPrice LIKE ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setObject(1,"%" + searchText + "%");
            stm.setObject(2,"%" + searchText + "%");
            stm.setObject(3,"%" + searchText + "%");
            stm.setObject(4,"%" + searchText + "%");
            stm.setObject(5,"%" + searchText + "%");
            ResultSet rst = stm.executeQuery();

            System.out.println("--=-=-=-=-===-=-=-=-=");
            System.out.println(rst.next());
            while (rst.next()){
                OrderDetailsTM orderDetailTM = new OrderDetailsTM(
                        rst.getString(1),
                        rst.getString(2),
                        getShop(rst.getString(2)).getName(),
                        getItem(rst.getString(3)).getName(),
                        Integer.parseInt(rst.getString(4)),
                        getOrder(rst.getString(1)).getDate(),
                        rst.getInt(4)*rst.getDouble(5)
                );
                System.out.println("--=-=-=-=-===-=-=-=-=]]]]]]]]");
                System.out.println(orderDetailTM);
                obList.add(orderDetailTM);
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
