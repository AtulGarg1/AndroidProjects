package com.example.covid_19tracker.classes;

import java.util.ArrayList;

public class State {
    String stateName;
    ArrayList<District> district;
    String statecode;
    Boolean isOpen;

    public State(String stateName, ArrayList<District> district, String statecode) {
        this.stateName = stateName;
        this.district = district;
        this.statecode = statecode;
        this.isOpen = false;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public ArrayList<District> getDistrict() {
        return district;
    }

    public void setDistrict(ArrayList<District> district) {
        this.district = district;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }
}
