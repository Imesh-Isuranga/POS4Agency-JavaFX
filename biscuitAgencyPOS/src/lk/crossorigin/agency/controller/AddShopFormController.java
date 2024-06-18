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
import lk.crossorigin.agency.view.tm.ShopTM;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class AddShopFormController {
    public AnchorPane addShopContext;
    public TableView<ShopTM> addShoptbl;
    public TableColumn colShopId;
    public TableColumn colShopName;
    public TableColumn colShopAddress;
    public TextField shopNameTxt;
    public TextField shopAddressTxt;
    public JFXButton btnBack;
    public TextField shopIdTxt;
    public TextField searchTxt;
    public JFXButton btnSearch;
    public TableColumn colOption;
    public JFXButton btnSaveShop;
    public JFXButton btnNewShop;


    public void initialize(){
        colShopId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colShopName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colShopAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        loadAllShops("");

        addShoptbl.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                setData(newValue);
            }
        });
    }

    private void setData(ShopTM tm){
        btnSaveShop.setText("Update Shop");
        shopIdTxt.setText(tm.getId());
        shopNameTxt.setText(tm.getName());
        shopAddressTxt.setText(tm.getAddress());
    }
    public void backbtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) addShopContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
    }

    public void SaveShopOnAction(ActionEvent actionEvent) {
        if(btnSaveShop.getText().equalsIgnoreCase("Save Shop")){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BiscuitAgency", "root", "1313");
                String sql = "INSERT INTO Shop VALUES(?,?,?)";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setObject(1,shopIdTxt.getText());
                stm.setObject(2,shopNameTxt.getText());
                stm.setObject(3,shopAddressTxt.getText());
                boolean isSaved = stm.executeUpdate()>0;
                if(isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION,"Shop was Saved", ButtonType.OK).show();
                    loadAllShops("");
                }else{
                    new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }else{
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BiscuitAgency", "root", "1313");
                String sql = "UPDATE Shop SET name=?,address=? WHERE id=?";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setObject(1,shopNameTxt.getText());
                stm.setObject(2,shopAddressTxt.getText());
                stm.setObject(3,shopIdTxt.getText());
                boolean isSaved = stm.executeUpdate()>0;
                if(isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION,"Shop was Saved", ButtonType.OK).show();
                    loadAllShops("");
                }else{
                    new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadAllShops(String searchText){
        ObservableList<ShopTM> obList = FXCollections.observableArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BiscuitAgency", "root", "1313");
            String sql = "SELECT * FROM Shop WHERE id LIKE ? OR name LIKE ? OR address LIKE ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1,"%" + searchText + "%");
            stm.setObject(2,"%" + searchText + "%");
            stm.setObject(3,"%" + searchText + "%");

            ResultSet rst = stm.executeQuery();
            while (rst.next()){
                Button btn = new Button("Delete");
                ShopTM shopTM = new ShopTM(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        btn);
                obList.add(shopTM);

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
                            String sql1 = "DELETE FROM Shop WHERE id=?";
                            PreparedStatement stm1 = con1.prepareStatement(sql1);
                            stm1.setObject(1,shopTM.getId());
                            boolean isDeleted = stm1.executeUpdate() > 0;

                            if(isDeleted) {
                                new Alert(Alert.AlertType.CONFIRMATION,"Shop was Deleted", ButtonType.OK).show();
                                loadAllShops("");
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
            addShoptbl.setItems(obList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchOnAction(ActionEvent actionEvent) {
        loadAllShops(searchTxt.getText());
    }

    public void NewShopOnAction(ActionEvent actionEvent) {
        btnSaveShop.setText("Save Shop");
        shopIdTxt.clear();
        shopNameTxt.clear();
        shopAddressTxt.clear();
    }
}
