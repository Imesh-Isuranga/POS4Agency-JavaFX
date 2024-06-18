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
import lk.crossorigin.agency.view.tm.ItemTM;
import lk.crossorigin.agency.view.tm.ShopTM;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class LoadStockFormController {
    public AnchorPane loadStockContext;
    public JFXButton btnBack;
    public TableView<ItemTM> itemsTbl;
    public TableColumn colItemCode;
    public TableColumn colItemName;
    public TableColumn colUnitPrice;
    public TableColumn colQTYOnHand;
    public TextField qtyTxt;
    public JFXComboBox itemsCmbBox;
    public TableColumn colOption;
    public JFXButton btnUpdateStock;

    public String selectedCode;


    public void initialize(){
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQTYOnHand.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        loadAllItems("");

        itemsCmbBox.setItems(loadComboBox(""));

        itemsCmbBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedCode = (String) newValue;
        });

    }

    private ObservableList<String> loadComboBox(String searchText){
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BiscuitAgency", "root", "1313");
            String sql = "SELECT * FROM Item WHERE code LIKE ? OR name LIKE ? OR unitPrice LIKE ? OR qty LIKE ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1,"%" + searchText + "%");
            stm.setObject(2,"%" + searchText + "%");
            stm.setObject(3,"%" + searchText + "%");
            stm.setObject(4,"%" + searchText + "%");

            ResultSet rst = stm.executeQuery();
            while (rst.next()){
                obList.add(rst.getString(1));
            }
            return obList;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void loadAllItems(String searchText){
        ObservableList<ItemTM> obList = FXCollections.observableArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BiscuitAgency", "root", "1313");
            String sql = "SELECT * FROM Item WHERE code LIKE ? OR name LIKE ? OR unitPrice LIKE ? OR qty LIKE ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1,"%" + searchText + "%");
            stm.setObject(2,"%" + searchText + "%");
            stm.setObject(3,"%" + searchText + "%");
            stm.setObject(4,"%" + searchText + "%");

            ResultSet rst = stm.executeQuery();
            while (rst.next()){
                Button btn = new Button("Delete");
                ItemTM itemTM = new ItemTM(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getDouble(3),
                        rst.getInt(4),
                        btn);
                obList.add(itemTM);

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
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/BiscuitAgency", "root", "1313");
                            String sql1 = "DELETE FROM Item WHERE code=?";
                            PreparedStatement stm1 = con1.prepareStatement(sql1);
                            stm1.setObject(1,itemTM.getCode());
                            boolean isDeleted = stm1.executeUpdate() > 0;

                            if(isDeleted) {
                                new Alert(Alert.AlertType.CONFIRMATION,"Shop was Deleted", ButtonType.OK).show();
                                loadAllItems("");
                            }else{
                                new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                            }

                        } catch (ClassNotFoundException | SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                //Delete------------------------
            }
            itemsTbl.setItems(obList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void backbtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) loadStockContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
    }

    public void UpdateStockOnAction(ActionEvent actionEvent) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BiscuitAgency", "root", "1313");
            String sql = "UPDATE Item SET qty=? WHERE code=?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1,qtyTxt.getText());
            stm.setObject(2,selectedCode);

            boolean isSaved = stm.executeUpdate()>0;
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION,"Shop was Saved", ButtonType.OK).show();
                loadAllItems("");
            }else{
                new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
