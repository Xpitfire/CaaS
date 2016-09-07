package at.fhhgb.caas.client.view;

import at.fhhgb.caas.client.controller.PortalController;
import at.fhhgb.caas.client.resources.lang.LanguageConsts;
import at.fhhgb.caas.client.view.layout.DefaultPortalLayout;
import javafx.scene.layout.Pane;

/**
 * Created by Dinu Marius-Constantin on 11.05.2015.
 */
public class PortalView extends DefaultPortalLayout {

    private Pane returnPane;

    public PortalView(PortalController controller) {
        super(controller);
        controller.registerView(this);
    }

    public void setHeaderTitleInfo(String titleInfo) {
        getHeaderTitleInfoLabel().setText(titleInfo);
    }

    @Override
    protected String getHeaderTitleInfo() {
        return lang.getString(LanguageConsts.PORTAL_APP_TITLE);
    }

    @Override
    protected Pane createHeaderTitleNavigationRegion() {
        returnPane = new Pane();
        return returnPane;
    }

    @Override
    protected Pane createContentPane() {
        return controller.createPortalTileView();
    }

}
