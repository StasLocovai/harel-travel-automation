package com.harel.automation.tests;

import com.harel.automation.pages.TravelPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TravelPurchaseTest {

    private WebDriver driver;
    private TravelPage travelPage;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        travelPage = new TravelPage(driver);
    }

    @Test
    public void testTravelFlow() {
        driver.get("https://digital.harel-group.co.il/travel-policy");
        travelPage.clickFirstPurchase();
        travelPage.selectRandomDestination();
        travelPage.clickNextToDates();
        travelPage.selectDepartureAndReturnDates();
        long expectedDays = travelPage.selectDepartureAndReturnDates();
        String daysText = travelPage.getTotalDaysText();     // "סה״כ 31 ימים"
        String digits   = daysText.replaceAll("\\D+", "");   // "31"
        int actualDays  = Integer.parseInt(digits);

        Assert.assertEquals(actualDays, expectedDays, "Total days label incorrect");


        travelPage.clickNextToPassengers();
        Assert.assertTrue(travelPage.isPassengersPageDisplayed(),
                "Traveler info page did not load correctly");
    }

    @AfterClass
    public void tearDown() {
            driver.quit();
    }
}
