package lk.crossorigin.agency.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.crossorigin.agency.bo.custom.ShopBO;
import lk.crossorigin.agency.bo.custom.ShopCreditBO;
import lk.crossorigin.agency.bo.custom.impl.ShopBoImpl;
import lk.crossorigin.agency.bo.custom.impl.ShopCreditBOImpl;
import lk.crossorigin.agency.dto.ShopCreditDTO;
import lk.crossorigin.agency.dto.ShopDTO;
import lk.crossorigin.agency.entity.Shop;
import lk.crossorigin.agency.view.tm.CreditTM;
import lk.crossorigin.agency.view.tm.ShopCreditTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class EachCreditShopsFormController {
    public AnchorPane creditContext;
    public JFXButton btnBack;
    public TableView creditTbl;
    public TableColumn colNum;
    public TableColumn colShopId;
    public TableColumn colDate;
    public TableColumn colAmount;
    public TableColumn colBankDetails;
    public JFXButton btnSearch;
    public Label eachCreditContext;
    public TableColumn colCredit;
    public TextField txtSearch;

    ShopBO shopBO = new ShopBoImpl();

    public void initialize() throws SQLException, ClassNotFoundException {
        colNum.setCellValueFactory(new PropertyValueFactory<>("id"));
        colShopId.setCellValueFactory(new PropertyValueFactory<>("shopId"));
        colCredit.setCellValueFactory(new PropertyValueFactory<>("credit"));

        loadAllShops("");
    }

    private void loadAllShops(String searchText){
        ObservableList<CreditTM> obList = FXCollections.observableArrayList();
        try {
            ArrayList<ShopDTO> dtoList = shopBO.getAllShops("%" + searchText + "%");
            for (ShopDTO dto:dtoList) {
                CreditTM creditTM = new CreditTM(
                        dto.getSh_id(),
                        dto.getId(),
                        dto.getCredit_uptoNow()
                );
                obList.add(creditTM);
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
}
