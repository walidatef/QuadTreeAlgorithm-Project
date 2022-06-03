
public class LinkedList<T> {
    private int size;
    private Node head;

    public LinkedList() {
        size = 0;
        head = null;
    }


    public int getSize() {
        return size;
    }


    public void insert(T element) {

        Node tempNode = new Node(element);
        if (head == null) {
            head = tempNode;
        }
        else {
            Node current = head;


            while (current.getNext() != null)
                current = current.getNext();

            current.setNext(tempNode);

        }
        size++;
    }


    public boolean delete(int index) {
        if (index == 0) {
            head = head.getNext();
            size--;
            return true;
        }
        Node current = head;

        for (int i = 0; i < index; i++)
            current = current.getNext();
        if (current.getNext() == null)
            current.setNext(null);
        else {
            current.setElement(current.getNext().getElement());
            current.setNext(current.getNext().getNext());
        }
        size--;
        return true;

    }


    public boolean exists(T element) {

        if (size == 0)
            return false;

        else {
            Node current = head;

            while (current != null) {
                if (current.getElement().equals(element))
                    return true;

                current = current.getNext();
            }
        }
        return false;
    }


    public T get(int index) {
        Node current = head;
        T temp = current.element;
        if (index == 0)
            return temp;
        else {
            for (int i = 0; i < index; i++)
                current = current.getNext();
            temp = current.getElement();
        }
        return temp;
    }


    private class Node {

        Node next;
        T element;


        public Node(T element) {
            this.element = element;
            next = null;
        }


        public T getElement() {
            return element;
        }


        public void setElement(T element) {
            this.element = element;
        }


        public Node getNext() {
            return next;
        }

        public void setNext(Node nextNode) {
            next = nextNode;
        }
    }

}