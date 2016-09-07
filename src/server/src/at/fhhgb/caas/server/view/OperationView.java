package at.fhhgb.caas.server.view;

import at.fhhgb.caas.server.controller.OperationController;
import at.fhhgb.caas.server.model.DAOFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Created by Dinu Marius-Constantin on 17.05.2015.
 */
public class OperationView extends Pane {

    private OperationController controller;

    public OperationView(OperationController controller) {
        this.controller = controller;
        controller.registerView(this);
        initComponents();
    }

    private void initComponents() {
        HBox rootBox = new HBox();

        Button saveButton = new Button("Save");
        saveButton.addEventHandler(ActionEvent.ACTION, event -> new Thread(() -> DAOFactory.save()).start());
        Button openButton = new Button("Load");
        openButton.addEventHandler(ActionEvent.ACTION, event -> new Thread(() -> DAOFactory.load()).start());

        rootBox.getChildren().addAll(saveButton, openButton);

        getChildren().add(rootBox);
    }


}
