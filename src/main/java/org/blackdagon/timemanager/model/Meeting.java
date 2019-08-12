package org.blackdagon.timemanager.model;

import org.apache.commons.lang3.StringUtils;

public class Meeting {

    private String name;
    private String time;
    private String inJira;

    public Meeting(String name, String time) {
        this.name = name;
        this.time = time;
        this.inJira = "";
    }

    public Meeting() {
        this.name = "";
        this.time = "";
        this.inJira = "";
    }

    public Boolean isEmpty() {
        return StringUtils.isBlank(name) && StringUtils.isBlank(time);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInJira() {
        return inJira;
    }

    public void setInJira(String jira) {
        this.inJira = jira;
    }
}
