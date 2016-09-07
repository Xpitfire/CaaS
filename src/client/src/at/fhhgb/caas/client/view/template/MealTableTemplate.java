package at.fhhgb.caas.client.view.template;

import at.fhhgb.caas.client.controller.PortalController;
import at.fhhgb.caas.client.resources.lang.LanguageConsts;
import at.fhhgb.caas.client.resources.css.CSSConsts;
import at.fhhgb.caas.client.view.layout.UIObject;
import at.fhhgb.caas.data.Category;
import at.fhhgb.caas.data.Meal;
import at.fhhgb.caas.data.WeekDay;
import javafx.beans.value.ObservableValueBase;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.util.Set;

/**
 * Created by Dinu Marius-Constantin on 13.05.2015.
 */
public class MealTableTemplate extends UIObject<PortalController> {

    private boolean showAdminView;

    public MealTableTemplate(PortalController controller, boolean showAdminView) {
        super(controller);
        this.showAdminView = showAdminView;
        initComponents();
    }

    public MealTableTemplate(PortalController controller) {
        this(controller, false);
    }

    private void initComponents() {
        TableView<Meal> mealTable = new TableView<>();
        mealTable.getStyleClass().add(CSSConsts.CLASS_TABLE_VIEW);
        mealTable.setMinWidth(PREF_TABLE_WIDTH);

        TableColumn<Meal, String> mealCol = new TableColumn<>(lang.getString(LanguageConsts.TABLE_COL_MEAL));
        mealCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Meal, Double> priceCol = new TableColumn<>(lang.getString(LanguageConsts.TABLE_COL_PRICE));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Meal, Set<Category>> categoryCol = null;
        if (showAdminView) {
            categoryCol = new TableColumn<>(lang.getString(LanguageConsts.TABLE_COL_CATEGORY));
            categoryCol.setCellValueFactory(new PropertyValueFactory<>("categories"));
        }

        TableColumn<Meal, Set<WeekDay>> dayAvailableCol = new TableColumn<>(lang.getString(LanguageConsts.TABLE_COL_DAY_AVAILABLE));
        dayAvailableCol.setCellValueFactory(new PropertyValueFactory<>("availableDaySet"));


        TableColumn<Meal, HBox> optionCol = null;
        if (showAdminView) {
            optionCol = new TableColumn<>(lang.getString(LanguageConsts.TABLE_COL_OPTIONS));
            optionCol.setSortable(false);
            optionCol.setCellValueFactory(col ->
                            new ObservableValueBase<HBox>() {
                                @Override
                                public HBox getValue() {
                                    HBox optionBox = new HBox();
                                    Button removeButton = new Button(lang.getString(LanguageConsts.REMOVE_BUTTON));
                                    removeButton.getStyleClass().add(CSSConsts.CLASS_BUTTON_CANCEL);
                                    removeButton.addEventHandler(ActionEvent.ACTION, event -> controller.removeMeal(col.getValue()));
                                    optionBox.getChildren().add(removeButton);
                                    return optionBox;
                                }
                            }
            );
        }

        mealTable.setItems(controller.getMeals());

        mealTable.getColumns().addAll(mealCol, priceCol, dayAvailableCol);
        if (categoryCol != null)
            mealTable.getColumns().add(categoryCol);
        if (optionCol != null)
            mealTable.getColumns().add(optionCol);

        getChildren().add(mealTable);
    }
}
