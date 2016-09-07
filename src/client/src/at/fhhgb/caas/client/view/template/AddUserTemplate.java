package at.fhhgb.caas.client.view.template;

import at.fhhgb.caas.client.controller.PortalController;
import at.fhhgb.caas.crypto.DataCrypto;
import at.fhhgb.caas.client.resources.lang.LanguageConsts;
import at.fhhgb.caas.client.resources.css.CSSConsts;
import at.fhhgb.caas.client.view.layout.UIObject;
import at.fhhgb.caas.data.AuthType;
import at.fhhgb.caas.data.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by Dinu Marius-Constantin on 12.05.2015.
 */
public class AddUserTemplate extends UIObject<PortalController> {

    private ObservableList<String> options = FXCollections.observableArrayList(
            AuthType.ADMIN.toString(),
            AuthType.STAFF.toString(),
            AuthType.USER.toString()
    );

    private TextField usernameTextField;
    private TextField idTextField;
    private TextField firstnameTextField;
    private TextField lastnameTextField;
    private PasswordField passwordTextField;
    private TextField emailTextField;
    private ComboBox authBox;

    private Label addUserInfoLabel;

    public AddUserTemplate(PortalController controller, Person data) {
        super(controller);
        initComponents(data);
    }

    private void initComponents(Person data) {
        VBox rootBox = new VBox();

        HBox line1 = new HBox();
        Label usernameLabel = new Label(lang.getString(LanguageConsts.ADD_USER_USERNAME_LABEL));
        usernameLabel.getStyleClass().add(CSSConsts.CLASS_LABLE);
        line1.getChildren().add(usernameLabel);
        usernameTextField = new TextField();
        usernameTextField.getStyleClass().add(CSSConsts.CLASS_INPUT_FIELD);
        if (data != null)
            usernameTextField.setText(data.getUsername());
        line1.getChildren().add(usernameTextField);
        rootBox.getChildren().add(line1);

        HBox line2 = new HBox();
        Label studentIdLabel = new Label(lang.getString(LanguageConsts.ADD_USER_ID_LABEL));
        studentIdLabel.getStyleClass().add(CSSConsts.CLASS_LABLE);
        line2.getChildren().add(studentIdLabel);
        idTextField = new TextField();
        idTextField.getStyleClass().add(CSSConsts.CLASS_INPUT_FIELD);
        if (data != null)
            idTextField.setText(data.getId());
        line2.getChildren().add(idTextField);
        rootBox.getChildren().add(line2);

        HBox line3 = new HBox();
        Label firstnameLabel = new Label(lang.getString(LanguageConsts.ADD_USER_FIRSTNAME_LABEL));
        firstnameLabel.getStyleClass().add(CSSConsts.CLASS_LABLE);
        line3.getChildren().add(firstnameLabel);
        firstnameTextField = new TextField();
        firstnameTextField.getStyleClass().add(CSSConsts.CLASS_INPUT_FIELD);
        if (data != null)
            firstnameTextField.setText(data.getFirstName());
        line3.getChildren().add(firstnameTextField);
        rootBox.getChildren().add(line3);

        HBox line4 = new HBox();
        Label lastnameLabel = new Label(lang.getString(LanguageConsts.ADD_USER_LASTNAME_LABEL));
        lastnameLabel.getStyleClass().add(CSSConsts.CLASS_LABLE);
        line4.getChildren().add(lastnameLabel);
        lastnameTextField = new TextField();
        lastnameTextField.getStyleClass().add(CSSConsts.CLASS_INPUT_FIELD);
        if (data != null)
            lastnameTextField.setText(data.getLastName());
        line4.getChildren().add(lastnameTextField);
        rootBox.getChildren().add(line4);

        HBox line5 = new HBox();
        Label passwordLabel = new Label(lang.getString(LanguageConsts.ADD_USER_PASSWORD_LABEL));
        passwordLabel.getStyleClass().add(CSSConsts.CLASS_LABLE);
        line5.getChildren().add(passwordLabel);
        passwordTextField = new PasswordField();
        passwordTextField.getStyleClass().add(CSSConsts.CLASS_INPUT_FIELD);
        line5.getChildren().add(passwordTextField);
        rootBox.getChildren().add(line5);

        HBox line6 = new HBox();
        Label emailLabel = new Label(lang.getString(LanguageConsts.ADD_USER_EMAIL_LABEL));
        emailLabel.getStyleClass().add(CSSConsts.CLASS_LABLE);
        line6.getChildren().add(emailLabel);
        emailTextField = new TextField();
        emailTextField.getStyleClass().add(CSSConsts.CLASS_INPUT_FIELD);
        if (data != null)
            emailTextField.setText(data.getEmail());
        line6.getChildren().add(emailTextField);
        rootBox.getChildren().add(line6);

        authBox = new ComboBox(options);
        if (data != null)
            authBox.getSelectionModel().select(data.getAuthType().toString());
        else
            authBox.getSelectionModel().select(0);
        rootBox.getChildren().add(authBox);

        addUserInfoLabel = new Label();
        addUserInfoLabel.getStyleClass().add(CSSConsts.CLASS_ALERT_INFO);
        rootBox.getChildren().add(addUserInfoLabel);

        Button confirmButton = new Button(lang.getString(LanguageConsts.CONFIRM_BUTTON));
        confirmButton.getStyleClass().add(CSSConsts.CLASS_BUTTON_CONFIRM);
        confirmButton.addEventHandler(ActionEvent.ACTION, event -> controller.onClickHandleUserFormConfirm((data != null)));
        rootBox.getChildren().add(confirmButton);

        getChildren().add(rootBox);
    }

    public void alertInputResponse(String infoMsg) {
        addUserInfoLabel.setText(infoMsg);
    }

    public String getUsername() {
        return usernameTextField.getText();
    }

    public String getStudentID() {
        return idTextField.getText();
    }

    public String getFirstname() {
        return firstnameTextField.getText();
    }

    public String getLastname() {
        return lastnameTextField.getText();
    }

    public CharSequence getPassword() {
        return DataCrypto.encrypt(passwordTextField.getCharacters().toString());
    }

    public String getEmail() {
        return emailTextField.getText();
    }

    public AuthType getAuthenticationType() {
        return AuthType.getType((String) authBox.getSelectionModel().getSelectedItem());
    }

}
