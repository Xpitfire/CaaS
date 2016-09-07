package at.fhhgb.caas.client.controller;

import at.fhhgb.caas.client.CaaSApp;
import at.fhhgb.caas.client.model.*;
import at.fhhgb.caas.client.resources.lang.LanguageConsts;
import at.fhhgb.caas.client.resources.css.CSSConsts;
import at.fhhgb.caas.client.view.PortalView;
import at.fhhgb.caas.client.view.template.*;
import at.fhhgb.caas.data.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Dinu Marius-Constantin on 11.05.2015.
 */
public class PortalController extends DefaultController<PortalView> {

    private static final Logger logger = Logger.getLogger(CaaSApp.CLIENT_LOGGER);

    private ObservableList<WeekDay> availableDaysList = FXCollections.observableList(Arrays.asList(WeekDay.values()));

    private String userName;

    private AddUserTemplate addUserTemplate;
    private AddCategoryTemplate addCategoryTemplate;
    private AddMealTemplate addMealTemplate;

    public PortalController(Stage primaryStage, String userName) {
        super(primaryStage);
        this.userName = userName;
    }

    public void onClickSeeMenu() {
        onClickPortalReturnButton();
        view.getSelectionPane().getChildren().add(new CategorySelectionTabTemplate(this));
        view.setHeaderTitleInfo(lang.getString(LanguageConsts.PORTAL_TILE_DESC_SEE_MENU));
        view.getContentPane().getChildren().clear();
        Pane contentTemplate = new Pane();
        contentTemplate.getChildren().add(new MealTableTemplate(this));
        view.getContentPane().getChildren().add(contentTemplate);
    }

    public void onClickCustomerOrders() {
        onClickPortalReturnButton();
        view.setHeaderTitleInfo(lang.getString(LanguageConsts.PORTAL_TILE_DESC_ORDERS));
        view.getSelectionPane().getChildren().clear();
        view.getContentPane().getChildren().clear();
        Pane contentTemplate = new Pane();
        contentTemplate.getChildren().add(new OrdersTableTemplate(this));
        view.getContentPane().getChildren().add(contentTemplate);
    }

    private void onClickReturnPrevButton(Function function) {
        view.getHeaderTitleNavigationRegion().getChildren().clear();
        Button returnButton = new Button(lang.getString(LanguageConsts.CANCEL_RETURN_PREV_BUTTON));
        returnButton.getStyleClass().add(CSSConsts.CLASS_BUTTON_CANCEL);
        returnButton.addEventHandler(ActionEvent.ACTION, event -> function.apply(null));
        view.getHeaderTitleNavigationRegion().getChildren().add(returnButton);
    }

    public void onClickAddUser(Person person) {
        onClickReturnPrevButton(function -> {
            onClickAdminSection();
            onClickPortalReturnButton();
            onClickAdminSectionUsers();
            return null;
        });
        view.getSelectionPane().getChildren().clear();
        view.setHeaderTitleInfo(lang.getString(LanguageConsts.PORTAL_TILE_DESC_ADD_USER));
        view.getContentPane().getChildren().clear();
        addUserTemplate = new AddUserTemplate(this, person);
        view.getContentPane().getChildren().add(addUserTemplate);
    }

    public void onClickPortalReturnButton() {
        view.getHeaderTitleNavigationRegion().getChildren().clear();
        Button returnButton = new Button(lang.getString(LanguageConsts.PORTAL_RETURN_BUTTON));
        returnButton.addEventHandler(ActionEvent.ACTION, event -> onClickPortalReturn());
        view.getHeaderTitleNavigationRegion().getChildren().add(returnButton);
    }

    public void onClickAdminSection() {
        onClickPortalReturnButton();
        view.setHeaderTitleInfo(lang.getString(LanguageConsts.PORTAL_TILE_DESC_ADMIN_SECTION));
        view.getContentPane().getChildren().clear();
        view.getSelectionPane().getChildren().add(new AdminSectionTabTemplate(this));
    }

