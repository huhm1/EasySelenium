/*
 * All the code that follow is
 * Copyright (c) 2020, Haiming Hu. All Rights Reserved.
 * Not for reuse without permission.
 */

package test.pageObjects.google;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class Search{
    private WebDriver        driver;

    @FindBy(name = "btnK")
    private WebElement       googleSearch;

    @FindBy(name = "q")
    private WebElement       SearchTextField;

    // @FindBy(css = "#rso > div")
    @FindBy(css = "div.tF2Cxc")
    private List<WebElement> Results;

    public Search(){}

    public Search(final WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);
    }

    /**
     * Click on Google Search Button.
     *
     * @return the google class instance.
     */
    public Search clickGoogleSearchButton(){
        this.googleSearch.click();
        return this;
    }

    /**
     * Set Search Text field.
     *
     * @return the google class instance.
     */
    public Search setSearchTextField(final String keyWord){
        this.SearchTextField.clear();
        this.SearchTextField.sendKeys(keyWord + Keys.RETURN);
        return this;
    }

    public List<WebElement> getResultsList(){
        return this.Results;
    }

    public String getResultTitle(final int numberOfResult){
        return this.Results.get(numberOfResult).findElement(By.cssSelector("a>h3")).getText();
    }

    public String getResultUrl(final int numberOfResult){
        return this.Results.get(numberOfResult).findElement(By.cssSelector("a")).getAttribute("href");
    }

    public String getResultDescription(final int numberOfResult){
        return this.Results.get(numberOfResult).findElement(By.cssSelector("div.IsZvec span")).getText();
    }
}
