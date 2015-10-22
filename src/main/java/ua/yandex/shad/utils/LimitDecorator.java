package ua.yandex.shad.utils;

import java.util.Iterator;

public class LimitDecorator implements Iterable<String> {

    private final Iterable<String> generator;
    private final int limiter;

    public LimitDecorator(Iterable<String> generator, int limiter) {
        this.generator = generator;
        this.limiter = limiter;
    }

    public Iterator<String> iterator() {
        return new LimitedIterator();
    }
    private class LimitedIterator implements Iterator<String> {

        private Iterator<String> iterator = generator.iterator();
        private String next;
        private int left = limiter;

        {
            if (iterator.hasNext()) {
                next = iterator.next();
                left--;
            }
        }

        @Override
        public boolean hasNext() {
            return next != null && left >= 0;
        }

        @Override
        public String next() {
            String current = next;
            if (iterator.hasNext()) {
                next = iterator.next();
                if (next.length() != current.length()) {
                    left--;
                }
            } else {
                next = null;
            }
            return current;
        }
    }
}
