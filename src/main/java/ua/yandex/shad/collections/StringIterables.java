package ua.yandex.shad.collections;

import java.util.Iterator;

public class StringIterables {
    public static String toString(Iterable<String> iterable) {
        Iterator<String> iterator = iterable.iterator();
        StringBuilder stringBuilder = new StringBuilder();
        if (iterator.hasNext()) {
            stringBuilder.append(iterator.next());
        }
        while (iterator.hasNext()) {
            stringBuilder.append(" ");
            stringBuilder.append(iterator.next());
        }
        return stringBuilder.toString();
    }
}
