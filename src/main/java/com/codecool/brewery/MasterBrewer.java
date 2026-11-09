package com.codecool.brewery;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class MasterBrewer extends Brewer{
    public MasterBrewer(String name, String role, int yearsOfExperience) {
        super(name, "master", yearsOfExperience, List.of(DayOfWeek.values()));
    }

    public int getProductivity(int helperBrewerCount) {
        return helperBrewerCount;
    }
}
