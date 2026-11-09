package com.codecool.brewery;

import java.time.DayOfWeek;
import java.util.List;

public class HelperBrewer extends Brewer{

    public HelperBrewer(String name, String role, int yearsOfExperience, List<DayOfWeek> workingDays) {
        super(name, "helper", yearsOfExperience, workingDays);
    }
}
