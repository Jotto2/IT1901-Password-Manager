package ui;

import java.io.IOException;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * the PasswordManagerController is the super class of all our other
 * controllers, it contains methods useful for all the controllers.
 */
public class PasswordManagerController {

  protected Stage stage;
  protected Scene scene;
  protected Parent root;

  protected Image eyeOpenImage = new Image("file:src/main/resources/temporaryImageFolder/eye-open.png");
  protected Image eyeClosedImage = new Image("file:src/main/resources/temporaryImageFolder/eye-closed.png");

  /**
   * switchScene switches the vies to a different page, for example when going to
   * the registerpage, or when logging out.

   * @param event     the event is used to get the window object
   * @param sceneName the path of the fxml file to be switched to
   * @throws IOException if the fxml file cant be opened
   */
  protected void switchScene(ActionEvent event, String sceneName) throws IOException {
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource(sceneName));
    scene = new Scene(root);
    stage.setScene(scene);
    String title = sceneName.substring(0, sceneName.length() - 5);
    title = title.substring(0, 1).toUpperCase() + title.substring(1);
    stage.setTitle("Password Manager | " + title);
    stage.show();
  }

  /**
   * passwordEye shows or hides the password in the passwordTextField and
   * passwordPasswordField.

   * @param textfield     the textfield that is used to show the password
   * @param passwordfield the passwordfield that is used to hide the password
   * @param imageview     the imageview that is used to show the eye
   */
  protected void passwordEye(TextField textField, PasswordField passwordField, ImageView imageView) {
    if (textField.isVisible()) {
      passwordField.setText(textField.getText());
      textField.setVisible(false);
      passwordField.setVisible(true);

      imageView.setImage(eyeOpenImage);
    } else {
      textField.setText(passwordField.getText());
      textField.setVisible(true);
      passwordField.setVisible(false);

      imageView.setImage(eyeClosedImage);
    }
  }

  /**
   * rotateNode animates a node by rotatating it.

   * @param element   the element that is to be rotated
   * @param clockwise whether the element should be rotated clockwise or not
   */
  protected void rotateNode(Node element, boolean clockwise) {
    element.setRotate(0);
    RotateTransition rotateTransition = new RotateTransition();
    rotateTransition.setDuration(Duration.millis(100));
    if (clockwise) {
      rotateTransition.setByAngle(3);
    } else {
      rotateTransition.setByAngle(-3);
    }
    rotateTransition.setCycleCount(4);
    rotateTransition.setAutoReverse(true);
    rotateTransition.setNode(element);
    rotateTransition.play();
  }

  /**
   * setBorderRed sets the border of a node to red.

   * @param element the element that is to be set to red
   */
  protected void setBorderRed(Node element) {
    element.setStyle("-fx-border-color: #FE8383");
  }

  protected void updateUserData() {

  }
}