package at.fhhgb.caas.client.view.template;

import at.fhhgb.caas.client.controller.PortalController;
import at.fhhgb.caas.client.resources.lang.LanguageConsts;
import at.fhhgb.caas.client.resources.css.CSSConsts;
import at.fhhgb.caas.client.view.layout.UIObject;
import at.fhhgb.caas.data.Person;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by Dinu Marius-Constantin on 11.05.2015.
 */
public class UserInfoBoxTemplate extends UIObject<PortalController> {

    public static final String USER_INFO_IMAGE_PLACEHOLDER = "/resources/img/user_info_placeholder.png";

    public UserInfoBoxTemplate(PortalController controller) {
        super(controller);
        initComponents();
    }

    private String getUserProfileName(Person person) {
        if (person == null)
            return "";
        else if (person.getFirstName() == null && person.getLastName() == null)
            return person.getUsername();
        else if (person.getFirstName() != null && person.getLastName() == null)
            return person.getFirstName();
        else if (person.getFirstName() == null && person.getLastName() != null)
            return person.getLastName();
        else
            return String.format(person.getFirstName(), " ", person.getLastName());
    }

    private void initComponents() {
        VBox rootBox = new VBox();

        HBox userProfileBox = new HBox();
        rootBox.getChildren().add(userProfileBox);

        ImageView headerImg = new ImageView(new Image(USER_INFO_IMAGE_PLACEHOLDER));
        userProfileBox.getChildren().add(headerImg);

        VBox profileDetailBox = new VBox();

        Label userNameLabel = new Label();
        userNameLabel.getStyleClass().add(CSSConsts.CLASS_LABLE);
        userNameLabel.setText(getUserProfileName(controller.getPerson()));
        profileDetailBox.getChildren().add(userNameLabel);

        Label authLabel = new Label();
        authLabel.getStyleClass().add(CSSConsts.CLASS_LABLE);
        Person person = controller.getPerson();
        if (person != null)
            authLabel.setText(person.getAuthType().toString());
        profileDetailBox.getChildren().add(authLabel);

        userProfileBox.getChildren().add(profileDetailBox);

        HBox userOrderBox = new HBox();
        rootBox.getChildren().add(userOrderBox);

        Button logoutButton = new Button(lang.getString(LanguageConsts.LOGOUT_BUTTON));
        logoutButton.addEventHandler(ActionEvent.ACTION, event -> controller.onClickLogout());
        userOrderBox.getChildren().add(logoutButton);

        Button seeOrdersButton = new Button(lang.getString(LanguageConsts.SEE_ORDERS_LABEL));
        userOrderBox.getChildren().add(seeOrdersButton);
        seeOrdersButton.addEventHandler(ActionEvent.ACTION, event -> controller.onClickCustomerOrders());

        HBox timeSetBox = new HBox();
        rootBox.getChildren().add(timeSetBox);

        getChildren().add(rootBox);
    }

}
