package at.fhhgb.caas.client.view.template;

import at.fhhgb.caas.client.controller.PortalController;
import at.fhhgb.caas.client.resources.lang.LanguageConsts;
import at.fhhgb.caas.client.resources.css.CSSConsts;
import at.fhhgb.caas.client.view.layout.UIObject;
import at.fhhgb.caas.data.Meal;
import at.fhhgb.caas.data.Order;
import at.fhhgb.caas.data.OrderStatus;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

/**
 * Created by Dinu Marius-Constantin on 14.05.2015.
 */
public class OrdersTableTemplate extends UIObject<PortalController> {
    public OrdersTableTemplate(PortalController controller) {
        super(controller);
        initComponents();
    }

    private void initComponents() {
        TableView<Order> ordersTable = new TableView<>();
        ordersTable.getStyleClass().add(CSSConsts.CLASS_TABLE_VIEW);
        ordersTable.setMinWidth(PREF_TABLE_WIDTH);

        TableColumn<Order, String> timeCol = new TableColumn<>(lang.getString(LanguageConsts.TABLE_COL_TIME));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("deliverHour"));

        TableColumn<Order, OrderStatus> orderStatusCol = new TableColumn<>(lang.getString(LanguageConsts.TABLE_COL_ORDERSTATUS));
        orderStatusCol.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));

        TableColumn<Order, List<Meal>> mealListCol = new TableColumn<>(lang.getString(LanguageConsts.TABLE_COL_MEAL));
        mealListCol.setCellValueFactory(new PropertyValueFactory<>("mealList"));

        TableColumn<Order, String> orderIdCol = new TableColumn<>(lang.getString(LanguageConsts.TABLE_COL_ORDERID));
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderID"));

        TableColumn<Order, String> personIdCol = new TableColumn<>(lang.getString(LanguageConsts.TABLE_COL_CUSTOMER));
        personIdCol.setCellValueFactory(new PropertyValueFactory<>("personId"));

        TableColumn<Order, Double> billCol = new TableColumn<>(lang.getString(LanguageConsts.TABLE_COL_BILL));
        billCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        ordersTable.setItems(controller.getOrders());
        ordersTable.getColumns().addAll(timeCol, orderStatusCol, mealListCol, orderIdCol, personIdCol, billCol);

        getChildren().add(ordersTable);
    }
}
