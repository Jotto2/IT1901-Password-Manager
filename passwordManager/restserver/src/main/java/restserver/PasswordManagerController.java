package restserver;

import core.database.DatabaseTalker;
import core.database.JsonDatabaseTalker;
import core.User;
import core.Profile;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/v1/entries")
public class PasswordManagerController {

  // Get test that logs the parameters
  @GetMapping("/test1")
  public @ResponseBody String getTest() {
    return "test11";
  }

  // Post test that logs the parameters
  @PostMapping("/test3")
  public @ResponseBody String postTest(@RequestBody String body) {
    return body;
  }

  // Get test that logs the parameters
  @GetMapping("/test")
  public @ResponseBody String getTest(@RequestParam String param1, @RequestParam String param2) {
    return param1 + " " + param2;
  }


  // Test4 gets a hashmap from the body and hashmap {test: test} where test is a value from the body
  @GetMapping(value = "/test4", produces = "application/json")
  public @ResponseBody String getTest4(@RequestParam String param) {
    String id = param;
    HashMap<String, String> map = new HashMap<>();
    map.put("test", id);
    return new JSONObject(map).toString();
  }

  private static DatabaseTalker databaseTalker =
  new JsonDatabaseTalker("../localpersistence/src/resources/localpersistance/Users.json");

  private User user;

  @GetMapping(value = "/login")
  public @ResponseBody String getProfiles(@RequestParam String username,
      @RequestParam String password) {
    if (databaseTalker.checkPassword(username, password)) {
      this.user = new User(username, password);
      user.setProfiles(databaseTalker.getProfiles(user.getUsername()));

      // Get the profiles and return them as a JSON array
      ArrayList<Profile> profiles = databaseTalker.getProfiles(user.getUsername());


      return "Found User";
    } else {
      return "Invalid username or password";
    }
  }
}
