package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id ="submit-button")
    private WebElement submitButton;

    @FindBy(id = "signup-link")
    private WebElement signupLink;

    @FindBy(id = "logout-msg")
    private WebElement logoutMsg;


    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public String getUsername() {
        return inputUsername.getText();
    }

    public String getPassword() {
        return inputPassword.getText();
    }

    public String getLogoutMsg() {
        return logoutMsg.getText();
    }


    public void login(String username, String password) {
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        submitButton.click();
    }

    public void gotoSignup() {
        signupLink.click();
    }

}
