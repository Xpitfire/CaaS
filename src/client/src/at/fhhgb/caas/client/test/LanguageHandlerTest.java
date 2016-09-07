package at.fhhgb.caas.client.test;

import at.fhhgb.caas.client.resources.lang.LanguageConsts;
import at.fhhgb.caas.client.resources.lang.LanguageFactory;
import org.junit.Test;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.*;

/**
 * Created by Dinu Marius-Constantin on 10.05.2015.
 */
public class LanguageHandlerTest {

    @Test
    public void testLanguageFactoryResourceBundle() {
        ResourceBundle rb = LanguageFactory.getBundle(LanguageConsts.LANGUAGE_RESOURCE_BUNDLE);
        assertTrue(rb != null);
    }

    @Test
    public void testLanguageFactoryChangeLocale() {
        LanguageFactory.changeLocale(Locale.GERMAN);
        ResourceBundle rb = LanguageFactory.getBundle(LanguageConsts.LANGUAGE_RESOURCE_BUNDLE);
        assertEquals("Einloggen", rb.getString(LanguageConsts.LOGIN_BUTTON));
    }

}
