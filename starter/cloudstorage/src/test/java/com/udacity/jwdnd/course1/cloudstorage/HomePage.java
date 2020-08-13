package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.dao.IncorrectUpdateSemanticsDataAccessException;

public class HomePage {

    @FindBy(id = "logout-button")
    private WebElement logoutButton;



    /* Notes */
    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "add-note-button")
    private WebElement addNoteButton;

    @FindBy(id = "edit-note-btn")
    private WebElement editNoteButton;

    @FindBy(id = "delete-note-btn")
    private WebElement deleteNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionField;

    @FindBy(id = "save-changes-button")
    private WebElement submitNoteButton;

    @FindBy(id = "note-title-display")
    private WebElement noteTitle;

    @FindBy(id = "note-description-display")
    private WebElement noteDescription;

    public void selectNotesTab() {
        notesTab.click();
    }

    public String getNoteTitle() {
        return noteTitle.getText();
    }

    public String getNoteDescription() {
        return noteDescription.getText();
    }

    public void addNote(String noteTitle, String noteDescription) throws InterruptedException {
        selectNotesTab();
        Thread.sleep(1000);
        addNoteButton.click();
        Thread.sleep(1000);

        noteTitleField.sendKeys(noteTitle);
        Thread.sleep(1000);
        noteDescriptionField.sendKeys(noteDescription);
        Thread.sleep(1000);

        submitNoteButton.click();
        Thread.sleep(1000);
    }

    public void editNote(String modNoteTitle, String modNoteDescription) throws InterruptedException {
        editNoteButton.click();
        Thread.sleep(1000);

        noteTitleField.clear();
        noteDescriptionField.clear();
        Thread.sleep(1000);

        noteTitleField.sendKeys(modNoteTitle);
        noteDescriptionField.sendKeys(modNoteDescription);
        Thread.sleep(1000);

        submitNoteButton.click();
        Thread.sleep(1000);
    }

    public void deleteNote() throws InterruptedException {
        deleteNoteButton.click();
    }



    /* Credentials */
    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "add-credential-btn")
    private WebElement addCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialURLField;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameField;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordField;

    @FindBy(id="submit-credentials-btn")
    private WebElement credentialSubmitButton;

    @FindBy(id = "credential-URL-list")
    private WebElement credentialURL;

    @FindBy(id = "credential-username-list")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password-list")
    private WebElement credentialPassword;

    @FindBy(id = "credential-title-display")
    private WebElement credentialEditButton;

    @FindBy(id = "credential-delete-btn")
    private WebElement credentialDeleteButton;

    public void selectCredentialsTab() {
        credentialsTab.click();
    }

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver,this);
    }

    public void logout() {
        logoutButton.click();
    }

    public void addCredentials(String url, String username, String password) throws InterruptedException {
        addCredentialButton.click();
        Thread.sleep(1000);

        credentialURLField.sendKeys(url);
        credentialUsernameField.sendKeys(username);
        credentialPasswordField.sendKeys(password);
        Thread.sleep(1000);

        credentialSubmitButton.click();
    }

    public String getCredentialURL() {
        return credentialURL.getText();
    }

    public String getCredentialUsername() {
        return credentialUsername.getText();
    }

    public String getCredentialPassword() {
        return  credentialPassword.getText();
    }

    public String editCredentials(String url, String username) throws InterruptedException {
        String password = "";

        credentialEditButton.click();
        Thread.sleep(1000);

        credentialURLField.clear();
        credentialUsernameField.clear();
        Thread.sleep(1000);

        credentialURLField.sendKeys(url);
        credentialUsernameField.sendKeys(username);
        Thread.sleep(1000);

        password = password + credentialPasswordField.getAttribute("value");

        credentialSubmitButton.click();
        Thread.sleep(1000);

        return password;
    }

    public void deleteCredentials() {
        credentialDeleteButton.click();
    }






}