    public void onClickAdminSectionCategories() {
        view.getContentPane().getChildren().clear();
        VBox contentTemplate = new VBox();
        Button addCategory = new Button(lang.getString(LanguageConsts.ADD_NEW_CATEGORY));
        addCategory.addEventHandler(ActionEvent.ACTION, event -> {
            onClickReturnPrevButton(function -> {
                onClickAdminSection();
                onClickPortalReturnButton();
                onClickAdminSectionCategories();
                return null;
            });

            view.getSelectionPane().getChildren().clear();
            view.getContentPane().getChildren().clear();
            addCategoryTemplate = new AddCategoryTemplate(this);
            view.getContentPane().getChildren().add(addCategoryTemplate);
        });
        contentTemplate.getChildren().addAll(addCategory, new CategoryTableTemplate(this));
        view.getContentPane().getChildren().add(contentTemplate);
    }

    public void onClickAdminSectionMenu() {
        view.getContentPane().getChildren().clear();
        VBox contentTemplate = new VBox();
        Button addMeal = new Button(lang.getString(LanguageConsts.ADD_NEW_MEAL));
        addMeal.addEventHandler(ActionEvent.ACTION, event -> {
            onClickReturnPrevButton(function -> {
                onClickAdminSection();
                onClickPortalReturnButton();
                onClickAdminSectionMenu();
                return null;
            });

            view.getSelectionPane().getChildren().clear();
            view.getContentPane().getChildren().clear();
            addMealTemplate = new AddMealTemplate(this);
            view.getContentPane().getChildren().add(addMealTemplate);
        });
        contentTemplate.getChildren().addAll(addMeal, new MealTableTemplate(this, true));
        view.getContentPane().getChildren().add(contentTemplate);
    }

    public void onClickAdminSectionUsers() {
        view.getContentPane().getChildren().clear();
        VBox contentTemplate = new VBox();
        Button addUser = new Button(lang.getString(LanguageConsts.ADD_NEW_USER));
        addUser.addEventHandler(ActionEvent.ACTION, event -> {
            onClickReturnPrevButton(function -> {
                onClickAdminSection();
                onClickPortalReturnButton();
                onClickAdminSectionUsers();
                return null;
            });

            onClickPortalReturnButton();
            view.getSelectionPane().getChildren().clear();
            onClickAddUser(null);
        });
        contentTemplate.getChildren().addAll(addUser, new UserTableTemplate(this));
        view.getContentPane().getChildren().add(contentTemplate);
    }

    public void onClickHandleUserFormConfirm(boolean modify) {
        String username = addUserTemplate.getUsername();
        String studentID = addUserTemplate.getStudentID();
        CharSequence password = addUserTemplate.getPassword();

        if (username == null || username.length() <= 0
                || studentID == null || studentID.length() <= 0
                || password == null || password.length() <= 0) {
            addUserTemplate.alertInputResponse(lang.getString(LanguageConsts.FILL_IN_MANDATORY_FIELDS));
        } else {
            Person person = new Person(username, studentID, password, addUserTemplate.getAuthenticationType());
            person.setFirstName(addUserTemplate.getFirstname());
            person.setLastName(addUserTemplate.getLastname());
            person.setEmail(addUserTemplate.getEmail());

            if (modify) {
                modifyPerson(person);

                onClickAdminSection();
                onClickAdminSectionUsers();
            } else {
                if (!addPerson(person)) {
                    addUserTemplate.alertInputResponse(lang.getString(LanguageConsts.USER_ALREADY_EXISTS));
                } else {
                    onClickAdminSection();
                    onClickAdminSectionUsers();
                }
            }
        }
    }

    public void onClickAddCategoryFormConfirm() {
        String categoryName = addCategoryTemplate.getCategoryName();
        if (categoryName == null || categoryName.length() <= 0) {
            addCategoryTemplate.alertInputResponse(lang.getString(LanguageConsts.FILL_IN_MANDATORY_FIELDS));
        } else {
            Category category = new Category(categoryName);
            addCategory(category);
            onClickAdminSection();
            onClickAdminSectionCategories();
        }
    }

    public void onClickAddMealFormConfirm() {
        String mealName = addMealTemplate.getMealName();
        double price = addMealTemplate.getPrice();
        Set<Category> categories = addMealTemplate.getCategories();
        Set<WeekDay> weekDays = addMealTemplate.getAvailableDaySet();

        if (mealName == null || mealName.length() <= 0
                || price < 0.0
                || categories == null || categories.size() <= 0
                || weekDays == null) {
            addMealTemplate.alertInputResponse(lang.getString(LanguageConsts.FILL_IN_MANDATORY_FIELDS));
        } else {
            Meal meal = new Meal(mealName);
            meal.setPrice(price);
            categories.stream().forEach(category -> meal.addCategory(category));
            weekDays.stream().forEach(weekDay -> meal.addAvailableDay(weekDay));
            addMeal(meal);
            onClickAdminSection();
            onClickAdminSectionMenu();
        }
    }

