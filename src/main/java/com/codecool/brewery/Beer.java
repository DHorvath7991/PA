package com.codecool.brewery;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Represents a beer produced by the brewery.
 * This is a basic implementation - you need to extend this to support different beer types.
 */
abstract class Beer {
    private String name;
    private double alcoholContent;
    private LocalDate productionDate;
    private int fermentationDay;
    private int maxStoreTemp;

    Beer(String name, double alcoholContent, LocalDate productionDate, int fermentationDay, int maxStoreTemp) {}

    // TODO: Add method to calculate days until beer is ready for sale
    // TODO: Consider how to handle different fermentation times for different beer types
    public void getDaysUntilReady() {
        LocalDate productionDate = LocalDate.now();
        LocalDate fermentationEndDate = productionDate.plusDays(this.fermentationDay);

        long dayRemaining = ChronoUnit.DAYS.between(LocalDate.now(), fermentationEndDate);
        if (dayRemaining > 0) {
            System.out.println(dayRemaining +" day until your fermentation is over.");
        } else {
            System.out.println("Your fermentation is over.");
        }
    }

    // TODO: Add method to get storage temperature requirement
    // TODO: Consider canBeStoredAt method may accept parameters for temperature checks
    public boolean canBeStoredAt(int temp) {
        return temp <= getMaxStoreTemp();
    }

    // TODO: Add method to get fermentation days
    public int getFermentationDays() {
        return this.fermentationDay;
    }

    protected int getMaxStoreTemp() {
        return this.maxStoreTemp;
    }
}
