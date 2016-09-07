package at.fhhgb.caas.client.resources.lang;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Dinu Marius-Constantin on 10.05.2015.
 */
public final class LanguageFactory {

    private static final Map<String, ResourceBundle> bundleMap = new HashMap<>();
    public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

    private static Locale currentLocale = DEFAULT_LOCALE;

    private LanguageFactory() {
    }

    public static ResourceBundle getBundle(String bundleName) {
        if (bundleMap.containsKey(bundleName)) {
            return bundleMap.get(bundleName);
        } else {
            bundleMap.put(bundleName, ResourceBundle.getBundle(bundleName, currentLocale));
            return bundleMap.get(bundleName);
        }
    }

    public static void changeLocale(Locale locale) {
        currentLocale = locale;
        bundleMap.forEach((bundleName, resourceBundle) ->
                bundleMap.put(bundleName, ResourceBundle.getBundle(bundleName, locale)));
    }

}
