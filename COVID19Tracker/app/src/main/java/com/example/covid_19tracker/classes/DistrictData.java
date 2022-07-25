package com.example.covid_19tracker.classes;

public class DistrictData {
    String notes;
    Integer active;
    Integer confirmed;
    Integer migratedother;
    Integer deceased;
    Integer recovered;
    Delta delta;

    public DistrictData(String notes, Integer active, Integer confirmed, Integer migratedother, Integer deceased, Integer recovered, Delta delta) {
        this.notes = notes;
        this.active = active;
        this.confirmed = confirmed;
        this.migratedother = migratedother;
        this.deceased = deceased;
        this.recovered = recovered;
        this.delta = delta;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Integer confirmed) {
        this.confirmed = confirmed;
    }

    public Integer getMigratedother() {
        return migratedother;
    }

    public void setMigratedother(Integer migratedother) {
        this.migratedother = migratedother;
    }

    public Integer getDeceased() {
        return deceased;
    }

    public void setDeceased(Integer deceased) {
        this.deceased = deceased;
    }

    public Integer getRecovered() {
        return recovered;
    }

    public void setRecovered(Integer recovered) {
        this.recovered = recovered;
    }

    public Delta getDelta() {
        return delta;
    }

    public void setDelta(Delta delta) {
        this.delta = delta;
    }
}
