package com.codecool.brewery;

import java.util.ArrayList;
import java.util.List;

public class MasterBrewer extends Brewer{
    public MasterBrewer(String name, String role, int yearsOfExperience) {
        super(name, role, yearsOfExperience, Days.getAllDay());
    }

    public int getProductivity(int helperBrewerCount) {
        return helperBrewerCount;
    }
}
