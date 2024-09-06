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
import lk.crossorigin.agency.view.tm.DisViewTM;
import lk.crossorigin.agency.view.tm.OrderDetailsTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class DisViewFormController {

    public AnchorPane disViewContext;
    public TableView<DisViewTM> disViewtbl;
    public TableColumn colId;
    public TableColumn colOrderId;
    public TableColumn colItem;
    public TableColumn colDisPercentage;
    public TextField txtSearch;
    public JFXButton btnSearch;
    DiscountBO discountBO = new DiscountBoImpl();
    ItemBO itemBO = new ItemBoImpl();

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colItem.setCellValueFactory(new PropertyValueFactory<>("item"));
        colDisPercentage.setCellValueFactory(new PropertyValueFactory<>("disPercentage"));

        loadAllDetails("");
    }

    private void loadAllDetails(String searchText){
        ObservableList<DisViewTM> obList = FXCollections.observableArrayList();
        try {
            ArrayList<DiscountDTO> dtoList = discountBO.getAllDiscount("%" + searchText + "%");
            for (DiscountDTO dto:dtoList) {
                DisViewTM disViewTM = new DisViewTM(
                        dto.getIdDup(),
                        dto.getOrderId(),
                        itemBO.getItem(dto.getItemCode()).getName(),
                        dto.getDiscountValue()
                );
                obList.add(disViewTM);
            }
            disViewtbl.setItems(obList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }



    public void searchOnAction(ActionEvent actionEvent) {
        loadAllDetails(txtSearch.getText());
    }


}