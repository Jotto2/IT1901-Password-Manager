package ui;

import core.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import java.io.IOException;

public class LoginPageController extends PasswordManagerController {

  @FXML
  private TextField usernameTextField, passwordTextField;

  @FXML
  private Button loginButton, registerButton;

  @FXML
  private Text visualFeedbackText;

  @FXML
  private PasswordField passwordPasswordField;

  @FXML
  private ImageView eyeImageView;


  // ----- //

  @FXML
  public void initialize() {
    eyeImageView.setImage(super.eyeOpenImage);
  }

  @FXML
  private void eyeImageViewClick() {
    super.passwordEye(passwordTextField, passwordPasswordField, eyeImageView);
  }

  /**
   * onLoginButtonClick handles the loginbutton click, if the fields are filled in
   * it passes the username and password to the userSession class to attempt a
   * login, if it can't login or the password and/or username
   * is not filled in; The function shows the appropriate response to the user.

   * @param event ActionEvent object to be used in the switchScene method.
   * @throws IOException if there is a problem opening the new scene
   * 
   */
  @FXML
  private void onLoginButtonClick(ActionEvent event) throws IOException {
    UserSession userSession;

    String username = usernameTextField.getText();
    String password = passwordTextField.getText();
    if (!passwordTextField.isVisible()) {
      password = passwordPasswordField.getText();
    }

    if (username != "" && password != "") {
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
    usernameTextField.setStyle("-fx-border-color: #FE8383");
    passwordTextField.setStyle("-fx-border-color: #FE8383");
    passwordPasswordField.setStyle("-fx-border-color: #FE8383");
    super.rotateNode(visualFeedbackText, false);
    userSession = null;
  }

  @FXML
  private void onRegisterButtonClick(ActionEvent event) throws IOException {
    super.switchScene(event, "register.fxml");
  }
}
