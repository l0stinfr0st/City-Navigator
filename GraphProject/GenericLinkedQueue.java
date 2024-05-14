package GraphProject;

public class GenericLinkedQueue<T> {

    private int size;
    private Node first;
    private Node last;

    private static class Node<T> {

        private T item;
        private Node next;

        public Node(T item) {
            this.item = item;
        }

        public Node getNext() {
            return next;
        }

        public T getItem() {
            return item;
        }

    }

    public GenericLinkedQueue() {
        first = null;
        size = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    public T peek() {
        if (!isEmpty()) {
            return (T) first.item;
        }
        return null;
    }

    public void enqueue(T entry) {
        Node newNode = new Node(entry);
        if (isEmpty()) {
            first = last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Stack underflow");
        }
        Node tmp = first;
        first = first.next;
        tmp.next = null;
        size--;
        return (T) tmp.item;     
    }

}

