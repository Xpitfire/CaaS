package at.fhhgb.caas.client.view.template;

import at.fhhgb.caas.client.controller.PortalController;
import at.fhhgb.caas.client.resources.lang.LanguageConsts;
import at.fhhgb.caas.client.resources.css.CSSConsts;
import at.fhhgb.caas.client.view.layout.UIObject;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by Dinu Marius-Constantin on 15.05.2015.
 */
public class AddCategoryTemplate extends UIObject<PortalController> {

    private TextField categoryName;

    private Label addCategoryInfoLabel;

    public AddCategoryTemplate(PortalController controller) {
        super(controller);
        initComponents();
    }

    private void initComponents() {
        VBox rootBox = new VBox();

        HBox line = new HBox();
        Label categoryLabel = new Label(lang.getString(LanguageConsts.CATEGORY_LABEL));
        categoryLabel.getStyleClass().add(CSSConsts.CLASS_LABLE);
        line.getChildren().add(categoryLabel);
        categoryName = new TextField();
        categoryName.getStyleClass().add(CSSConsts.CLASS_INPUT_FIELD);
        line.getChildren().add(categoryName);
        rootBox.getChildren().add(line);

        addCategoryInfoLabel = new Label();
        addCategoryInfoLabel.getStyleClass().add(CSSConsts.CLASS_ALERT_INFO);
        rootBox.getChildren().add(addCategoryInfoLabel);

        Button confirmButton = new Button(lang.getString(LanguageConsts.CONFIRM_BUTTON));
        confirmButton.getStyleClass().add(CSSConsts.CLASS_BUTTON_CONFIRM);
        confirmButton.addEventHandler(ActionEvent.ACTION, event -> controller.onClickAddCategoryFormConfirm());
        rootBox.getChildren().add(confirmButton);

        getChildren().add(rootBox);
    }

    public String getCategoryName() {
        return categoryName.getText();
    }

    public void alertInputResponse(String message) {
        addCategoryInfoLabel.setText(message);
    }

}
