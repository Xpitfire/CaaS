package at.fhhgb.caas.client.view.template;

import at.fhhgb.caas.client.controller.PortalController;
import at.fhhgb.caas.client.resources.lang.LanguageConsts;
import at.fhhgb.caas.client.resources.css.CSSConsts;
import at.fhhgb.caas.client.view.layout.UIObject;
import at.fhhgb.caas.data.Category;
import javafx.beans.value.ObservableValueBase;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

/**
 * Created by Dinu Marius-Constantin on 14.05.2015.
 */
public class CategoryTableTemplate extends UIObject<PortalController> {
    public CategoryTableTemplate(PortalController controller) {
        super(controller);
        initComponents();
    }

    private void initComponents() {
        TableView<Category> categoriesTable = new TableView<>();
        categoriesTable.getStyleClass().add(CSSConsts.CLASS_TABLE_VIEW);
        categoriesTable.setMinWidth(PREF_TABLE_WIDTH);

        TableColumn<Category, String> categoryCol = new TableColumn<>(lang.getString(LanguageConsts.TABLE_COL_CATEGORY));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Category, HBox> optionCol = new TableColumn<>(lang.getString(LanguageConsts.TABLE_COL_OPTIONS));
        optionCol.setSortable(false);
        optionCol.setCellValueFactory(col ->
                        new ObservableValueBase<HBox>() {
                            @Override
                            public HBox getValue() {
                                HBox optionBox = new HBox();
                                Button removeButton = new Button(lang.getString(LanguageConsts.REMOVE_BUTTON));
                                removeButton.getStyleClass().add(CSSConsts.CLASS_BUTTON_CANCEL);
                                removeButton.addEventHandler(ActionEvent.ACTION, event -> controller.removeCategory(col.getValue()));
                                optionBox.getChildren().add(removeButton);
                                return optionBox;
                            }
                        }
        );


        categoriesTable.setItems(controller.getCategories());
        categoriesTable.getColumns().addAll(categoryCol, optionCol);

        getChildren().add(categoriesTable);
    }
}
