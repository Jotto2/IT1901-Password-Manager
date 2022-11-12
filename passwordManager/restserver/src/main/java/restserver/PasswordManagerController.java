package restserver;

import core.database.DatabaseTalker;
import core.database.JsonDatabaseTalker;
import core.userbuilder.PasswordValidation;
import core.userbuilder.UserBuilder;
import core.userbuilder.UsernameValidation;
import core.User;
import core.Profile;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/entries")
public class PasswordManagerController {

  private String url = "../localpersistence/src/resources/localpersistance/Users.json";

  private User user;

  @GetMapping(value = "/login")
  public @ResponseBody String getProfiles(@RequestParam String username,
      @RequestParam String password) {

    DatabaseTalker databaseTalker = new JsonDatabaseTalker(url);

    if (databaseTalker.checkPassword(username, password)) {
      this.user = new User(username, password);
      return "Success";
    } else {
      return "Failure";
    }
  }

  @GetMapping(value = "/getProfiles")
  public @ResponseBody String getProfiles() {
    DatabaseTalker databaseTalker = new JsonDatabaseTalker(url);
    ArrayList<Profile> profiles = databaseTalker.getProfiles(user.getUsername());
    JSONArray jsonArray = new JSONArray();
    for (Profile profile : profiles) {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("username", profile.getProfileUsername());
      jsonObject.put("title", profile.getTitle());
      jsonObject.put("password", profile.getEncryptedPassword());
      jsonArray.put(jsonObject);
    }
    return jsonArray.toString();
  }

  /*
   * databaseTalker.insertUser(new User(username, password));
   */
  @PostMapping(value = "/register")
  public @ResponseBody String postRegister(@RequestBody String body) {

    DatabaseTalker databaseTalker = new JsonDatabaseTalker(url);
    JSONObject jsonObject = new JSONObject(body);
    String username = jsonObject.getString("username");
    String password = jsonObject.getString("password");
    if (databaseTalker.insertUser(new User(username, password))) {
      return "Success";
    } else {
      return "Failure";
    }
  }

  // Get username
  @GetMapping(value = "/username")
  public @ResponseBody String getUsername() {
    return user.getUsername();
  }

  // Logout
  @GetMapping(value = "/logout")
  public @ResponseBody String logout() {
    user = null;
    return "Success";
  }

  // Insert profile
  @PostMapping(value = "/insertProfile")
  public @ResponseBody String insertProfile(@RequestBody String body) {

    DatabaseTalker databaseTalker = new JsonDatabaseTalker(url);
    JSONObject jsonObject = new JSONObject(body);
    String username = jsonObject.getString("username");
    String title = jsonObject.getString("title");
    String password = jsonObject.getString("password");
    if (databaseTalker.insertProfile(user.getUsername(),
        new Profile(title, username, password, user.getUsername()))) {
      return "Success";
    } else {
      return "Failure";
    }
  }


  /**
   * userValidator that validates the user input for username and password.
   * 
   * @param username a username to check
   * @param password a password to check
   * @param passwordRepeat repeated password provided by the user
   * @return a string which contains the message explaining what why the username/password is not
   *         accepted, if the username and passwords are accepted, and the passwordRepeat ==
   *         password, the function return "OK"
   */
  public String userValidator(String username, String password, String passwordRepeat) {

    DatabaseTalker databaseTalker = new JsonDatabaseTalker(url);
    UserBuilder userBuilder = new UserBuilder(databaseTalker);
    userBuilder.setUsername(username);
    userBuilder.setPassword(password);

    UsernameValidation usernameValidation = userBuilder.setUsername(username);
    PasswordValidation passwordValidation = userBuilder.setPassword(password);

    String message = null;
    if (!password.equals(passwordRepeat)) {
      message = "Passwords does not match";
    }
    switch (usernameValidation) {
      case OK:
        switch (passwordValidation) {
          case OK:
            if (message == null) {
              message = "OK";
            }
            break;
          case diversityError:
            message =
                "Password must contain: uppercase letter, lowercase letter, digit and a symbol";
            break;
          case tooShort:
            message = "Password is too short";
            break;
          case tooLong:
            message = "Password is too long";
            break;
          default:
            message = "something went wrong with the validator";
            break;
        }
        break;
      case alreadyTaken:
        message = "Username is allready taken";
        break;
      case invalidCharacters:
        message = "Username must only contain letters and digits";
        break;
      case tooShort:
        message = "Username is too short";
        break;
      case tooLong:
        message = "Username is too long";
        break;
      default:
        message = "something went wrong with the validator";
        break;
    }
    return message;
  }

  // Uservalidator
  @GetMapping(value = "/userValidator")
  public @ResponseBody String getUserValidator(@RequestParam String username,
      @RequestParam String password, @RequestParam String passwordRepeat) {
    return userValidator(username, password, passwordRepeat);
  }
  //Delete profile
  @PostMapping(value = "/deleteProfile")
  public @ResponseBody String deleteProfile(@RequestBody String body) {
    DatabaseTalker databaseTalker = new JsonDatabaseTalker(url);
    JSONObject jsonObject = new JSONObject(body);
    String username = jsonObject.getString("username");
    String title = jsonObject.getString("title");
    String password = jsonObject.getString("password");
    if (databaseTalker.deleteProfile(user.getUsername(), new Profile(username, title, password, user.getUsername()))) {
      return "Success";
    } else {
      return "Failure";
    }
  }
}
