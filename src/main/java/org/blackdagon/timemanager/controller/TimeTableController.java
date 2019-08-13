package org.blackdagon.timemanager.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.blackdagon.timemanager.facade.DateTimeCalculationFacade;
import org.blackdagon.timemanager.model.EditingCell;
import org.blackdagon.timemanager.model.Meeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class TimeTableController implements Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(TimeTableController.class);

    @Autowired
    private DateTimeCalculationFacade dateTimeCalculationFacade;

    @FXML
    private TableView tableWithTimes;

    @FXML
    private TableColumn<Meeting, String> meetingColumn;

    @FXML
    private TableColumn<Meeting, String> timeColumn;

    @FXML
    private TableColumn<Meeting, String> jiraColumn;

    private String time = "00:00";

    public void calculateTimeDifference(String time) {
        this.time = time;
        dateTimeCalculationFacade.calculateTimeDifferenceInColumns(time, tableWithTimes.getItems());
        tableWithTimes.refresh();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableWithTimes.setEditable(true);
        setUpColumns();
        tableWithTimes.getItems().setAll(setUpTable());
    }

    private static List<Meeting> setUpTable() {
        Meeting meeting = new Meeting("Daily", "00:15");
        ObservableList<Meeting> observableList = FXCollections.observableList(new ArrayList<>());
        observableList.add(meeting);
        for (int i = 0; i < 10; i++) {
            observableList.add(new Meeting());
        }
        return observableList;
    }

    private void setUpColumns() {
        Callback<TableColumn<Meeting, String>, TableCell<Meeting, String>> cellFactory =
                new Callback<TableColumn<Meeting, String>, TableCell<Meeting, String>>() {
                    @Override
                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };
        meetingColumn.setCellValueFactory(new PropertyValueFactory<Meeting, String>("name"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Meeting, String>("time"));
        jiraColumn.setCellValueFactory(new PropertyValueFactory<Meeting, String>("inJira"));
        meetingColumn.setCellFactory(cellFactory);
        timeColumn.setCellFactory(cellFactory);
        meetingColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Meeting, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Meeting, String> event) {
                event.getRowValue().setName(event.getNewValue());
            }
        });

        timeColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Meeting, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Meeting, String> event) {
                event.getRowValue().setTime(event.getNewValue());
                calculateTimeDifference(time);
            }
        });

        meetingColumn.setEditable(true);
        timeColumn.setEditable(true);
    }
}
