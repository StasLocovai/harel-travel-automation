package com.harel.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

public class TravelPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By firstPurchaseBtn =
            By.cssSelector("button[data-hrl-bo='purchase-for-new-customer']");

    private final By destinations =
            By.cssSelector("div[id^='destination-'][role='radio']");

    private final By nextToDatesBtn =
            By.cssSelector("button[data-hrl-bo='wizard-next-button']");

    private final By departureInput = By.id("travel_start_date");

    private final By returnInput    = By.id("travel_end_date");

    private By dayButton(String yyyyMmDd) {
        return By.cssSelector("button[data-hrl-bo='" + yyyyMmDd + "']");
    }

    private final By totalDaysLabel = By.cssSelector("[data-hrl-bo='total-days']");


    private final By nextToPassengersBtn = By.id("nextButton");

    private final By travelerContactCard =
            By.cssSelector("div[data-hrl-bo='traveler-card-contact-person']");



    public TravelPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void clickFirstPurchase() {
        driver.findElement(firstPurchaseBtn).click();
    }

    private final Random random = new Random();

    public void selectRandomDestination() {
        List<WebElement> all = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(destinations)
        );
        int index = random.nextInt(all.size());
        WebElement chosen = all.get(index);
        chosen.click();
    }

    public void clickNextToDates() {
        WebElement next = wait.until(
                ExpectedConditions.elementToBeClickable(nextToDatesBtn)
        );
        next.click();
    }


    public long selectDepartureAndReturnDates() {

        LocalDate today = LocalDate.now();
        LocalDate departureDate = today.plusDays(7);
        LocalDate returnDate = departureDate.plusDays(30);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String depAttr = departureDate.format(formatter);
        String retAttr = returnDate.format(formatter);

        driver.findElement(departureInput).click();
        driver.findElement(dayButton(depAttr)).click();
        driver.findElement(returnInput).click();
        driver.findElement(dayButton(retAttr)).click();
        return ChronoUnit.DAYS.between(departureDate, returnDate) + 1;
    }

    public String getTotalDaysText() {
        WebElement label = wait.until(ExpectedConditions.visibilityOfElementLocated(totalDaysLabel));
        return label.getText();
    }

    public void clickNextToPassengers() {
        driver.findElement(nextToPassengersBtn).click();
    }

    public boolean isPassengersPageDisplayed() {
        return driver.findElement(travelerContactCard).isDisplayed();
    }
}
