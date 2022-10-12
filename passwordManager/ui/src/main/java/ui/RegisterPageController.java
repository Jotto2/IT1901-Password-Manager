package ui;

import java.io.IOException;

import core.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;

public class RegisterPageController extends PasswordManagerController {

    @FXML
    private TextField usernameTextField, passwordTextField, repeatPasswordTextField;

    @FXML
    private Text visualFeedbackText;

    @FXML
    private PasswordField passwordPasswordField, repeatPasswordPasswordField;

    @FXML
    private ImageView eyeImageView1, eyeImageView2;

    // ----- //

    @FXML
    public void initialize() {
        eyeImageView1.setImage(super.eyeOpenImage);
        eyeImageView2.setImage(super.eyeOpenImage);
    }

    @FXML
    private void eyeImageViewClick1() {
        passwordEye(passwordTextField, passwordPasswordField, eyeImageView1);
    }

    @FXML
    private void eyeImageViewClick2() {
        passwordEye(repeatPasswordTextField, repeatPasswordPasswordField, eyeImageView2);
    }

    @FXML
    private void onRegisterBackButtonClick(ActionEvent event) throws IOException {
        switchScene(event, "login.fxml");
    }

    /**
     * onCreateUserButtonClick sends the fields in the register page form to the
     * usersession for validation, if they are validated, a new user is stored and
     * the user is sent back to the loginpage
     * 
     * @param event ActionEvent object used in the switchScene method
     * @throws IOException if the fxml file cant be opened
     */
    @FXML
    private void onCreateUserButtonClick(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        if (!passwordTextField.isVisible()) {
            password = passwordPasswordField.getText();
        }
        String passwordRepeat = repeatPasswordTextField.getText();
        if (!repeatPasswordTextField.isVisible()) {
            passwordRepeat = repeatPasswordPasswordField.getText();
        }

        // if all fields are filled
        if (username != "" && password != "" && passwordRepeat != "") {

            UserSession userSession = UserSession.getInstance();
            String validationResult = userSession.userValidator(username, password, passwordRepeat);

            if (validationResult.equals("OK")) {
                switchScene(event, "login.fxml");
            } else {
                visualFeedbackText.setText(validationResult);
            }
        } else {
            visualFeedbackText.setText("Please fill in all fields");
        }

        visualFeedbackText.setVisible(true);
        setBorderRed(usernameTextField);
        setBorderRed(passwordTextField);
        setBorderRed(repeatPasswordTextField);
        setBorderRed(passwordPasswordField);
        setBorderRed(repeatPasswordPasswordField);
        rotateNode(visualFeedbackText, false);
    }

}
