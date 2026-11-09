package com.codecool.brewery;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Brewery {
    private String name;
    private List<Brewer> brewers;
    private Map<Beer, Integer> stock;

    public Brewery(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("The brewery's name cannot be null or empty!");
        }

        this.name = name;
        this.brewers = new ArrayList<>();
        this.stock = new HashMap<>();
    }

    public void addBrewer(Brewer brewer) {
        if (brewer == null) {
            throw new IllegalArgumentException("The brewer can't be null!");
        }

        if (brewer.isMaster()) {
            boolean hasMaster = brewers.stream().anyMatch(Brewer::isMaster);
            if (hasMaster) {
                throw new IllegalStateException("The brewery already have a master brewer!");
            }
        }

        brewers.add(brewer);
    }

    public void addListOfBrewer(List<Brewer> newBrewers) {
        newBrewers.forEach(this::addBrewer);
    }

    public void addBeerToStock(Beer beer) {
        if (beer == null) {
            throw new IllegalArgumentException("The beer can't be null!");
        }
        stock.merge(beer, 1, Integer::sum);
    }

    public void retrieveBeerFromStock(String beerType, int quantity) {
        if (beerType == null || quantity <= 0) {
            throw new IllegalArgumentException("The type can't be null and the quantity should be more than 0!");
        }

        Beer targetBeer = stock.keySet().stream()
                .filter(beer -> beer.getName().equalsIgnoreCase(beerType))
                .findFirst()
                .orElse(null);

        if (targetBeer == null) {
            throw new IllegalArgumentException("There is no such type of beer in the stock: " + beerType);
        }

        int currentQuantity = stock.get(targetBeer);

        if (currentQuantity < quantity) {
            throw new IllegalStateException("Not enough stock for " + beerType + "! Available: " + currentQuantity + ", Requested: " + quantity);
        }

        if (currentQuantity == quantity) {
            stock.remove(targetBeer);
        } else {
            stock.put(targetBeer, currentQuantity - quantity);
        }
    }

    public int calculateProductionCapacity(List<DayOfWeek> days) {
        if (days == null || days.isEmpty()) {
            throw new IllegalArgumentException("Days can't be null or empty!");
        }

        boolean hasMaster = brewers != null && brewers.stream().anyMatch(Brewer::isMaster);

        if (!hasMaster) {
            return 0;
        }

        return days.stream()
                .mapToInt(this::countAvailableHelpers)
                .sum();
    }

    public List<String> findMaturedBeers() {
        LocalDate today = LocalDate.now();

        return stock.keySet().stream()
                .filter(beer -> beer.getProductionDate().plusDays(180).isBefore(today))
                .map(Beer::getName)
                .collect(Collectors.toList());
    }

    public List<String> findOptimalProductionDays(String startDayName, String endDayName) {
        DayOfWeek startDay = parseDayOfWeek(startDayName);
        DayOfWeek endDay = parseDayOfWeek(endDayName);

        List<DayOfWeek> daysInRange = getDaysInRange(startDay, endDay);

        if (daysInRange.isEmpty()) {
            return Collections.emptyList();
        }

        Map<DayOfWeek, Integer> helperCountByDay = daysInRange.stream()
                .collect(Collectors.toMap(
                        day -> day,
                        this::countAvailableHelpers
                ));

        int maxHelpers = helperCountByDay.values().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);

        return helperCountByDay.entrySet().stream()
                .filter(entry -> entry.getValue() == maxHelpers)
                .map(Map.Entry::getKey)
                .sorted()
                .map(DayOfWeek::toString)
                .collect(Collectors.toList());
    }

    private DayOfWeek parseDayOfWeek(String dayName) {
        try {
            return DayOfWeek.valueOf(dayName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid day name: " + dayName, e);
        }
    }

    private List<DayOfWeek> getDaysInRange(DayOfWeek start, DayOfWeek end) {
        List<DayOfWeek> days = new ArrayList<>();
        DayOfWeek current = start;

        while (true) {
            days.add(current);
            if (current == end) {
                break;
            }
            current = current.plus(1);
            if (days.size() > 7) {
                break;
            }
        }

        return days;
    }

    private int countAvailableHelpers(DayOfWeek day) {
        return (int) brewers.stream()
                .filter(brewer -> !brewer.isMaster())
                .filter(brewer -> brewer.isAvailableOn(day))
                .count();
    }
}
