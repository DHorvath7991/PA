package com.codecool.brewery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BreweryTest {

    private Brewery brewery;
    private Brewer masterBrewer;
    private Brewer helper1;
    private Brewer helper2;
    private Brewer helper3;

    @BeforeEach
    void setUp() {
        brewery = new Brewery("Test Brewery");

        // Set up brewers
        brewery.addListOfBrewer(
                List.of(
                        new MasterBrewer("Master John", 15),
                        new HelperBrewer("Lucius", 3, List.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY)),
                        new HelperBrewer("Laura", 3, List.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY)),
                        new HelperBrewer("Bartalos", 5, List.of(DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)),
                        new HelperBrewer("Geralt", 2, List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)),
                        new HelperBrewer("Herkules", 8, List.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY)),
                        new HelperBrewer("Matilda", 4, List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)),
                        new HelperBrewer("Sigrid", 6, List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY))
                ));
    }

    @Test
    @DisplayName("Simple Test: Production capacity on single day equals available helpers")
    void testProductionCapacityOnSingleDay() {

        int capacity = brewery.calculateProductionCapacity(List.of(DayOfWeek.MONDAY));
        assertEquals(4, capacity, "Production capacity should equal number of helpers on Monday");
    }

    @Test
    @DisplayName("Production capacity on multiple days returns sum of helpers")
    void testProductionCapacityOnMultipleDays() {
        int capacity = brewery.calculateProductionCapacity(List.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY));
        assertEquals(8, capacity, "Production capacity on multiple days should equal the sum of available helpers across those days");
    }

    @Test
    @DisplayName("Production capacity throws exception on invalid day name")
    void testProductionCapacityWithInvalidDay() {
        assertThrows(IllegalArgumentException.class, () -> {
            brewery.calculateProductionCapacity(null);
        }, "Should throw exception when day list is null");
    }

    @Test
    @DisplayName("Find expired beers returns beers older than 180 days")
    void testFindExpiredBeers() {
        brewery.addBeerToStock(new Lager("Krusovice",5.0, LocalDate.now().minusDays(100)));

        brewery.addBeerToStock(new Ale("Soproni IPA", 6.0, LocalDate.now().minusDays(200)));
        brewery.addBeerToStock(new Lager("Bernard",5.5, LocalDate.now().minusDays(190)));

        List<String> expired = brewery.findMaturedBeers();

        assertEquals(2, expired.size());
        assertTrue(expired.contains("Soproni IPA"));
        assertTrue(expired.contains("Bernard"));
        assertFalse(expired.contains("Krusovice"));
    }

    @Test
    @DisplayName("Find optimal production days returns days with most helpers")
    void testFindOptimalProductionDays() {
        List<String> optimalDays = brewery.findOptimalProductionDays("MONDAY", "FRIDAY");

        assertFalse(optimalDays.isEmpty());
        assertTrue(optimalDays.contains("MONDAY"));
        assertTrue(optimalDays.contains("THURSDAY"));
    }

    @Test
    @DisplayName("Beer type has correct fermentation days")
    void testBeerFermentationDays() {
        Beer lager = new Lager("Test lager", 5.0, LocalDate.now());
        Beer ale = new Ale("Test ale", 5.5, LocalDate.now());

        assertEquals(21, lager.getFermentationDays(), "Lager fermentation time should be 21 days");
        assertEquals(14, ale.getFermentationDays(), "Ale fermentation time should be 14 days");
    }

    @ParameterizedTest
    @CsvSource({
            "2, true, true",
            "10, true, false",
            "30, false, false"
    })
    @DisplayName("Beer types have correct storage temperatures")
    void testBeerStorageTemperature(int temp, boolean expectedAleResult, boolean expectedLagerResult) {
        Beer ale = new Ale("Test Ale", 5.5, LocalDate.now());
        Beer lager = new Lager("Test Lager", 5.0, LocalDate.now());

        assertEquals(expectedAleResult, ale.canBeStoredAt(temp),
                "Ale storage check failed at " + temp + "°C");

        assertEquals(expectedLagerResult, lager.canBeStoredAt(temp),
                "Lager storage check failed at " + temp + "°C");
    }

    @Test
    @DisplayName("Helper brewer availability is correct")
    void testHelperBrewerAvailability() {
        HelperBrewer helper = new HelperBrewer("Test Helper", 3, List.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY));

        assertTrue(helper.isAvailableOn(DayOfWeek.MONDAY), "Helper should be available on Monday");
        assertTrue(helper.isAvailableOn(DayOfWeek.THURSDAY), "Helper should be available on Thursday");
        assertFalse(helper.isAvailableOn(DayOfWeek.TUESDAY), "Helper should not be available on Tuesday");
        assertFalse(helper.isAvailableOn(DayOfWeek.WEDNESDAY), "Helper should not be available on Wednesday");
    }

}
