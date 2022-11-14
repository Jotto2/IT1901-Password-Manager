package ui;

import core.UserSession;
import client.RestTalker;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * FXML Controller class for the login page.
 */
public class LoginPageController extends PasswordManagerController {

  @FXML
  private TextField usernameTextField;

  @FXML
  private TextField passwordTextField;

  @FXML
  private Button loginButton;

  @FXML
  private Button registerButton;

  @FXML
  private Text visualFeedbackText;

  @FXML
  private PasswordField passwordPasswordField;

  @FXML
  private ImageView eyeImageView;

  /**
   * initialize sets the eye image to the open eye image.
   */
  @FXML
  public void initialize() {
    eyeImageView.setImage(eyeOpenImage);
    visualFeedbackText.setFill(Color.valueOf(lightRed));
  }

  @FXML
  private void eyeImageViewClick() {
    passwordEye(passwordTextField, passwordPasswordField, eyeImageView);
  }

  /**
   * onLoginButtonClick handles the loginbutton click, if the fields are filled in
   * it passes the username and password to the userSession class to attempt a
   * login, if it can't login or the password and/or username
   * is not filled in; The function shows the appropriate response to the user.
   * 
   * @param event ActionEvent object to be used in the switchScene method.
   * @throws IOException if there is a problem opening the new scene
   * 
   */
  @FXML
  private void onLoginButtonClick(ActionEvent event) throws IOException {

    RestTalker restTalker = new RestTalker();
    System.out.println(restTalker.test1());
    System.out.println(restTalker.test2("hello", "world"));
    System.out.println(restTalker.test3("postTest"));

    UserSession userSession;

    String username = usernameTextField.getText();
    String password = passwordTextField.getText();
    if (!passwordTextField.isVisible()) {
      password = passwordPasswordField.getText();
    }

    if (!username.equals("") && !password.equals("")) {
      userSession = UserSession.getInstance();
      if (userSession.login(username, password)) {
        ((Stage) usernameTextField.getScene().getWindow()).setUserData(userSession);
        switchScene(event, "passwords.fxml");
      }
      visualFeedbackText.setText("Wrong username or password");
    } else {
      visualFeedbackText.setText("Please fill in all fields");
    }

    visualFeedbackText.setVisible(true);
    setBorder(usernameTextField, lightRed);
    setBorder(passwordTextField, lightRed);
    setBorder(passwordPasswordField, lightRed);
    rotateNode(visualFeedbackText, false);
    userSession = null;
  }

  @FXML
  private void onRegisterButtonClick(ActionEvent event) throws IOException {
    switchScene(event, "register.fxml");
  }
}
