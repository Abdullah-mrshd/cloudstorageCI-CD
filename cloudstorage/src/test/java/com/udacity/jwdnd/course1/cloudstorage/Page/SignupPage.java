package com.udacity.jwdnd.course1.cloudstorage.Page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage
{
    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy( id = "inputUsername")
    private WebElement inputUsername;

    @FindBy( id = "inputPassword")
    private WebElement inputPassword;

    @FindBy( css = "div.alert.alert-dark")
    private WebElement signupMessage;

    public SignupPage(WebDriver driver)
    {
        PageFactory.initElements(driver, this);
    }

    public void signup(String firstName, String lastName, String username, String password)
    {

        inputFirstName.sendKeys(firstName);
        inputLastName.sendKeys(lastName);
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        inputPassword.submit();
    }

    public boolean isSignupMessageDisplayed()
    {
        return signupMessage.isDisplayed();
    }
}
