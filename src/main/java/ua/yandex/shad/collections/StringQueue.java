package ua.yandex.shad.collections;

public class StringQueue {

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

    public String remove() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        String value = tail.getValue();
        tail = tail.getNext();
        if (tail == null) {
            head = null;
        }
        return value;
    }

    public boolean isEmpty() {
        return tail == head && head == null;
    }
}
