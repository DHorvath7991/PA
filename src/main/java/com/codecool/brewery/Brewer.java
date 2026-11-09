package com.codecool.brewery;

import java.util.List;

public abstract class Brewer {
    private String name;
    private String role;
    private int yearsOfExperience;
    private List<Days> workingDays;

    public Brewer(String name, String role, int yearsOfExperience, List<Days> workingDays) {
        this.name = name;
        this.role = role;
        this.yearsOfExperience = yearsOfExperience;
        this.workingDays = workingDays;
    }

    public boolean isAvailableOn(Days day) {
        return this.workingDays.contains(day);
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public List<Days> getWorkingDays() {
        return workingDays;
    }
}