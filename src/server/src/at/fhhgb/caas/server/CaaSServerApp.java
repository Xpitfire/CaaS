package at.fhhgb.caas.server;

import at.fhhgb.caas.server.controller.OperationController;
import at.fhhgb.caas.server.model.DAOFactory;
import at.fhhgb.caas.server.socket.ClientListener;
import at.fhhgb.caas.server.view.OperationView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Dinu Marius-Constantin on 17.05.2015.
 */
public class CaaSServerApp extends Application {

    public static final String SERVER_LOGGER = "server-logger";

    @Override
    public void start(Stage primaryStage) throws Exception {
        OperationController controller = new OperationController(primaryStage);
        primaryStage.setTitle("CaaS Server");
        primaryStage.setMinHeight(70);
        primaryStage.setMinWidth(220);
        primaryStage.setOnCloseRequest(event -> {
            ClientListener.getInstance().close();
            System.exit(0);
        });
        primaryStage.setScene(new Scene(new OperationView(controller)));
        primaryStage.show();
    }

    public static void main(String[] args) {
        DAOFactory.createInstance();
        ClientListener clientHandler = ClientListener.getInstance();
        new Thread(clientHandler).start();
        launch(args);
    }

}
