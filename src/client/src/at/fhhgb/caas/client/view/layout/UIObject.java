package at.fhhgb.caas.client.view.layout;

import at.fhhgb.caas.client.controller.DefaultController;
import at.fhhgb.caas.client.resources.lang.LanguageConsts;
import at.fhhgb.caas.client.resources.lang.LanguageFactory;
import at.fhhgb.caas.client.resources.css.CSSConsts;
import javafx.scene.layout.Pane;

import java.util.ResourceBundle;

/**
 * Created by Dinu Marius-Constantin on 11.05.2015.
 */
public abstract class UIObject<T extends DefaultController> extends Pane {

    protected final ResourceBundle lang = LanguageFactory.getBundle(LanguageConsts.LANGUAGE_RESOURCE_BUNDLE);

    public static final int PREF_RETURN_WIDTH = 80;
    public static final int PREF_HEADER_HEIGTH = 150;
    public static final int PREF_PANE_WIDTH = 980;
    public static final int PREF_TABLE_WIDTH = 860;

    protected T controller;

    public UIObject(T controller) {
        this.controller = controller;
        getStylesheets().add(CSSConsts.BASIC_LAYOUT_CSS);
        getStyleClass().add(CSSConsts.CLASS_UIOBJECT);
    }

}
