package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.Page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.Page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.Page.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;
	private String baseURL;

	private WebDriver driver;
	private WebDriverWait wait;
	private LoginPage loginPage;
	private SignupPage signupPage;
	private HomePage homePage;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();

		baseURL = "http://localhost:" + port;

		loginPage = new LoginPage(driver);
		signupPage = new SignupPage(driver);
		homePage = new HomePage(driver);
		wait = new WebDriverWait(driver, 10);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}


	@Test
	public void getLoginPage() {
		driver.get(baseURL + "/login");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testUnauthorizedUserCanOnlyAccessLoginAndSignUpPage()
	{
		//navigate to home page and verify that unauthorized user has no access
		driver.get(baseURL + "/home");
		assertEquals("Login", driver.getTitle());

		//navigate to sign up page and verify that unauthorized user has access
		driver.get(baseURL + "/signup");
		assertEquals("Sign Up", driver.getTitle());
	}
	@Test
	public void testUnregisteredUsersInaccessible()
	{
		// submit unregistered credential
		driver.get(baseURL + "/login");
		loginPage.enterCredentialsAndSubmit("user", "pass");

		// verify error message is displayed
		assertEquals("Invalid username or password", loginPage.getDisplayedErrorMessage());
	}


	@Test
	public void testHomePageInaccessibleAfterLogout() throws InterruptedException
	{
		// register new account then login
		String firstname = "user1";
		String lastname = "user1";
		String username = "user1";
		String password = "user1";
		signupAndLogin(firstname, lastname, username, password);

		// verify home page is accessible
		assertEquals("Home", driver.getTitle());

		// log out
		homePage.clickLogout();

		//verify that home page is no longer visible
		wait.until(webDriver -> webDriver.findElement(By.id("login-title")));
		assertFalse("Home".equalsIgnoreCase(driver.getTitle()));
		assertEquals("Login", driver.getTitle());
	}

	/**
	 * Write a test that creates a note, and verifies it is displayed.
	 */
	@Test
	public void testAddNoteAndVerifyItDisplayed()
	{
		// register new account then login
		String firstname = "user2";
		String lastname = "user2";
		String username = "user2";
		String password = "user2";
		signupAndLogin(firstname, lastname, username, password);

		//create note
		String noteTitle = "GitHub";
		String noteDescription = "GitHub is code repository";

		homePage.addNewNote(noteTitle, noteDescription);

		//navigate to home page
		driver.get(baseURL + "/home");

		//verify note created and displayed
		homePage.goToNotesTab();

		assertEquals(true, homePage.isNoteDisplayed());
	}

	/**
	 * Write a test that edits an existing note and verifies that the changes are displayed.
	 */
	@Test
	public void testEditNoteAndVerifyChangesAreDisplayed()
	{
		// register new account then login
		String firstname = "user3";
		String lastname = "user3";
		String username = "user3";
		String password = "user3";
		signupAndLogin(firstname, lastname, username, password);


		//create Note
		String noteTitle = "GitHub";
		String noteDesc = "GitHub is code repository";

		homePage.addNewNote(noteTitle, noteDesc);

		//navigate to home page
		driver.get(baseURL + "/home");

		//edit note
		String newNoteTitle = "Bit Bucket";
		String newNoteDesc = "Bit Bucket is code repository and SCM";

		homePage.editFirstNote(newNoteTitle, newNoteDesc);

		//navigate to home page
		driver.get(baseURL + "/home");

		//verify note update
		homePage.goToNotesTab();

		//retrieving displayed note and verify edit is saved
		Note firstNote = homePage.getFirstNote();
		assertEquals(newNoteTitle, firstNote.getNoteTitle());
		assertEquals(newNoteDesc, firstNote.getNoteDescription());
		assertFalse(noteTitle.equalsIgnoreCase(firstNote.getNoteTitle()));
		assertFalse(noteDesc.equalsIgnoreCase(firstNote.getNoteDescription()));
	}

	/**
	 * Write a test that deletes a note and verifies that the note is no longer displayed.
	 */
	@Test
	public void testDeleteNoteAndVerifyItNotDisplayed()
	{
		// register new account then login
		String firstname = "user4";
		String lastname = "user4";
		String username = "user4";
		String password = "user4";
		signupAndLogin(firstname, lastname, username, password);

		//create note
		String noteTitle = "GitHub";
		String noteDescription = "GitHub is code repository";

		homePage.addNewNote(noteTitle, noteDescription);

		//navigate to home page
		driver.get(baseURL + "/home");

		//delete note
		homePage.deleteFirstNote();


		//navigate to notes
		driver.get(baseURL + "/home");
		homePage.goToNotesTab();

		assertFalse(homePage.isNoteTitleDisplayed());
		assertFalse(homePage.isNoteDescriptionDisplayed());
	}

	/**
	 * Write a test that creates a set of credentials,
	 * verifies that they are displayed,
	 * and verifies that the displayed password is encrypted.
	 */
	@Test
	public void testCreateCredentialThenVerifyItDisplayedAndPasswordEncrypted()
	{
		// register new account then login
		String firstname = "user5";
		String lastname = "user5";
		String username = "user5";
		String password = "user5";
		signupAndLogin(firstname, lastname, username, password);

		//create credential
		String urlCredential = "twitter.com";
		String usernameCredential = "ekjge";
		String passwordCredential = "kejfkej";

		homePage.addNewCredential(urlCredential, usernameCredential, passwordCredential);

		//navigate to home page
		driver.get(baseURL + "/home");

		//verify note created and displayed
		homePage.goToCredentialsTab();

		//Verify saved credential is displayed
		Credential firstCredential = homePage.getFirstCredential();
		assertEquals(urlCredential, firstCredential.getUrl());
		assertEquals(usernameCredential, firstCredential.getUsername());
		assertFalse(passwordCredential.equals(firstCredential.getPassword()));
	}

	/**
	 * Write a test that views an existing set of credentials,
	 * verifies that the viewable password is unencrypted,
	 * edits the credentials, and verifies that the changes are displayed.
	 */
	@Test
	public void testViewCredentialAndVerifyPasswordUnencryptedAndEditThenVerifyEditDisplayed()
	{
		// register new account then login
		String firstname = "user6";
		String lastname = "user6";
		String username = "user6";
		String password = "user6";
		signupAndLogin(firstname, lastname, username, password);

		//create credential
		String urlCredential = "twitter.com";
		String usernameCredential = "beforeEditUser";
		String passwordCredential = "beforeEditPassword";

		homePage.addNewCredential(urlCredential, usernameCredential, passwordCredential);

		//navigate to home page
		driver.get(baseURL + "/home");

		//edit credential
		String newUrl = "youtube.com";
		String newUsername = "editUsername";
		String newPassword = "editPassword";

		homePage.editFirstCredential(newUrl, newUsername, newPassword);


		//navigate to home page
		driver.get(baseURL + "/home");

		//verify credential update
		homePage.goToCredentialsTab();

		//retrieving displayed note and verify edit is saved
		Credential firstCredential = homePage.getFirstCredential();
		assertEquals(newUrl, firstCredential.getUrl());
		assertEquals(newUsername, firstCredential.getUsername());
		assertFalse(urlCredential.equalsIgnoreCase(firstCredential.getUrl()));
		assertFalse(usernameCredential.equalsIgnoreCase(firstCredential.getUsername()));

	}

	/**
	 * Write a test that deletes an existing set of credentials
	 * and verifies that the credentials are no longer displayed.
	 */
	@Test
	public void testDeleteCredentialAndVerifyItNotDisplayed()
	{
		// register new account then login
		String firstname = "user7";
		String lastname = "user7";
		String username = "user7";
		String password = "user7";
		signupAndLogin(firstname, lastname, username, password);

		//create credential
		String urlCredential = "twitter.com";
		String usernameCredential = "twitterUsername";
		String passwordCredential = "twitterPassword";

		homePage.addNewCredential(urlCredential, usernameCredential, passwordCredential);

		//navigate to home page
		driver.get(baseURL + "/home");

		//delete note
		homePage.deleteFirstCredential();


		//navigate to notes
		driver.get(baseURL + "/home");
		homePage.goToCredentialsTab();

		assertFalse(homePage.isUrlDisplayed());
		assertFalse(homePage.isUsernameDisplayed());
		assertFalse(homePage.isPasswordDisplayed());


	}

	public void signupAndLogin(String firstname, String lastname, String username, String password)
	{
		driver.get(baseURL + "/signup");
		signupPage.signup(firstname, lastname, username, password);

		driver.get(baseURL + "/login");
		loginPage.enterCredentialsAndSubmit(username, password);

	}


}

