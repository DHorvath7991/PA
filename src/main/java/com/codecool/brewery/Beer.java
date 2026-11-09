package com.codecool.brewery;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Represents a beer produced by the brewery.
 * This is a basic implementation - you need to extend this to support different beer types.
 */
public abstract class Beer {
    private String name;
    private String type;
    private double alcoholContent;
    private LocalDate productionDate;
    private int fermentationDay;
    private int maxStoreTemp;

    protected Beer(String name, String type, double alcoholContent, LocalDate productionDate, int fermentationDay, int maxStoreTemp) {
        getValidAlcoholContent(alcoholContent);

        this.name = name;
        this.type = type;
        this.alcoholContent = alcoholContent;
        this.productionDate = productionDate;
        this.fermentationDay = fermentationDay;
        this.maxStoreTemp = maxStoreTemp;
    }

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

    public LocalDate getProductionDate() {
        return productionDate;
    }

    private void getValidAlcoholContent(double alcoholContent) {
        if (alcoholContent < 0 || alcoholContent >100) {
            throw new IllegalArgumentException("The alcohol percentage should be between 0% and 100%!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        Beer beer = (Beer) o;
        return Double.compare(beer.alcoholContent, alcoholContent) == 0 &&
                Objects.equals(name, beer.name) &&
                Objects.equals(productionDate, beer.productionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, alcoholContent, productionDate);
    }
}
