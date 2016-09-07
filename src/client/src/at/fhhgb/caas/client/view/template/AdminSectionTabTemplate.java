package at.fhhgb.caas.client.view.template;

import at.fhhgb.caas.client.controller.PortalController;
import at.fhhgb.caas.client.resources.lang.LanguageConsts;
import at.fhhgb.caas.client.resources.css.CSSConsts;
import at.fhhgb.caas.client.view.layout.UIObject;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * Created by Dinu Marius-Constantin on 14.05.2015.
 */
public class AdminSectionTabTemplate extends UIObject<PortalController> {
    public AdminSectionTabTemplate(PortalController controller) {
        super(controller);
        initComponents();
    }

    private void initComponents() {
        HBox selectionPane = new HBox();
        Button categoryButton = new Button(lang.getString(LanguageConsts.ADMIN_SECTION_BUTTON_CATEGORY));
        categoryButton.getStyleClass().add(CSSConsts.CLASS_SELECTION);
        categoryButton.addEventHandler(ActionEvent.ACTION, event -> controller.onClickAdminSectionCategories());
        Button menuButton = new Button(lang.getString(LanguageConsts.ADMIN_SECTION_BUTTON_MENU));
        menuButton.getStyleClass().add(CSSConsts.CLASS_SELECTION);
        menuButton.addEventHandler(ActionEvent.ACTION, event -> controller.onClickAdminSectionMenu());
        Button usersButton = new Button(lang.getString(LanguageConsts.ADMIN_SECTION_BUTTON_USERS));
        usersButton.getStyleClass().add(CSSConsts.CLASS_SELECTION);
        usersButton.addEventHandler(ActionEvent.ACTION, event -> controller.onClickAdminSectionUsers());

        selectionPane.getChildren().addAll(categoryButton, menuButton, usersButton);
        getChildren().add(selectionPane);
    }
}
