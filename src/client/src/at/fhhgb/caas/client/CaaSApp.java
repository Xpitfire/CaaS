package at.fhhgb.caas.client;

import at.fhhgb.caas.client.controller.LoginController;
import at.fhhgb.caas.client.model.CommandExecutor;
import at.fhhgb.caas.client.model.DataModelUpdate;
import at.fhhgb.caas.client.socket.ServerHandler;
import at.fhhgb.caas.client.view.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Dinu Marius-Constantin on 09.05.2015.
 */
public class CaaSApp extends Application {

    public static final String APP_NAME = "CaaS App";

    public static final String CLIENT_LOGGER = "client-logger";

    public static final double DEFAULT_APP_WIDTH = 1200;
    public static final double DEFAULT_APP_HEIGHT = 700;
    public static final double MIN_APP_WIDTH = 400;
    public static final double MIN_APP_HEIGHT = 200;

    private Scene scene;

    public static void main(String[] args) {
        CaaSApp.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ServerHandler.getInstance();
        LoginController controller = new LoginController(primaryStage);
        scene = new Scene(new LoginView(controller));
        primaryStage.setScene(scene);
        primaryStage.setWidth(DEFAULT_APP_WIDTH);
        primaryStage.setHeight(DEFAULT_APP_HEIGHT);
        primaryStage.setMinWidth(MIN_APP_WIDTH);
        primaryStage.setMinHeight(MIN_APP_HEIGHT);
        primaryStage.setTitle(APP_NAME);
        primaryStage.setOnCloseRequest(event -> {
            ServerHandler.getInstance().close();
            CommandExecutor.close();
            DataModelUpdate.getInstance().close();
            System.exit(0);
        });
        primaryStage.show();
    }

}
