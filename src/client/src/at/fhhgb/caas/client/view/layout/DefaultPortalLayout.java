package at.fhhgb.caas.client.view.layout;

import at.fhhgb.caas.client.controller.PortalController;
import at.fhhgb.caas.client.resources.css.CSSConsts;
import at.fhhgb.caas.client.view.template.UserInfoBoxTemplate;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * Created by Dinu Marius-Constantin on 11.05.2015.
 */
public abstract class DefaultPortalLayout extends PortalLayout<PortalController> {

    private Pane selectionPane;
    private Label titleLabel;

    public DefaultPortalLayout(PortalController controller) {
        super(controller);
    }

    @Override
    protected Pane createSidebarPane() {
        return new UserInfoBoxTemplate(controller);
    }

    @Override
    protected Pane createFooterPane() {
        return newEmptyPane();
    }

    @Override
    protected Pane createHeaderTitleInfoPane() {
        titleLabel = new Label(getHeaderTitleInfo());
        titleLabel.setPrefWidth(900);
        titleLabel.getStyleClass().add(CSSConsts.CLASS_TITLE);
        Pane labelPane = new Pane();
        labelPane.getChildren().add(titleLabel);
        return labelPane;
    }

    protected abstract String getHeaderTitleInfo();

    protected final Label getHeaderTitleInfoLabel() {
        return titleLabel;
    }

    @Override
    protected Pane createHeaderSelectionPane() {
        selectionPane = new Pane();
        return selectionPane;
    }

    public Pane getSelectionPane() {
        return selectionPane;
    }

}
