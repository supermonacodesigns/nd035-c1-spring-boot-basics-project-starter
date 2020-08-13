package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "success-msg")
    private WebElement successMsg;

    @FindBy(id = "login-link")
    private WebElement loginLink;


    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver,this);
    }


    public String getFirstName() {
        return inputFirstName.getText();
    }

    public String getLastName() {
        return inputLastName.getText();
    }

    public String getUsername() {
        return inputUsername.getText();
    }

    public String getPassword() {
        return inputPassword.getText();
    }

    public void completeRegistration(String firstName, String lastName, String username, String password) {
        inputFirstName.sendKeys(firstName);
        inputLastName.sendKeys(lastName);
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);

        submitButton.click();
    }

    public void login() {
        loginLink.click();
    }
}
