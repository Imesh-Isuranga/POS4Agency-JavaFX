package lk.crossorigin.agency.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
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
import lk.crossorigin.agency.bo.custom.ItemBO;
import lk.crossorigin.agency.bo.custom.OrderBO;
import lk.crossorigin.agency.bo.custom.OrderDetailBO;
import lk.crossorigin.agency.bo.custom.ShopBO;
import lk.crossorigin.agency.bo.custom.impl.ItemBoImpl;
import lk.crossorigin.agency.bo.custom.impl.OrderBoImpl;
import lk.crossorigin.agency.bo.custom.impl.OrderDetailBoImpl;
import lk.crossorigin.agency.bo.custom.impl.ShopBoImpl;
import lk.crossorigin.agency.dto.ItemDTO;
import lk.crossorigin.agency.dto.OrderDTO;
import lk.crossorigin.agency.dto.ShopDTO;
import lk.crossorigin.agency.view.tm.MonthlyReportTM;
import lk.crossorigin.agency.view.tm.ShopTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class MonthlyReportFormController{

    public AnchorPane monthlyReportContext;
    public TableView<MonthlyReportTM> tblMonthlyReport;
    public TableColumn colDate;
    public TableColumn colTotal;
    public TableColumn colreturn;
    public TableColumn colDis;

    public JFXButton btnBack;


    ItemBO itemBO = new ItemBoImpl();
    OrderDetailBO orderDetailBO = new OrderDetailBoImpl();
    OrderBO orderBO = new OrderBoImpl();
    public void initialize() throws SQLException, ClassNotFoundException {
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colreturn.setCellValueFactory(new PropertyValueFactory<>("return_tot"));
        colDis.setCellValueFactory(new PropertyValueFactory<>("discount"));

        loadMonthlyReport("");
    }
    public void backbtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) monthlyReportContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
    }

    private void loadMonthlyReport(String searchText){
        ObservableList<MonthlyReportTM> obList = FXCollections.observableArrayList();
        try {
            ArrayList<OrderDTO> dtoAllOrdersList = orderBO.getAllOrders("%" + searchText + "%");
            Date date = null;
            double total = 0.0;
            double dis_tot = 0.0;
            double return_tot = 0.0;
            int length = 0;
            for (OrderDTO dto:dtoAllOrdersList) {
                length++;
                String orderId = dto.getOrderId();
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println(dto);
                System.out.println(orderId);
                System.out.println(date);
                System.out.println(date!=null);
                System.out.println(dto.getDate().equals(date));
                System.out.println(dto.getDate() == (date));

                if((date == null) || !(dto.getDate().equals(date))){
                    System.out.println("3333333333333333333333333333333333333333");
                    if(date != null){
                        System.out.println("222222222222222222222222222");
                        MonthlyReportTM monthlyReportTM = new MonthlyReportTM(
                            date,
                            total,
                            return_tot,
                            dis_tot
                        );
                        obList.add(monthlyReportTM);

                    }
                    date = dto.getDate();
                    total = 0.0;
                    dis_tot = 0.0;
                    return_tot = 0.0;
                }
                total += orderDetailBO.getAllOrderDetailsByOrderId(orderId).get(0).getTotal();
                dis_tot += orderDetailBO.getAllOrderDetailsByOrderId(orderId).get(0).getFree_total() + orderDetailBO.getAllOrderDetailsByOrderId(orderId).get(0).getDis_tot();
                return_tot += orderDetailBO.getAllOrderDetailsByOrderId(orderId).get(0).getReturn_tot();

                if(length==dtoAllOrdersList.size()){
                    MonthlyReportTM monthlyReportTM = new MonthlyReportTM(
                            date,
                            total,
                            return_tot,
                            dis_tot
                    );
                    obList.add(monthlyReportTM);
                }
            }
            tblMonthlyReport.setItems(obList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
