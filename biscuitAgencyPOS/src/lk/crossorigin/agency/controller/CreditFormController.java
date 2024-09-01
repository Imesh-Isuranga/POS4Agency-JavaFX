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
import lk.crossorigin.agency.bo.custom.ShopBO;
import lk.crossorigin.agency.bo.custom.ShopCreditBO;
import lk.crossorigin.agency.bo.custom.impl.ShopBoImpl;
import lk.crossorigin.agency.bo.custom.impl.ShopCreditBOImpl;
import lk.crossorigin.agency.db.DBConnection;
import lk.crossorigin.agency.dto.ShopCreditDTO;
import lk.crossorigin.agency.dto.ShopDTO;
import lk.crossorigin.agency.view.tm.ShopCreditTM;
import lk.crossorigin.agency.view.tm.ShopTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class CreditFormController {
    public AnchorPane creditContext;
    public JFXButton btnBack;
    public TableView creditTbl;
    public TableColumn colNum;
    public TableColumn colShopId;
    public TableColumn colDate;
    public TableColumn colAmount;
    public TableColumn colBankDetails;
    public JFXButton btnSearch;
    public JFXButton btnShow;
    public JFXButton btnPrint;
    public TextField txtSearch;

    ShopCreditBO shopCreditBO = new ShopCreditBOImpl();

    public void initialize() throws SQLException, ClassNotFoundException {
        colNum.setCellValueFactory(new PropertyValueFactory<>("id"));
        colShopId.setCellValueFactory(new PropertyValueFactory<>("shopId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colBankDetails.setCellValueFactory(new PropertyValueFactory<>("bank_details"));

        loadAllShops("");
    }

    private void loadAllShops(String searchText){
        ObservableList<ShopCreditTM> obList = FXCollections.observableArrayList();
        try {
            ArrayList<ShopCreditDTO> dtoList = shopCreditBO.getAllCreditsShops("%" + searchText + "%");
            for (ShopCreditDTO dto:dtoList) {
                ShopCreditTM shopCreditTM = new ShopCreditTM(
                        dto.getId(),
                        dto.getShopId(),
                        dto.getDate_paid(),
                        dto.getAmount(),
                        dto.getPaymentDetails()
                );
                obList.add(shopCreditTM);
            }
            creditTbl.setItems(obList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void backbtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) creditContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
    }

    public void searchOnAction(ActionEvent actionEvent) {
        loadAllShops(txtSearch.getText());
    }

    public void showOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) creditContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/EachCreditShopsForm.fxml"))));
    }

    public void printOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load("src/lk/crossorigin/agency/reports/Shop_Credit.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            Connection conn = DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), conn);
            JasperViewer.viewReport(jasperPrint, false);
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
