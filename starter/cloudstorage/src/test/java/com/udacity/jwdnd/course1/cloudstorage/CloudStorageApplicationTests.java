package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	private LoginPage loginPage;
	private SignupPage signupPage;
	private HomePage homePage;
	private ResultPage resultPage;

	String username = "stephencody";
	String password = "password";
	String firstName = "Stephen";
	String lastName = "Cody";

	String noteTitle = "Note 1";
	String noteDescription = "This is Note 1";

	String urlCredentials = "www.radiosega.net";
	String usernameCredentials = "ResidentSD";
	String passwordCredentials = "password";

	public void signUp() throws InterruptedException {
		driver.get("http://localhost:" +this.port + "/signup");
		Thread.sleep(1000);
		signupPage = new SignupPage(driver);
		signupPage.completeRegistration(firstName,lastName,username,password);
		Thread.sleep(1000);
	}

	public void login() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		Thread.sleep(1000);
		loginPage = new LoginPage(driver);
		loginPage.login(username,password);
		Thread.sleep(1000);
	}

	public boolean notesIsEmpty() {
		return driver.findElements(By.id("note-title-display")).isEmpty();
	}

	public boolean credentialsIsEmpty() {
		return driver.findElements(By.id("credential-title-display")).isEmpty();
	}

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/** Write a test that verifies that an unauthorized user can only access the login and signup pages. */
	@Test
	public void testUnauthorizedUserAccess() {

		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" +this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		driver.get("http://localhost:" +this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());

		driver.get("http://localhost:" +this.port + "/result");
		Assertions.assertNotEquals("Result", driver.getTitle());
	}

	/** Write a test that signs up a new user, logs in, verifies that the home page is accessible,
	 logs out, and verifies that the home page is no longer accessible. */
	@Test
	public void testAuthorizedUserAccess() throws InterruptedException {

		signUp();
		login();
		driver.get("http://localhost:" +this.port + "/home");
		Assertions.assertEquals("Home",driver.getTitle());

		homePage = new HomePage(driver);
		homePage.logout();

		Thread.sleep(2000);

		loginPage = new LoginPage(driver);
		Assertions.assertEquals("You have been logged out",loginPage.getLogoutMsg());

		driver.get("http://localhost:" +this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	/** Write a test that creates a note, and verifies it is displayed. */
	@Test
	public void testNoteCreation() throws InterruptedException {
		signUp();
		login();

		Thread.sleep(1000);
		homePage = new HomePage(driver);
		homePage.addNote(noteTitle,noteDescription);
		Thread.sleep(1000);

		driver.get("http://localhost:" +this.port + "/home");
		Thread.sleep(1000);
		homePage.selectNotesTab();
		Thread.sleep(1000);
		Assertions.assertEquals(noteTitle,homePage.getNoteTitle());
	}

	/** Write a test that edits an existing note and verifies that the changes are displayed. */
	@Test
	public void testNoteModification() throws InterruptedException {

		signUp();
		login();

		homePage = new HomePage(driver);

		if (notesIsEmpty()) {
			driver.get("http://localhost:" + this.port + "/home");
			homePage.addNote(noteTitle, noteDescription);
			Thread.sleep(1000);
		}

		Thread.sleep(1000);
		driver.get("http://localhost:" +this.port + "/home");
		Thread.sleep(1000);
		homePage.selectNotesTab();
		Thread.sleep(1000);
		homePage.editNote("Note 2","This is NOT note 2");
		Thread.sleep(2000);

		driver.get("http://localhost:" +this.port + "/home");
		Thread.sleep(1000);
		homePage.selectNotesTab();
		Thread.sleep(1000);
		Assertions.assertEquals("Note 2", homePage.getNoteTitle());
		Assertions.assertEquals("This is NOT note 2", homePage.getNoteDescription());
	}

	/** Write a test that deletes a note and verifies that the note is no longer displayed. */
	@Test
	public void testNoteDeletion() throws InterruptedException {
		signUp();
		login();

		homePage = new HomePage(driver);

		if (notesIsEmpty()) {
			driver.get("http://localhost:" + this.port + "/home");
			homePage.addNote(noteTitle, noteDescription);
			Thread.sleep(1000);
		}

		driver.get("http://localhost:" +this.port + "/home");
		Thread.sleep(1000);
		homePage.selectNotesTab();
		Thread.sleep(1000);
		homePage.deleteNote();
		driver.get("http://localhost:" +this.port + "/home");
		homePage.selectNotesTab();

		Assertions.assertTrue(this::notesIsEmpty);

	}

	/** Write a test that creates a set of credentials, verifies that they are displayed,
	 and verifies that the displayed password is encrypted. */
	@Test
	public void testCredentialsCreationAndPasswordEncryption() throws InterruptedException {
		signUp();
		login();

		homePage = new HomePage(driver);
		Thread.sleep(1000);
		homePage.selectCredentialsTab();
		Thread.sleep(1000);

		homePage.addCredentials(urlCredentials,usernameCredentials,passwordCredentials);
		Thread.sleep(1000);

		driver.get("http://localhost:" +this.port + "/home");

		Thread.sleep(1000);
		homePage.selectCredentialsTab();
		Thread.sleep(1000);

		Assertions.assertEquals(urlCredentials, homePage.getCredentialURL());
		Assertions.assertEquals(usernameCredentials, homePage.getCredentialUsername());
		Assertions.assertNotEquals(passwordCredentials, homePage.getCredentialPassword());

	}

	/** Write a test that views an existing set of credentials, verifies that the viewable password is unencrypted,
	 edits the credentials, and verifies that the changes are displayed. */
	@Test
	public void testCredentialsModificationAndPasswordDecryption() throws InterruptedException {
		signUp();
		login();

		homePage = new HomePage(driver);
		homePage.selectCredentialsTab();
		Thread.sleep(1000);
		if (credentialsIsEmpty()) {
			homePage.addCredentials(urlCredentials,usernameCredentials,passwordCredentials);
		}

		Thread.sleep(1000);
		driver.get("http://localhost:" +this.port + "/home");
		Thread.sleep(1000);
		homePage.selectCredentialsTab();
		Thread.sleep(1000);

		String plaintextPassword = homePage.editCredentials("www.discord.com","Virtua");

		driver.get("http://localhost:" +this.port + "/home");
		Thread.sleep(1000);
		homePage.selectCredentialsTab();
		Thread.sleep(1000);

		Assertions.assertEquals(password, plaintextPassword);
		Assertions.assertEquals("www.discord.com", homePage.getCredentialURL());
		Assertions.assertEquals("Virtua", homePage.getCredentialUsername());

	}

	/** Write a test that deletes an existing set of credentials and verifies that the credentials
	 are no longer displayed. */
	@Test
	public void testCredentialsDeletion() throws InterruptedException {
		signUp();
		login();

		homePage = new HomePage(driver);
		homePage.selectCredentialsTab();
		Thread.sleep(1000);
		if (credentialsIsEmpty()) {
			homePage.addCredentials(urlCredentials,usernameCredentials,passwordCredentials);
		}

		Thread.sleep(1000);
		driver.get("http://localhost:" +this.port + "/home");
		Thread.sleep(1000);
		homePage.selectCredentialsTab();
		Thread.sleep(1000);

		homePage.deleteCredentials();

		driver.get("http://localhost:" +this.port + "/home");
		Thread.sleep(1000);

		Assertions.assertTrue(credentialsIsEmpty());

	}

}
