package ua.yandex.shad.collections;

import java.util.Iterator;

public class StringList implements Iterable<String> {

    private Node head;
    private Node tail;

    private static class Node {

        private String value;
        private Node next;

        public Node(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node newNext) {
            this.next = newNext;
        }
    }

    public void add(String s) {
        Node cur = new Node(s);
        if (head != null) {
            head.setNext(cur);
        }
        head = cur;
        if (tail == null) {
            tail = head;
        }
    }

    public Iterator<String> iterator() {
        return new StringQueueIterator();
    }

    private class StringQueueIterator implements Iterator<String> {

        private Node cur = tail;

        public boolean hasNext() {
            return cur != null;
        }

        public String next() {
            String value = cur.getValue();
            cur = cur.getNext();
            return value;
        }

    }
}
