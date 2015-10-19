package ua.yandex.shad.collections;

import java.util.Iterator;

public class StringIterables {
    public static String toString(Iterable<String> iterable) {
        Iterator<String> iterator = iterable.iterator();
        String result = "";
        if (iterator.hasNext()) {
            result = iterator.next();
        }
        while (iterator.hasNext()) {
            result += " " + iterator.next();
        }
        return result;
    }
}
