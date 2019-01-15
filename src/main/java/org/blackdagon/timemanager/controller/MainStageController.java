package org.blackdagon.timemanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.blackdagon.timemanager.TimeValidator;
import org.blackdagon.timemanager.facade.DateTimeCalculationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainStageController {

    private static final Logger LOG = LogManager.getLogger(MainStageController.class);

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

    private TimeValidator timeValidator;

    public MainStageController() {
        timeValidator = new TimeValidator();
    }

    @FXML
    private void calculateTimeDifference() {
        timeValidator.validate(startTime.getText(), endTime.getText());
        if (timeValidator.getValid()) {
            String timeWithLunch = dateTimeCalculationFacade.calculateDifferenceInTime(startTime.getText(), endTime.getText());
            String timeWithoutLunch = dateTimeCalculationFacade.calculateDifferenceInTimeWithoutLunch(startTime.getText(), endTime.getText());
            withLunchCalculated.setText(timeWithLunch);
            withoutLunchCalculated.setText(timeWithoutLunch);
        } else {
            withLunchCalculated.setText(timeValidator.getMessage());
            withoutLunchCalculated.setText(timeValidator.getMessage());
        }
    }

    @FXML
    private void copyTimeWithLunch() {
        copyToClipboard(withLunchCalculated.getText());
    }

    @FXML
    private void copyTimeWithoutLunch() {
        copyToClipboard(withoutLunchCalculated.getText());
    }

    private void copyToClipboard(String text) {
        if (timeValidator.validate(text)) {
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(dateTimeCalculationFacade.getTimeForJira(text));
            Clipboard.getSystemClipboard().setContent(clipboardContent);
        }
    }

}
