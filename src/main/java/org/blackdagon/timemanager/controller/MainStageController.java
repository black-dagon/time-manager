package org.blackdagon.timemanager.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.util.Callback;
import org.apache.commons.lang3.tuple.Pair;
import org.blackdagon.timemanager.facade.DateTimeCalculationFacade;
import org.blackdagon.timemanager.facade.TimeMessageFacade;
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
public class MainStageController implements Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(MainStageController.class);

    @Autowired
    private TimeMessageFacade timeMessageFacade;

    @Autowired
    private DateTimeCalculationFacade dateTimeCalculationFacade;

    @FXML
    private Label withLunchCalculated;

    @FXML
    private Label withoutLunchCalculated;

    @FXML
    private Button calculate;

    @FXML
    private Button copyTimeWithLunch;

    @FXML
    private Button copyTimeWithoutLunch;

    @FXML
    private TextField startTime;

    @FXML
    private TextField endTime;

    @FXML
    private TableView tableWithTimes;

    @FXML
    private TableColumn<Meeting, String> meetingColumn;

    @FXML
    private TableColumn<Meeting, String> timeColumn;

    @FXML
    private TableColumn<Meeting, String> jiraColumn;

    private ObservableList<Meeting> observableList;

    private Pair<String, String> messages;

    @FXML
    private void calculateTimeDifference() {
        messages = timeMessageFacade.getTimeMessages(startTime.getText(), endTime.getText());
        withLunchCalculated.setText(messages.getLeft());
        withoutLunchCalculated.setText(messages.getRight());
        dateTimeCalculationFacade.calculateTimeDifferenceInColumns(messages.getLeft(), tableWithTimes.getItems());
        tableWithTimes.refresh();
    }

    @FXML
    private void copyTimeWithLunch() {
        copyToClipboard(withLunchCalculated.getText());
    }

    @FXML
    private void copyTimeWithoutLunch() {
        copyToClipboard(withoutLunchCalculated.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableWithTimes.setEditable(true);
        setUpColumns();
        tableWithTimes.getItems().setAll(setUpTable());
    }

    private void copyToClipboard(String text) {
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(timeMessageFacade.getTimeMessageForJira(text));
        Clipboard.getSystemClipboard().setContent(clipboardContent);
    }

    private List<Meeting> setUpTable() {
        Meeting meeting = new Meeting("Daily", "00:15");
        observableList = FXCollections.observableList(new ArrayList<>());
        observableList.add(meeting);
        for (int i = 0; i < 10; i++) {
            observableList.add(new Meeting());
        }
        return observableList;
    }

    private void setUpColumns() {
        Callback<TableColumn<Meeting, String>, TableCell<Meeting, String>> cellFactory =
                new Callback<TableColumn<Meeting, String>, TableCell<Meeting, String>>() {
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
                calculateTimeDifference();
            }
        });

        meetingColumn.setEditable(true);
        timeColumn.setEditable(true);
    }
}
