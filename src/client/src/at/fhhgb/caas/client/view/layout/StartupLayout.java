package at.fhhgb.caas.client.view.layout;

import at.fhhgb.caas.client.controller.DefaultController;
import javafx.scene.layout.Pane;


/**
 * Created by Dinu Marius-Constantin on 10.05.2015.
 */
public abstract class StartupLayout<T extends DefaultController> extends BasicLayout<T> {

    public static final String CAAS_SPLASH_IMAGE = "/resources/img/caas_splash_image.png";

    public StartupLayout(T controller) {
        super(controller);
    }

    @Override
    protected Pane createHeaderPane() {
        return newEmptyPane();
    }

    @Override
    protected Pane createFooterPane() {
        return newEmptyPane();
    }

    @Override
    protected Pane createSidebarPane() {
        return newEmptyPane();
    }

}
