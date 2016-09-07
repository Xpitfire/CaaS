package at.fhhgb.caas.client.controller;

import at.fhhgb.caas.client.CaaSApp;
import at.fhhgb.caas.crypto.DataCrypto;
import at.fhhgb.caas.client.model.DAOFactory;
import at.fhhgb.caas.client.resources.lang.LanguageConsts;
import at.fhhgb.caas.client.view.LoginView;
import at.fhhgb.caas.client.view.PortalView;
import at.fhhgb.caas.data.AuthType;
import at.fhhgb.caas.data.UserStatus;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Dinu Marius-Constantin on 10.05.2015.
 */
public class LoginController extends DefaultController<LoginView> {

    private static final Logger logger = Logger.getLogger(CaaSApp.CLIENT_LOGGER);

    public LoginController(Stage primaryStage) {
        super(primaryStage);
    }

    public void onClickLogin() {
        String username = view.getUsername();
        CharSequence password = DataCrypto.encrypt(view.getPassword().toString());
        if (username == null || username.length() <= 0
                || password == null || password.length() <= 0) {
            view.alertInputResponse(lang.getString(LanguageConsts.FILL_IN_MANDATORY_FIELDS));
        } else {
            try {
                AuthType authType = DAOFactory.getPersonDAO().authenticateUserInput(username, password);

                if (authType == null || DAOFactory.getPersonDAO().getPerson(username).getUserStatus() == UserStatus.BLOCKED) {
                    view.alertInputResponse(lang.getString(LanguageConsts.LOGIN_FAILED));
                } else {
                    PortalController portalController = new PortalController(primaryStage, view.getUsername());
                    primaryStage.setScene(new Scene(new PortalView(portalController)));
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Could not authenticate user!", e);
            }
        }
    }

}
