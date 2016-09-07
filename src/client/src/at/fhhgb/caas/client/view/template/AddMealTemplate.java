package at.fhhgb.caas.client.view.template;

import at.fhhgb.caas.client.controller.PortalController;
import at.fhhgb.caas.client.resources.lang.LanguageConsts;
import at.fhhgb.caas.client.resources.css.CSSConsts;
import at.fhhgb.caas.client.view.layout.UIObject;
import at.fhhgb.caas.data.Category;
import at.fhhgb.caas.data.WeekDay;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Dinu Marius-Constantin on 16.05.2015.
 */
public class AddMealTemplate extends UIObject<PortalController> {

    private Set<Category> categories = new TreeSet<>();
    private Set<WeekDay> availableDaySet = new TreeSet<>();

    private TextField nameText;
    private TextField priceText;

    private Label addMealInfoLabel;

    public AddMealTemplate(PortalController controller) {
        super(controller);
        initComponents();
    }

    private void initComponents() {
        VBox rootBox = new VBox();

        HBox nameBox = new HBox();
        Label nameLabel = new Label(lang.getString(LanguageConsts.NEW_MEAL_NAME));
        nameLabel.getStyleClass().add(CSSConsts.CLASS_LABLE);
        nameText = new TextField();
        nameText.getStyleClass().add(CSSConsts.CLASS_INPUT_FIELD);
        nameBox.getChildren().addAll(nameLabel, nameText);
        rootBox.getChildren().add(nameBox);

        HBox priceBox = new HBox();
        Label priceLabel = new Label(lang.getString(LanguageConsts.NEW_MEAL_PRICE));
        priceLabel.getStyleClass().add(CSSConsts.CLASS_LABLE);
        priceText = new TextField();
        priceText.setText("0.0");
        priceText.getStyleClass().add(CSSConsts.CLASS_INPUT_FIELD);
        // allow numbers only
        priceText.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Double.parseDouble(newValue);
            } catch (NumberFormatException e) {
                priceText.setText(oldValue);
            }
        });
        priceBox.getChildren().addAll(priceLabel, priceText);
        rootBox.getChildren().add(priceBox);

        int listViewHeight = 120;

        Label matchCategoryLabel = new Label(lang.getString(LanguageConsts.NEW_MEAL_MATCH_CATEGORY));
        ListView<Category> categoryListView = new ListView<>(controller.getCategories());
        categoryListView.setMaxHeight(listViewHeight);
        categoryListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        categoryListView.setOnMouseClicked(event -> {
            getCategories().clear();
            ObservableList<Category> selectedItems = categoryListView.getSelectionModel().getSelectedItems();
            selectedItems.stream().forEach(category -> getCategories().add(category));
        });
        rootBox.getChildren().addAll(matchCategoryLabel, categoryListView);

        Label availableDayLabel = new Label(lang.getString(LanguageConsts.NEW_MEAL_AVAILABLE_DAYS));
        ListView<WeekDay> weekDayListView = new ListView<>(controller.getAvailableDays());
        weekDayListView.setMaxHeight(listViewHeight);
        weekDayListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        weekDayListView.setOnMouseClicked(event -> {
            getAvailableDaySet().clear();
            ObservableList<WeekDay> selectedItems = weekDayListView.getSelectionModel().getSelectedItems();
            selectedItems.stream().forEach(weekDay -> getAvailableDaySet().add(weekDay));
        });
        rootBox.getChildren().addAll(availableDayLabel, weekDayListView);

        addMealInfoLabel = new Label();
        addMealInfoLabel.getStyleClass().add(CSSConsts.CLASS_ALERT_INFO);
        rootBox.getChildren().add(addMealInfoLabel);

        Button confirmButton = new Button(lang.getString(LanguageConsts.CONFIRM_BUTTON));
        confirmButton.getStyleClass().add(CSSConsts.CLASS_BUTTON_CONFIRM);
        confirmButton.addEventHandler(ActionEvent.ACTION, event -> controller.onClickAddMealFormConfirm());
        rootBox.getChildren().add(confirmButton);

        getChildren().add(rootBox);
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Set<WeekDay> getAvailableDaySet() {
        return availableDaySet;
    }

    public String getMealName() {
        return nameText.getText();
    }

    public Double getPrice() {
        return Double.valueOf(priceText.getText());
    }

    public void alertInputResponse(String message) {
        addMealInfoLabel.setText(message);
    }
}
