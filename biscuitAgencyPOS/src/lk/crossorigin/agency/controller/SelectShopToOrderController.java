package lk.crossorigin.agency.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.crossorigin.agency.bo.custom.OrderBookBO;
import lk.crossorigin.agency.bo.custom.SaveListBO;
import lk.crossorigin.agency.bo.custom.ShopBO;
import lk.crossorigin.agency.bo.custom.impl.OrderBookBoImpl;
import lk.crossorigin.agency.bo.custom.impl.SavelistBoImpl;
import lk.crossorigin.agency.bo.custom.impl.ShopBoImpl;
import lk.crossorigin.agency.dto.OrderBookDTO;
import lk.crossorigin.agency.dto.SaveListDTO;
import lk.crossorigin.agency.dto.ShopDTO;
import lk.crossorigin.agency.view.tm.SaveListTM;
import lk.crossorigin.agency.view.tm.ShopTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class SelectShopToOrderController {
    public JFXButton btnBack;
    public AnchorPane selectShopToOrderContext;
    public JFXComboBox shopIdcmb;
    public TextField bookNumtxt;
    public TextField invoiceNumtxt;
    public JFXButton btnNext;
    public TableView tblSaveList;
    public TableColumn colNo;
    public TableColumn colListItem;
    public TableColumn colDelete;

    ShopBO shopBO = new ShopBoImpl();
    OrderBookBO orderBookBO = new OrderBookBoImpl();

    SaveListBO saveListBO = new SavelistBoImpl();

    public void initialize() throws SQLException, ClassNotFoundException {
        colNo.setCellValueFactory(new PropertyValueFactory<>("no"));
        colListItem.setCellValueFactory(new PropertyValueFactory<>("listItem"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btn"));

        shopIdcmb.setItems(loadAllShopIds());
        loadAllSavedList("");
    }

    private void loadAllSavedList(String searchText){
        ObservableList<SaveListTM> obList = FXCollections.observableArrayList();
        try {
            ArrayList<SaveListDTO> dtoList = saveListBO.getList("%" + searchText + "%");
            for (SaveListDTO dto:dtoList) {
                Button btn = new Button();
                SaveListTM saveListTM = new SaveListTM(
                        dto.getId(),
                        dto.getDescription(),
                        btn
                );
                obList.add(saveListTM);
                //Delete------------------------
                btn.setOnAction(e->{
                    Alert confirmation = new Alert(
                            Alert.AlertType.CONFIRMATION,
                            "ARE YOU SURE ?",
                            ButtonType.YES,ButtonType.CANCEL
                    );
                    Optional<ButtonType> confirmState = confirmation.showAndWait();
                    if(confirmState.get().equals(ButtonType.YES)){
                        try {
                            if(saveListBO.deleteList(dto.getId())) {
                                new Alert(Alert.AlertType.CONFIRMATION,"Save item was Deleted", ButtonType.OK).show();
                                loadAllSavedList("");
                            }else{
                                new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                });
                //Delete------------------------
            }
            tblSaveList.setItems(obList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<String> loadAllShopIds() throws SQLException, ClassNotFoundException {
        ObservableList<String> shopIdsObList = FXCollections.observableArrayList();;
        ArrayList<ShopDTO> shopDTOArrayList = shopBO.getAllShops("%"+""+"%");

        for (ShopDTO shopDTO:shopDTOArrayList) {
            shopIdsObList.add(shopDTO.getId());
        }

        return shopIdsObList;
    }

    public void nextOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if(tblSaveList.getItems().size()==0){
            OrderBookDTO orderBookDTO = new OrderBookDTO(orderBookBO.generateOrderId(bookNumtxt.getText(),invoiceNumtxt.getText()), bookNumtxt.getText(),invoiceNumtxt.getText(),shopIdcmb.getValue().toString());
            if(orderBookBO.saveOrderBook(orderBookDTO)){
                Stage stage = (Stage) selectShopToOrderContext.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/AddOrderForm.fxml"))));
            }else{
                new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.", ButtonType.OK).show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Please check saved list.", ButtonType.OK).show();
        }

    }



    public void backbtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) selectShopToOrderContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
    }
}
