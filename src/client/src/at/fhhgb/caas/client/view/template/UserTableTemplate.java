package at.fhhgb.caas.client.view.template;

import at.fhhgb.caas.client.controller.PortalController;
import at.fhhgb.caas.client.resources.lang.LanguageConsts;
import at.fhhgb.caas.client.resources.css.CSSConsts;
import at.fhhgb.caas.client.view.layout.UIObject;
import at.fhhgb.caas.data.Person;
import at.fhhgb.caas.data.UserStatus;
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
public class UserTableTemplate extends UIObject<PortalController> {
    public UserTableTemplate(PortalController controller) {
        super(controller);
        initComponents();
    }

    private void initComponents() {
        TableView<Person> userTable = new TableView<>();
        userTable.getStyleClass().add(CSSConsts.CLASS_TABLE_VIEW);
        userTable.setMinWidth(PREF_TABLE_WIDTH);

        TableColumn<Person, String> usernameCol = new TableColumn<>(lang.getString(LanguageConsts.TABLE_COL_USERNAME));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Person, String> personIdCol = new TableColumn<>(lang.getString(LanguageConsts.TABLE_COL_PERSONID));
        personIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Person, String> emailCol = new TableColumn<>(lang.getString(LanguageConsts.TABLE_COL_EMAIL));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Person, String> authTypeCol = new TableColumn<>(lang.getString(LanguageConsts.TABLE_COL_AUTHTYPE));
        authTypeCol.setCellValueFactory(new PropertyValueFactory<>("authType"));

        TableColumn<Person, String> statusCol = new TableColumn<>(lang.getString(LanguageConsts.TABLE_COL_STATUS));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("userStatus"));

        TableColumn<Person, String> firstnameCol = new TableColumn<>(lang.getString(LanguageConsts.TABLE_COL_FIRSTNAME));
        firstnameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Person, String> lastnameCol = new TableColumn<>(lang.getString(LanguageConsts.TABLE_COL_LASTNAME));
        lastnameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Person, HBox> optionCol = new TableColumn<>(lang.getString(LanguageConsts.TABLE_COL_OPTIONS));
        optionCol.setSortable(false);
        optionCol.setCellValueFactory(row ->
                        new ObservableValueBase<HBox>() {
                            @Override
                            public HBox getValue() {
                                HBox optionBox = new HBox();

                                Button editButton = new Button(lang.getString(LanguageConsts.EDIT_BUTTON));
                                editButton.getStyleClass().add(CSSConsts.CLASS_BUTTON);
                                editButton.addEventHandler(ActionEvent.ACTION, event -> controller.onClickAddUser(row.getValue()));
                                optionBox.getChildren().add(editButton);

                                Button removeButton = new Button(lang.getString(LanguageConsts.REMOVE_BUTTON));
                                removeButton.getStyleClass().add(CSSConsts.CLASS_BUTTON_CANCEL);
                                removeButton.addEventHandler(ActionEvent.ACTION, event -> controller.removePerson(row.getValue()));
                                optionBox.getChildren().add(removeButton);

                                if (row.getValue().getUserStatus() == UserStatus.ACTIVE) {
                                    Button blockButton = new Button(lang.getString(LanguageConsts.BLOCK_BUTTON));
                                    blockButton.getStyleClass().add(CSSConsts.CLASS_BUTTON_CANCEL);
                                    blockButton.addEventHandler(ActionEvent.ACTION, event -> controller.blockPerson(row.getValue()));
                                    optionBox.getChildren().add(blockButton);
                                } else if (row.getValue().getUserStatus() == UserStatus.BLOCKED) {
                                    Button unblockButton = new Button(lang.getString(LanguageConsts.UNBLOCK_BUTTON));
                                    unblockButton.addEventHandler(ActionEvent.ACTION, event -> controller.unblockPerson(row.getValue()));
                                    optionBox.getChildren().add(unblockButton);
                                }

                                return optionBox;
                            }
                        }
        );

        userTable.setItems(controller.getPersons());

        userTable.getColumns().addAll(usernameCol, personIdCol, firstnameCol, lastnameCol, emailCol, authTypeCol, statusCol, optionCol);

        getChildren().add(userTable);
    }
}
