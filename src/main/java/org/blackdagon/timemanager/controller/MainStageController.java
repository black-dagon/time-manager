package org.blackdagon.timemanager.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import org.apache.commons.lang3.tuple.Pair;
import org.blackdagon.timemanager.facade.DateTimeCalculationFacade;
import org.blackdagon.timemanager.facade.TimeMessageFacade;
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
    private TimeTableController timeTableController;

    private Pair<String, String> messages;

    @FXML
    private void calculateTimeDifference() {
        messages = timeMessageFacade.getTimeMessages(startTime.getText(), endTime.getText());
        withLunchCalculated.setText(messages.getLeft());
        withoutLunchCalculated.setText(messages.getRight());
        timeTableController.calculateTimeDifference(messages.getLeft());
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
    }

    private void copyToClipboard(String text) {
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(timeMessageFacade.getTimeMessageForJira(text));
        Clipboard.getSystemClipboard().setContent(clipboardContent);
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

}
