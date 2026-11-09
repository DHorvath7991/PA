package com.codecool.brewery;

import java.time.LocalDate;

public class Ale extends Beer{

    Ale(String name, double alcoholContent, LocalDate productionDate) {
        super(name,"Ale", alcoholContent, productionDate, 14, 23);
    }
}