    public void onClickPortalReturn() {
        primaryStage.setScene(new Scene(new PortalView(this)));
    }

    public Pane createPortalTileView() {
        PortalTilesTemplate tileTemplate = new PortalTilesTemplate(this);
        Person person = getPerson(userName);
        if (person == null) {
            onClickLogout();
        } else {

            AuthType authType = person.getAuthType();

            tileTemplate.getTilePane().getChildren().add(tileTemplate.getMenuTile());
            tileTemplate.getTilePane().getChildren().add(tileTemplate.getCostumerOrdersTile());

            if (authType == AuthType.ADMIN) {
                tileTemplate.getTilePane().getChildren().add(tileTemplate.getAddUserTile());
                tileTemplate.getTilePane().getChildren().add(tileTemplate.getAdminSectionTile());
            }
        }
        return tileTemplate;
    }

    public void showMenuWithFilter(Category filter) {
        try {
            DAOFactory.getMealDAO().filter(filter);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not filter meal!", e);
        }
    }

    private boolean addPerson(Person person) {
        try {
            return DAOFactory.getPersonDAO().addPerson(person);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not add person!", e);
        }
        return false;
    }

    public Person getPerson() {
        return getPerson(userName);
    }

    public Person getPerson(String userName) {
        try {
            return DAOFactory.getPersonDAO().getPerson(userName);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not get person!", e);
        }
        return null;
    }

    private void modifyPerson(Person person) {
        try {
            DAOFactory.getPersonDAO().modifyPerson(person);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not modify person!", e);
        }
    }

    public ObservableList<Person> getPersons() {
        try {
            return (ObservableList<Person>) DAOFactory.getPersonDAO().getPersons();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not get person list!", e);
        }
        return FXCollections.emptyObservableList();
    }

    public void addMeal(Meal meal) {
        try {
            DAOFactory.getMealDAO().addMeal(meal);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not add meal!", e);
        }
    }

    public ObservableList<Meal> getMeals() {
        try {
            return (ObservableList<Meal>) DAOFactory.getMealDAO().getMeals();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not get meal list!", e);
        }
        return FXCollections.emptyObservableList();
    }

    public ObservableList<Order> getOrders() {
        try {
            return (ObservableList<Order>) DAOFactory.getOrderDAO().getOrders();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not get order list!", e);
        }
        return FXCollections.emptyObservableList();
    }

    public ObservableList<Category> getCategories() {
        try {
            return (ObservableList<Category>) DAOFactory.getCategoryDAO().getCategories();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not get category list!", e);
        }
        return FXCollections.emptyObservableList();
    }

    public void addCategory(Category category) {
        try {
            DAOFactory.getCategoryDAO().addCategory(category);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not add category!", e);
        }
    }

    public ObservableList<WeekDay> getAvailableDays() {
        return availableDaysList;
    }

    public void removeMeal(Meal meal) {
        try {
            DAOFactory.getMealDAO().removeMeal(meal);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not remove meal!", e);
        }
    }

    public void removeCategory(Category category) {
        try {
            DAOFactory.getCategoryDAO().removeCategory(category);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not remove category!", e);
        }
    }

    public void removePerson(Person person) {
        if (person != null && !person.getUsername().equals(userName)) {
            try {
                DAOFactory.getPersonDAO().removePerson(person);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Could not remove person!", e);
            }
        }
    }

    public void blockPerson(Person person) {
        if (person != null && !person.getUsername().equals(userName)) {
            person.setUserStatus(UserStatus.BLOCKED);
            try {
                DAOFactory.getPersonDAO().modifyPerson(person);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Could not modify person!", e);
            }
        }
    }

    public void unblockPerson(Person person) {
        if (person != null) {
            person.setUserStatus(UserStatus.ACTIVE);
            try {
                DAOFactory.getPersonDAO().modifyPerson(person);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Could not modify person!", e);
            }
        }
    }

}
