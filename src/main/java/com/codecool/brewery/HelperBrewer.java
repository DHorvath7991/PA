package com.codecool.brewery;

import java.time.DayOfWeek;
import java.util.List;

public class HelperBrewer extends Brewer{

    public HelperBrewer(String name, int yearsOfExperience, List<DayOfWeek> workingDays) {
        super(name, "helper", yearsOfExperience, workingDays);
    }

    @Override
    public boolean isMaster() {
        return false;
    }
}
