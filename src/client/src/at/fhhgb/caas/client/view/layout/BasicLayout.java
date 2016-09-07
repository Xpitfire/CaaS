package at.fhhgb.caas.client.view.layout;

import at.fhhgb.caas.client.controller.DefaultController;
import at.fhhgb.caas.client.resources.css.CSSConsts;
import javafx.scene.layout.*;

/**
 * Created by Dinu Marius-Constantin on 09.05.2015.
 */
public abstract class BasicLayout<T extends DefaultController> extends UIObject<T> {

    private Pane headerPane;
    private Pane contentPane;
    private Pane footerPane;
    private Pane sidebarPane;

    public BasicLayout(T controller) {
        super(controller);
        initComponents();
    }

    private void initComponents() {
        HBox rootBox = new HBox();
        HBox.setHgrow(this, Priority.ALWAYS);
        rootBox.getStyleClass().add(CSSConsts.CLASS_BASE_LAYOUT);

        VBox contentBox = new VBox();
        VBox.setVgrow(rootBox, Priority.ALWAYS);
        contentBox.getStyleClass().add(CSSConsts.CLASS_CONTENT_LAYOUT);

        headerPane = new Pane();
        headerPane.setPrefHeight(PREF_HEADER_HEIGTH);
        headerPane.getStyleClass().add(CSSConsts.CLASS_CAAS_HEADER);
        headerPane.getChildren().add(createHeaderPane());
        contentBox.getChildren().add(headerPane);

        contentPane = new Pane();
        contentPane.setPrefWidth(PREF_PANE_WIDTH);
        contentPane.getChildren().add(createContentPane());
        contentBox.getChildren().add(contentPane);

        footerPane = new Pane();
        footerPane.getChildren().add(createFooterPane());
        contentBox.getChildren().add(footerPane);

        rootBox.getChildren().add(contentBox);

        sidebarPane = new Pane();
        sidebarPane.getChildren().add(createSidebarPane());
        rootBox.getChildren().add(sidebarPane);

        getChildren().add(rootBox);
    }

    protected final Pane newEmptyPane() {
        return new Pane();
    }

    protected abstract Pane createHeaderPane();

    public final Pane getHeaderPane() {
        return headerPane;
    }

    protected abstract Pane createContentPane();

    public final Pane getContentPane() {
        return contentPane;
    }

    protected abstract Pane createFooterPane();

    public final Pane getFooterPane() {
        return footerPane;
    }

    protected abstract Pane createSidebarPane();

    public final Pane getSidebarPane() {
        return sidebarPane;
    }

}
