package at.fhhgb.caas.client.controller;

import at.fhhgb.caas.client.resources.lang.LanguageConsts;
import at.fhhgb.caas.client.resources.lang.LanguageFactory;
import at.fhhgb.caas.client.view.LoginView;
import at.fhhgb.caas.client.view.layout.UIObject;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;

/**
 * Created by Dinu Marius-Constantin on 11.05.2015.
 */
public abstract class DefaultController<T extends UIObject> {

    protected Stage primaryStage;
    protected ResourceBundle lang;
    protected T view;

    public DefaultController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        lang = LanguageFactory.getBundle(LanguageConsts.LANGUAGE_RESOURCE_BUNDLE);
    }

    public final void registerView(T view) {
        this.view = view;
    }

    public void onClickLogout() {
        LoginController loginController = new LoginController(primaryStage);
        primaryStage.setScene(new Scene(new LoginView(loginController)));
    }

}
