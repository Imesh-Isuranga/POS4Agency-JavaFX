package lk.crossorigin.agency.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.crossorigin.agency.bo.custom.DiscountBO;
import lk.crossorigin.agency.bo.custom.ShopBO;
import lk.crossorigin.agency.bo.custom.ShopCreditBO;
import lk.crossorigin.agency.bo.custom.impl.DiscountBoImpl;
import lk.crossorigin.agency.bo.custom.impl.ShopBoImpl;
import lk.crossorigin.agency.bo.custom.impl.ShopCreditBOImpl;
import lk.crossorigin.agency.dto.MainItemDTO;
import lk.crossorigin.agency.dto.ShopCreditDTO;
import lk.crossorigin.agency.dto.ShopDTO;
import lk.crossorigin.agency.view.tm.ShopTM;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ShopscreditFormController {
    public AnchorPane shopCreditContext;
    public JFXButton btnBack;
    public JFXComboBox cmbShops;
    public TextField txtCashAmount;
    public JFXButton tbnPay;
    public JFXButton btnShowTable;
    public Label lblUptoNowCredits;
    public DatePicker datePicker;
    public String selectedCode;
    public TextField txtBankDetails;

    ShopBO shopBO = new ShopBoImpl();
    ShopCreditBO shopCreditBO = new ShopCreditBOImpl();


    private Map<String, String> itemMap = new HashMap<>();  // Map to store item name and code
    public void initialize() throws SQLException, ClassNotFoundException {
        cmbShops.setItems(loadAllShopIds());

        cmbShops.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedCode = (String) newValue;
            try {
                lblUptoNowCredits.setText(String.valueOf((shopBO.getShop(selectedCode)).getCredit_uptoNow()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private ObservableList<String> loadAllShopIds() throws SQLException, ClassNotFoundException {
        ObservableList<String> shopIdsObList = FXCollections.observableArrayList();;
        ArrayList<ShopDTO> shopDTOArrayList = shopBO.getAllShops("%"+""+"%");

        for (ShopDTO shopDTO:shopDTOArrayList) {
            shopIdsObList.add(shopDTO.getId());
        }

        return shopIdsObList;
    }

    public void backbtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) shopCreditContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
    }

    public void payOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        LocalDate localDate = datePicker.getValue();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        System.out.println(localDate);
        System.out.println(date);

        double amount = Double.parseDouble(txtCashAmount.getText());
        String bankDetails ;

        if(txtBankDetails.getText().isEmpty()){
            bankDetails = "";
        }else{
            bankDetails = txtBankDetails.getText();
        }

        ShopCreditDTO shopCreditDTO = new ShopCreditDTO(selectedCode,date,amount,bankDetails);
        if(txtBankDetails.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please Enter Amount.", ButtonType.CANCEL).show();
        }else if(shopCreditBO.saveCredit(shopCreditDTO)){
            if((shopBO.getShop(selectedCode).getCredit_uptoNow()-amount) >= 0){
                if(shopBO.updateShopCredit(selectedCode,(shopBO.getShop(selectedCode).getCredit_uptoNow()-amount))){
                    new Alert(Alert.AlertType.CONFIRMATION,"Credit was Added", ButtonType.OK).show();
                }else{
                    new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.", ButtonType.CANCEL).show();
                }
            }else{
                new Alert(Alert.AlertType.WARNING,"Please Enter Low Amount because amount exceed credit limit", ButtonType.CANCEL).show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.", ButtonType.CANCEL).show();
        }
    }

    public void showInAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) shopCreditContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/CreditForm.fxml"))));
    }
}
