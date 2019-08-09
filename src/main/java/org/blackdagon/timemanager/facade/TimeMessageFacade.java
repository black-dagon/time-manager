package org.blackdagon.timemanager.facade;

import org.apache.commons.lang3.tuple.Pair;

public interface TimeMessageFacade {

    Pair<String, String> getTimeMessages(String startTime, String endTime);

    String getTimeMessageForJira(String time);

}
