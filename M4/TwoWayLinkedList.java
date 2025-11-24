import java.util.ListIterator;
import java.util.NoSuchElementException;


interface MyList<E> {
    void add(E e);
    void add(int index, E e);
    void clear();
    boolean contains(Object o);
    E get(int index);
    int indexOf(Object o);
    int lastIndexOf(Object o);
    boolean remove(Object o);
    E remove(int index);
    E set(int index, E e);
    int size();
    boolean isEmpty();
    ListIterator<E> listIterator();
    ListIterator<E> listIterator(int index);
}


class Node<E> {
    E element;
    Node<E> next;
    Node<E> previous;

    public Node(E e) {
        element = e;
    }
}


public class TwoWayLinkedList<E> implements MyList<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size = 0;



    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<E> getNode(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        Node<E> current;

        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++)
                current = current.next;
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--)
                current = current.previous;
        }

        return current;
    }


    @Override
    public void add(E e) {
        add(size, e);
    }

    @Override
    public void add(int index, E e) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        Node<E> newNode = new Node<>(e);

        if (size == 0) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
        } else if (index == size) {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        } else {
            Node<E> current = getNode(index);
            Node<E> prev = current.previous;

            newNode.previous = prev;
            newNode.next = current;

            prev.next = newNode;
            current.previous = newNode;
        }

        size++;
    }

    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public E get(int index) {
        return getNode(index).element;
    }

    @Override
    public int indexOf(Object o) {
        Node<E> current = head;
        int i = 0;

        while (current != null) {
            if ((o == null && current.element == null) ||
                (o != null && o.equals(current.element)))
                return i;

            current = current.next;
            i++;
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Node<E> current = tail;
        int i = size - 1;

        while (current != null) {
            if ((o == null && current.element == null) ||
                (o != null && o.equals(current.element)))
                return i;

            current = current.previous;
            i--;
        }

        return -1;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index == -1)
            return false;

        remove(index);
        return true;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        Node<E> removed;

        if (size == 1) {
            removed = head;
            head = tail = null;
        } else if (index == 0) {
            removed = head;
            head = head.next;
            head.previous = null;
        } else if (index == size - 1) {
            removed = tail;
            tail = tail.previous;
            tail.next = null;
        } else {
            removed = getNode(index);
            Node<E> prev = removed.previous;
            Node<E> next = removed.next;

            prev.next = next;
            next.previous = prev;
        }

        size--;
        return removed.element;
    }

    @Override
    public E set(int index, E e) {
        Node<E> node = getNode(index);
        E old = node.element;
        node.element = e;
        return old;
    }

    @Override
    public int size() {
        return size;
    }


    @Override
    public ListIterator<E> listIterator() {
        return new TwoWayIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new TwoWayIterator(index);
    }

    private class TwoWayIterator implements ListIterator<E> {
        private Node<E> current;
        private int currentIndex;

        public TwoWayIterator(int index) {
            if (index == size) {
                current = null;
            } else {
                current = getNode(index);
            }
            currentIndex = index;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();

            E value = current.element;
            current = current.next;
            currentIndex++;
            return value;
        }

        @Override
        public boolean hasPrevious() {
            return currentIndex > 0;
        }

        @Override
        public E previous() {
            if (!hasPrevious())
                throw new NoSuchElementException();

            current = (current == null) ? tail : current.previous;
            E value = current.element;
            currentIndex--;
            return value;
        }

        @Override
        public int nextIndex() {
            return currentIndex;
        }

        @Override
        public int previousIndex() {
            return currentIndex - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove not supported.");
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException("set not supported.");
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException("add not supported.");
        }
    }
}
