package com.example.covid_19tracker.classes;

public class District {
    String districtName;
    DistrictData districtData;

    public District(String districtName, DistrictData districtData) {
        this.districtName = districtName;
        this.districtData = districtData;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public DistrictData getDistrictData() {
        return districtData;
    }

    public void setDistrictData(DistrictData districtData) {
        this.districtData = districtData;
    }
}
