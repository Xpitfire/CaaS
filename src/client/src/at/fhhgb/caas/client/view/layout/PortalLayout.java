package at.fhhgb.caas.client.view.layout;

import at.fhhgb.caas.client.controller.DefaultController;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Created by Dinu Marius-Constantin on 11.05.2015.
 */
public abstract class PortalLayout<T extends DefaultController> extends BasicLayout<T> {

    private Pane returnPane;

    public PortalLayout(T controller) {
        super(controller);
    }

    protected Pane createHeaderPane() {
        VBox headerBox = new VBox();

        HBox titleHeaderBox = new HBox();
        returnPane = createHeaderTitleNavigationRegion();
        returnPane.setPrefWidth(PREF_RETURN_WIDTH);
        titleHeaderBox.getChildren().add(returnPane);
        titleHeaderBox.getChildren().add(createHeaderTitleInfoPane());
        headerBox.getChildren().add(titleHeaderBox);

        headerBox.getChildren().add(createHeaderSelectionPane());

        return headerBox;
    }

    public Pane getHeaderTitleNavigationRegion() {
        return returnPane;
    }

    protected abstract Pane createHeaderTitleNavigationRegion();

    protected abstract Pane createHeaderTitleInfoPane();

    protected abstract Pane createHeaderSelectionPane();



}
