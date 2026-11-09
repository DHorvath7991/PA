package com.codecool.brewery;

import java.time.LocalDate;

public class Lager extends Beer{

    Lager(String name, double alcoholContent, LocalDate productionDate) {
        super(name,"Lager", alcoholContent, productionDate, 21, 5);
    }
}
