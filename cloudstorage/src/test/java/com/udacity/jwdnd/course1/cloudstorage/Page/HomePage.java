
package com.udacity.jwdnd.course1.cloudstorage.Page;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage
{
    WebDriver driver;

    @FindBy( id = "logout-button")
    private WebElement logoutButton;

    /*
     * Notes elements
     */
    @FindBy( xpath = "//a[@id='nav-notes-tab']")
    private WebElement notesTab;

    @FindBy( xpath = "//div/button[@id='add-note-button']")
    private WebElement addNoteButton;

    @FindBy( id = "note-title")
    private WebElement noteTitleInput;

    @FindBy( id = "note-description")
    private WebElement noteDescriptionInput;

    @FindBy( id = "note-title-edit")
    private WebElement noteTitleEditInput;

    @FindBy( id = "note-description-edit")
    private WebElement noteDescriptionEditInput;

    @FindBy(id ="notes-table")
    private WebElement notesTable;

    @FindBy( xpath = "//table[@id='userTable']/tbody[@id='notes-table']/tr")
    private WebElement noteTableRow;

    @FindBy(id = "title-column")
    private WebElement noteTitleColumn;

    @FindBy( id = "description-column")
    private WebElement noteDescriptionColumn;

    @FindBy(id = "edit-note-button")
    private WebElement editNoteButton;

    @FindBy( xpath = "//a[@id='delete-note-a']")
    private WebElement deleteNoteButton;

    /*
     * Credentials elements
     */
    @FindBy( id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy( id = "add-credential-button")
    private WebElement addCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement urlInput;

    @FindBy( id = "credential-username")
    private WebElement usernameInput;

    @FindBy( id = "credential-password")
    private WebElement passwordInput;

    @FindBy(id = "credential-url-edit")
    private WebElement urlEditInput;

    @FindBy( id = "credential-username-edit")
    private WebElement usernameEditInput;

    @FindBy( id = "credential-password-edit")
    private WebElement passwordEditInput;

    @FindBy( id = "credentials-table")
    private WebElement credentialsTable;

    @FindBy( xpath = "//table[@id='credentialTable']/tbody[@id='credentials-table']/tr")
    private WebElement credentialTableRow;

    @FindBy( id = "url-column")
    private WebElement credentialUrlColumn;

    @FindBy( id = "username-column")
    private WebElement credentialUsernameColumn;

    @FindBy( id = "password-column")
    private WebElement credentialPasswordColumn;

    @FindBy( id = "edit-credential-button")
    private WebElement editCredentialButton;

    @FindBy( id = "delete-credential-a")
    private WebElement deleteCredentialButton;


    public HomePage(WebDriver driver)
    {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    private void waitForVisibility(WebElement element) throws Error {
        new WebDriverWait(driver, 60)
                .until(ExpectedConditions.visibilityOf(element));
    }

    public void click(WebElement element)
    {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].click();", element);
    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public void clickLogout()
    {
        waitForVisibility(logoutButton);
        click(logoutButton);
    }

    public void addNewNote(String noteTitle, String noteDescription) {
        goToNotesTab();
        waitForVisibility(addNoteButton);
        addNoteButton.click();
        saveNote(noteTitle, noteDescription);
    }

    public void editFirstNote(String noteTitle, String noteDescription) {
        goToNotesTab();
        waitForVisibility(editNoteButton);
        editNoteButton.click();
        clearNoteEditInputs();
        saveNoteEdit(noteTitle, noteDescription);
    }

    public void deleteFirstNote()
    {
        goToNotesTab();
        waitForVisibility(deleteNoteButton);
        click(deleteNoteButton);
    }

    public Note getFirstNote() {
        waitForVisibility(noteTitleColumn);
        Note result = new Note();
        result.setNoteTitle(noteTitleColumn.getText());
        result.setNoteDescription(noteDescriptionColumn.getText());
        return result;
    }

    private void saveNote(String noteTitle, String noteDescription) {
        waitForVisibility(noteTitleInput);
        this.noteTitleInput.sendKeys(noteTitle);
        this.noteDescriptionInput.sendKeys(noteDescription);
        noteTitleInput.submit();
    }

    // Editing note form is exactly like add note form, but different instances
    private void saveNoteEdit(String noteTitle, String noteDescription) {
        waitForVisibility(noteTitleEditInput);
        this.noteTitleEditInput.sendKeys(noteTitle);
        this.noteDescriptionEditInput.sendKeys(noteDescription);
        noteTitleEditInput.submit();
    }

    public void clearNoteEditInputs() {
        waitForVisibility(noteTitleEditInput);
        this.noteTitleEditInput.clear();
        this.noteDescriptionEditInput.clear();
    }
    public void goToNotesTab() {
        waitForVisibility(notesTab);
        click(notesTab);
    }

    public void goToCredentialsTab() {
        waitForVisibility(credentialsTab);
        click(credentialsTab);
    }


    // check if there is row in the notes table
    public boolean isNoteDisplayed()
    {
        waitForVisibility(notesTable);
        click(notesTable);
        return noteTableRow.isDisplayed();
    }

    public boolean isNoteTitleDisplayed() {
        return isElementPresent(By.id("title-column"));
    }

    public boolean isNoteDescriptionDisplayed() {
        return isElementPresent(By.id("description-column"));
    }

    public void addNewCredential(String url, String username, String password) {
        goToCredentialsTab();
        waitForVisibility(addCredentialButton);
        click(addCredentialButton);
        saveCredential(url, username, password);
    }

    private void saveCredential(String url, String username, String password) {
        waitForVisibility(urlInput);
        urlInput.sendKeys(url);
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        passwordInput.submit();
    }

    public Credential getFirstCredential() {
        waitForVisibility(credentialsTable);
        Credential result = new Credential();
        result.setUrl(credentialUrlColumn.getText());
        result.setUsername(credentialUsernameColumn.getText());
        result.setPassword(credentialPasswordColumn.getText());
        return result;
    }

    public void editFirstCredential(String url, String username, String password) {
        goToCredentialsTab();
        waitForVisibility(editCredentialButton);
        click(editCredentialButton);
        clearCredentialEditInputs();
        saveCredentialEdit(url, username, password);
    }

    public void clearCredentialEditInputs()
    {
        waitForVisibility(urlEditInput);
        urlEditInput.clear();
        usernameEditInput.clear();
        passwordEditInput.clear();
    }

    public void saveCredentialEdit(String url, String username, String password)
    {
        waitForVisibility(urlEditInput);
        urlEditInput.sendKeys(url);
        usernameEditInput.sendKeys(username);
        passwordEditInput.sendKeys(password);
        passwordEditInput.submit();
    }

    public void deleteFirstCredential()
    {
        goToCredentialsTab();
        waitForVisibility(deleteCredentialButton);
        click(deleteCredentialButton);
    }

    public boolean isCredentialDisplayed()
    {
        waitForVisibility(credentialsTable);
        click(credentialsTab);
        return credentialTableRow.isDisplayed();
    }

    public boolean isUrlDisplayed() {
        return isElementPresent(By.id("url-column"));
    }

    public boolean isUsernameDisplayed() {
        return isElementPresent(By.id("username-column"));
    }

    public boolean isPasswordDisplayed() {
        return isElementPresent(By.id("password-column"));
    }

}
