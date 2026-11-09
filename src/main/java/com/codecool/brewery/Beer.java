package com.codecool.brewery;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Represents a beer produced by the brewery.
 * This is a basic implementation - you need to extend this to support different beer types.
 */
public abstract class Beer {
    private String name;
    private double alcoholContent;
    private LocalDate productionDate;
    private int fermentationDay;
    private int maxStoreTemp;

    Beer(String name, double alcoholContent, LocalDate productionDate, int fermentationDay, int maxStoreTemp) {}

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

    public boolean canBeStoredAt(int temp) {
        return temp <= getMaxStoreTemp();
    }

    public int getFermentationDays() {
        return this.fermentationDay;
    }

    protected int getMaxStoreTemp() {
        return this.maxStoreTemp;
    }

    public String getName() {
        return this.name;
    }
}
