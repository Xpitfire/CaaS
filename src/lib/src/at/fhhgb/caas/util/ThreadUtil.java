package at.fhhgb.caas.util;

/**
 * Created by Dinu Marius-Constantin on 18.05.2015.
 */
public final class ThreadUtil {

    public static final long SLEEP_200_MILLIS = 200;

    private ThreadUtil() {
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }

}
