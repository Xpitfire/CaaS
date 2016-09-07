package at.fhhgb.caas.server.controller;

import at.fhhgb.caas.server.view.OperationView;
import javafx.stage.Stage;

/**
 * Created by Dinu Marius-Constantin on 17.05.2015.
 */
public class OperationController {

    private Stage primaryStage;
    private OperationView view;

    public OperationController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public final void registerView(OperationView view) {
        this.view = view;
    }
}
