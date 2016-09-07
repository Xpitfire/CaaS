package at.fhhgb.caas.client.view.template;

import at.fhhgb.caas.client.controller.PortalController;
import at.fhhgb.caas.client.resources.lang.LanguageConsts;
import at.fhhgb.caas.client.resources.css.CSSConsts;
import at.fhhgb.caas.client.view.layout.UIObject;
import at.fhhgb.caas.data.Category;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * Created by Dinu Marius-Constantin on 13.05.2015.
 */
public class CategorySelectionTabTemplate extends UIObject<PortalController> {
    public CategorySelectionTabTemplate(PortalController controller) {
        super(controller);
        initComponents();
    }

    private void initComponents() {
        HBox selectionPane = new HBox();
        Button allButton = new Button(lang.getString(LanguageConsts.CATEGORY_FILTER_ALL));
        allButton.getStyleClass().add(CSSConsts.CLASS_SELECTION);
        allButton.addEventHandler(ActionEvent.ACTION, event -> controller.showMenuWithFilter(null));
        selectionPane.getChildren().add(allButton);

        for (Category c : controller.getCategories()) {
            Button b = new Button(c.getName());
            b.getStyleClass().add(CSSConsts.CLASS_SELECTION);
            b.addEventHandler(ActionEvent.ACTION, event -> controller.showMenuWithFilter(c));
            selectionPane.getChildren().add(b);
        }
        getChildren().add(selectionPane);
    }
}
