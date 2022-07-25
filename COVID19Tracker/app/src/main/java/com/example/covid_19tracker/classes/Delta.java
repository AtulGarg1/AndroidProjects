package com.example.covid_19tracker.classes;

public class Delta {
    Integer confirmed;
    Integer deceased;
    Integer recovered;

    public Delta(Integer confirmed, Integer deceased, Integer recovered) {
        this.confirmed = confirmed;
        this.deceased = deceased;
        this.recovered = recovered;
    }

    public Integer getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Integer confirmed) {
        this.confirmed = confirmed;
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
}
