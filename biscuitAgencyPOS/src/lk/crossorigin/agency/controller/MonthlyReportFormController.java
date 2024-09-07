package lk.crossorigin.agency.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.crossorigin.agency.bo.custom.*;
import lk.crossorigin.agency.bo.custom.impl.*;
import lk.crossorigin.agency.db.DBConnection;
import lk.crossorigin.agency.dto.*;
import lk.crossorigin.agency.view.tm.MonthlyReportTM;
import lk.crossorigin.agency.view.tm.ReturnStockTM;
import lk.crossorigin.agency.view.tm.ShopTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MonthlyReportFormController{

    public AnchorPane monthlyReportContext;
    public TableView<MonthlyReportTM> tblMonthlyReport;
    public TableColumn colDate;
    public TableColumn colTotal;
    public TableColumn colreturn;
    public TableColumn colDis;

    public JFXButton btnBack;
    public JFXButton btnPrint;


    ItemBO itemBO = new ItemBoImpl();
    OrderDetailBO orderDetailBO = new OrderDetailBoImpl();
    OrderBO orderBO = new OrderBoImpl();
    MonthlyReportBO monthlyReportBO = new MonthlyReportBoImpl();
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

                if((date == null) || !(dto.getDate().equals(date))){
                    if(date != null){
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

                total = Math.round(total * 100.0) / 100.0;
                dis_tot = Math.round(dis_tot * 100.0) / 100.0;
                return_tot = Math.round(return_tot * 100.0) / 100.0;

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

    public Object getCellValueReturn(int rowIndex, int columnIndex) {
        // Step 1: Retrieve the row's data (item) from the TableView's items
        MonthlyReportTM rowData = tblMonthlyReport.getItems().get(rowIndex);

        // Step 2: Retrieve the TableColumn for the given column index
        TableColumn<MonthlyReportTM, ?> column = tblMonthlyReport.getColumns().get(columnIndex);

        // Step 3: Get the value from the cell using the column's CellDataFeatures
        ObservableValue<?> cellValue = column.getCellObservableValue(rowData);

        // Step 4: Return the cell value
        return cellValue.getValue();
    }

    public void printOnAction(ActionEvent actionEvent) {
        try {
            if(monthlyReportBO.deleteMonthlyReport()){
                String dateString = "2024-09-01";  // Your date string
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                for (int i=0; i<tblMonthlyReport.getItems().size(); i++){
                    MonthlyReportDTO monthlyReportDTO = new MonthlyReportDTO(
                            formatter.parse(dateString),
                            Double.parseDouble(getCellValueReturn(i,1).toString()),
                            Double.parseDouble(getCellValueReturn(i,2).toString()),
                            Double.parseDouble(getCellValueReturn(i,3).toString())
                    );
                    if(monthlyReportBO.saveMonthlyReport(monthlyReportDTO)){
                        if(i==tblMonthlyReport.getItems().size()-1){
                            JasperDesign design = JRXmlLoader.load("src/lk/crossorigin/agency/reports/Monthly_Report.jrxml");
                            JasperReport jasperReport = JasperCompileManager.compileReport(design);
                            Connection conn = DBConnection.getInstance().getConnection();
                            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), conn);
                            JasperViewer.viewReport(jasperPrint, false);                    }
                    }else {
                        new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
                        break;
                    }
                }
            }else{
                new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.",ButtonType.CANCEL).show();
            }
        } catch (JRException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "JRException: " + e.getMessage(), ButtonType.OK).show();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQLException: " + e.getMessage(), ButtonType.OK).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "ClassNotFoundException: " + e.getMessage(), ButtonType.OK).show();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
