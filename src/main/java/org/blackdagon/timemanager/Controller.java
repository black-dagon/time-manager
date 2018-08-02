package org.blackdagon.timemanager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.blackdagon.timemanager.calculator.DateTimeCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Controller {

    @Autowired
    private DateTimeCalculatorService dateTimeCalculatorService;

    @FXML
    private Label withLunchCalculated;

    @FXML
    private Label withoutLunchCalculated;

    @FXML
    private Button calculate;

    @FXML
    private Button calculateAndAddToTable;

    @FXML
    private TextField startTime;

    @FXML
    private TextField endTime;

    public Controller() {
    }

    @FXML
    private void calculateTimeDiffrence() {
        String timeWithLunch = dateTimeCalculatorService.calculateDiffrenceInDates(startTime.getText(), endTime.getText());
        String timeWithoutLunch = dateTimeCalculatorService.getTimeWithoutLunch(timeWithLunch);
        withLunchCalculated.setText(timeWithLunch);
        withoutLunchCalculated.setText(timeWithoutLunch);
    }
}
