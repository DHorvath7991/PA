package com.codecool.brewery;

import java.time.LocalDate;

public class Ale extends Beer{

    Ale(String name, double alcoholContent, LocalDate productionDate, int fermentationDay, int maxStoreTemp) {
        super("Ale", alcoholContent, LocalDate.now(), 21, 14);
    }
}
