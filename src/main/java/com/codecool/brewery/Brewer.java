package com.codecool.brewery;

import java.time.DayOfWeek;
import java.util.List;

public abstract class Brewer {
    private String name;
    private String role;
    private int yearsOfExperience;
    private List<DayOfWeek> workingDays;

    public Brewer(String name, String role, int yearsOfExperience, List<DayOfWeek> workingDays) {
        this.name = name;
        this.role = role;
        this.yearsOfExperience = yearsOfExperience;
        this.workingDays = workingDays;
    }

    public boolean isAvailableOn(DayOfWeek day) {
        return this.workingDays.contains(day);
    }

    public String getName() {
        return name;
    }

    public boolean isMaster() {
        return this.role.equals("master");
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public List<DayOfWeek> getWorkingDays() {
        return workingDays;
    }
}