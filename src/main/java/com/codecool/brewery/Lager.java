package com.codecool.brewery;

import java.time.LocalDate;

public class Lager extends Beer{

    Lager(String name, double alcoholContent, LocalDate productionDate, int fermentationDay, int maxStoreTemp) {
        super("Lager", alcoholContent, LocalDate.now(), 21, 5);
    }
}
