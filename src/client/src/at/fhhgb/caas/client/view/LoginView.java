package at.fhhgb.caas.client.view;

import at.fhhgb.caas.client.controller.LoginController;
import at.fhhgb.caas.client.resources.lang.LanguageConsts;
import at.fhhgb.caas.client.resources.css.CSSConsts;
import at.fhhgb.caas.client.view.layout.StartupLayout;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


/**
 * Created by Dinu Marius-Constantin on 10.05.2015.
 */
public class LoginView extends StartupLayout<LoginController> {

    private TextField usernameField;
    private PasswordField passwordField;
    private Label loginInfoLabel;


    public LoginView(LoginController controller) {
        super(controller);
        controller.registerView(this);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public CharSequence getPassword() {
        return passwordField.getCharacters();
    }

    public void alertInputResponse(String infoMsg) {
        loginInfoLabel.setText(infoMsg);
    }

    @Override
    protected Pane createContentPane() {
        StackPane stackPane = new StackPane();
        stackPane.getStyleClass().add(CSSConsts.CLASS_LOGIN_PANE);

        VBox loginBox = new VBox();

        ImageView headerImg = new ImageView(new Image(CAAS_SPLASH_IMAGE));
        headerImg.setId(CSSConsts.CAAS_SPLASH_IMAGE);
        loginBox.getChildren().add(headerImg);

        usernameField = new TextField();
        usernameField.getStyleClass().add(CSSConsts.CLASS_INPUT_FIELD);
        usernameField.setPromptText(lang.getString(LanguageConsts.LOGIN_USERNAME_PLACEHOLDER));
        loginBox.getChildren().add(usernameField);

        passwordField = new PasswordField();
        passwordField.getStyleClass().add(CSSConsts.CLASS_INPUT_FIELD);
        passwordField.setPromptText(lang.getString(LanguageConsts.LOGIN_PASSWORD_PLACEHOLDER));
        loginBox.getChildren().add(passwordField);

        loginInfoLabel = new Label();
        loginInfoLabel.getStyleClass().add(CSSConsts.CLASS_ALERT_INFO);
        loginBox.getChildren().add(loginInfoLabel);

        Button loginButton = new Button(lang.getString(LanguageConsts.LOGIN_BUTTON));
        loginButton.getStyleClass().add(CSSConsts.CLASS_BUTTON);
        loginButton.getStyleClass().add(CSSConsts.CLASS_BUTTON_CONFIRM);
        loginButton.addEventHandler(ActionEvent.ACTION, event -> controller.onClickLogin());
        loginBox.getChildren().add(loginButton);

        Separator separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);
        loginBox.getChildren().add(separator);

        stackPane.getChildren().add(loginBox);

        return stackPane;
    }

}
