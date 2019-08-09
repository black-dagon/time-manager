package org.blackdagon.timemanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import org.apache.commons.lang3.tuple.Pair;
import org.blackdagon.timemanager.facade.TimeMessageFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainStageController {

    private static final Logger LOG = LoggerFactory.getLogger(MainStageController.class);

    @Autowired
    private TimeMessageFacade timeMessageFacade;

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
    private final TableView tableWithTimes = new TableView();

    private Pair<String, String> messages;

    @FXML
    private void calculateTimeDifference() {
        messages = timeMessageFacade.getTimeMessages(startTime.getText(), endTime.getText());
        withLunchCalculated.setText(messages.getLeft());
        withoutLunchCalculated.setText(messages.getRight());
    }

    @FXML
    private void copyTimeWithLunch() {
        setUpTable();

        copyToClipboard(withLunchCalculated.getText());
    }

    @FXML
    private void copyTimeWithoutLunch() {
        copyToClipboard(withoutLunchCalculated.getText());
    }

    private void copyToClipboard(String text) {
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(timeMessageFacade.getTimeMessageForJira(text));
            Clipboard.getSystemClipboard().setContent(clipboardContent);
    }

    private void setUpTable() {
        tableWithTimes.setEditable(true);
        TableColumn typeOfMeeting = new TableColumn("Meeting");
        typeOfMeeting.setMinWidth(100d);
        tableWithTimes.getColumns().add(typeOfMeeting);

    }

}
