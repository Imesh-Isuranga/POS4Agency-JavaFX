package lk.crossorigin.agency.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.crossorigin.agency.bo.custom.*;
import lk.crossorigin.agency.bo.custom.impl.*;
import lk.crossorigin.agency.dto.*;
import lk.crossorigin.agency.view.tm.OrderDetailsTM;
import lk.crossorigin.agency.view.tm.ReturnItemsTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnItemsFormController {
    public AnchorPane orderHistoryContext;
    public JFXButton btnSearch;
    public TableColumn colItem;
    public TableColumn colshop;
    public TableColumn colboxQty;
    public TableColumn colitemQty;
    public TableColumn colperQtyPrice;
    public TableView<ReturnItemsTM> retunItemsTbl;


    ItemBO itemBO = new ItemBoImpl();
    OrderBO orderBO = new OrderBoImpl();
    ReturnStockBO returnStockBO = new ReturnStockBoImpl();

    public void initialize(){
        colItem.setCellValueFactory(new PropertyValueFactory<>("code"));
        colshop.setCellValueFactory(new PropertyValueFactory<>("shopName"));
        colboxQty.setCellValueFactory(new PropertyValueFactory<>("boxQty"));
        colitemQty.setCellValueFactory(new PropertyValueFactory<>("itemQty"));
        colperQtyPrice.setCellValueFactory(new PropertyValueFactory<>("perQTyPrice"));

        loadAllDetails("");
    }

    private void loadAllDetails(String searchText){
        ObservableList<ReturnItemsTM> obList = FXCollections.observableArrayList();
        try {
            double total = 0.00;
            double freeItemTotal = 0.00;



            ArrayList<ReturnStockDTO> dtoList = returnStockBO.getReturnByOrderId("%" + searchText + "%");
            for (ReturnStockDTO dto:dtoList) {
                ItemDTO itemDTO = itemBO.getItem(dto.getItemCode());
                OrderDTO orderDTO = orderBO.getOrderByOrderId(dto.getOrderId());

                ReturnItemsTM returnItemsTM = new ReturnItemsTM(
                    itemDTO.getCode(),
                        orderDTO.getShopId(),
                        dto.getBoxQty(),
                        dto.getItemQty(),
                        dto.getPerQty()
                );
                obList.add(returnItemsTM);
            }
            retunItemsTbl.setItems(obList);
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