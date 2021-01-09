package com.udacity.jwdnd.course1.cloudstorage.Page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage
{

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy ( css = "div.alert.alert-danger")
    private WebElement alertDangerDisplay;

    @FindBy(css = "div.alert.alert-dark")
    private WebElement alertDarkDisplay;


    public LoginPage(WebDriver driver)
    {
        PageFactory.initElements(driver, this);
    }

    public void enterCredentialsAndSubmit(String username, String password)
    {
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        inputPassword.submit();
    }

    public String getDisplayedErrorMessage()
    {
        if(alertDangerDisplay.isDisplayed())
            return alertDangerDisplay.getText();

        return null;
    }

    public String getLogoutMessage()
    {
        if(alertDarkDisplay.isDisplayed())
            return alertDarkDisplay.getText();

        return null;
    }

}
