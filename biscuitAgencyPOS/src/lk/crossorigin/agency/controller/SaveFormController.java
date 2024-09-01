package lk.crossorigin.agency.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.crossorigin.agency.bo.custom.SaveListBO;
import lk.crossorigin.agency.bo.custom.impl.SavelistBoImpl;
import lk.crossorigin.agency.dao.custom.SaveListDAO;
import lk.crossorigin.agency.dao.custom.impl.SaveListDaoImpl;
import lk.crossorigin.agency.dto.SaveListDTO;

import java.io.IOException;
import java.sql.SQLException;

public class SaveFormController {
    public AnchorPane saveContext;
    public TextField txtDescription;
    public JFXButton btnAdd;
    public JFXButton btnBack;
    public JFXButton btnSave;
    public JFXTextArea txtSaveDescription;

    SaveListBO saveListBO = new SavelistBoImpl();

    public void saveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String[] tasksArray = txtSaveDescription.getText().split(",");
        int i = Integer.parseInt(saveListBO.getLastSavedId());
        int temp = i;
        for (String task:tasksArray) {
            i++;
            SaveListDTO saveListDTO = new SaveListDTO(i,task);
            if(saveListBO.saveList(saveListDTO)){
                if((i-temp)== tasksArray.length){
                    new Alert(Alert.AlertType.CONFIRMATION,"List Was Saved", ButtonType.OK).show();
                }
            }else{
                new Alert(Alert.AlertType.WARNING,"Something went wrong! Please try again.", ButtonType.CANCEL).show();
            }
        }
    }

    public void addOnAction(ActionEvent actionEvent) {
        String saveTask = txtDescription.getText();
        String addedTaskstoList = txtSaveDescription.getText();
        if(addedTaskstoList.isEmpty()){
            addedTaskstoList = "";
        }
        //txtSaveDescription.setText(addedTaskstoList + saveTask + System.lineSeparator());
        txtSaveDescription.setText(addedTaskstoList + saveTask + ",");
    }

    public void backbtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) saveContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
    }
}
