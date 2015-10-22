package ua.yandex.shad.utils;

import java.util.Iterator;

public class Iterables<T> {
    public String toString(Iterable<T> iterable) {
        Iterator<T> iterator = iterable.iterator();
        StringBuilder stringBuilder = new StringBuilder();
        if (iterator.hasNext()) {
            stringBuilder.append(iterator.next().toString());
        }
        while (iterator.hasNext()) {
            stringBuilder.append(" ");
            stringBuilder.append(iterator.next().toString());
        }
        return stringBuilder.toString();
    }
}
