package at.fhhgb.caas.client.view.template;

import at.fhhgb.caas.client.controller.PortalController;
import at.fhhgb.caas.client.resources.lang.LanguageConsts;
import at.fhhgb.caas.client.view.layout.UIObject;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

/**
 * Created by Dinu Marius-Constantin on 12.05.2015.
 */
public class PortalTilesTemplate extends UIObject<PortalController> {

    public static final String PORTAL_TILE_BOX_ID = "portal-tile-box";
    public static final String PORTAL_TILE_IMG_ID = "portal-tile-img-description";
    public static final String PROTAL_TILE_IMG_LABEL_ID = "portal-tile-img-label";

    public static final String PORTAL_TILE_IMAGE_PLACEHOLDER = "/resources/img/portal-preview-tile.png";

    private TilePane tilePane;

    public PortalTilesTemplate(PortalController controller) {
        super(controller);
        initComponents();
    }

    private void initComponents() {
        tilePane = new TilePane();
        tilePane.setHgap(2);
        tilePane.setPrefColumns(2);
        getChildren().add(tilePane);
    }

    public TilePane getTilePane() {
        return tilePane;
    }

    public Tile getMenuTile() {
        Tile seeMenuTile = new Tile(
                new ImageView(new Image(PORTAL_TILE_IMAGE_PLACEHOLDER)),
                lang.getString(LanguageConsts.PORTAL_TILE_DESC_SEE_MENU));
        seeMenuTile.setOnMouseClicked(event1 -> controller.onClickSeeMenu());
        return seeMenuTile;
    }

    public Tile getCostumerOrdersTile() {
        Tile ordersTile = new Tile(
                new ImageView(new Image(PORTAL_TILE_IMAGE_PLACEHOLDER)),
                lang.getString(LanguageConsts.PORTAL_TILE_DESC_ORDERS));
        ordersTile.setOnMouseClicked(event -> controller.onClickCustomerOrders());
        return ordersTile;
    }

    public Tile getAddUserTile() {
        Tile addUserTile = new Tile(
                new ImageView(new Image(PORTAL_TILE_IMAGE_PLACEHOLDER)),
                lang.getString(LanguageConsts.PORTAL_TILE_DESC_ADD_USER));
        addUserTile.setOnMouseClicked(event -> controller.onClickAddUser(null));
        return addUserTile;
    }

    public Tile getAdminSectionTile() {
        Tile adminSectionTile = new Tile(
                new ImageView(new Image(PORTAL_TILE_IMAGE_PLACEHOLDER)),
                lang.getString(LanguageConsts.PORTAL_TILE_DESC_ADMIN_SECTION));
        adminSectionTile.setOnMouseClicked(event -> {
            controller.onClickAdminSection();
            controller.onClickAdminSectionCategories();
        });
        return adminSectionTile;
    }


    public static class Tile extends VBox {
        private Tile(ImageView image, String description) {
            initComponents(image, description);
        }

        private void initComponents(ImageView image, String description) {
            setId(PORTAL_TILE_BOX_ID);
            image.setId(PORTAL_TILE_IMG_ID);
            getChildren().add(image);
            Label imageDescription = new Label(description);
            imageDescription.setId(PROTAL_TILE_IMG_LABEL_ID);
            getChildren().add(imageDescription);
        }
    }
}
