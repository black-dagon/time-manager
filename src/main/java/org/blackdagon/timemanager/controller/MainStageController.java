package org.blackdagon.timemanager.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MainStageController implements Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(MainStageController.class);

    @FXML
    private TimeCalculationPaneController timeCalculationPaneController;

    private Pair<String, String> messages;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
