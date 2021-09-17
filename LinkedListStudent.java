public class LinkedListStudent {

    private LLNode head; //head of linked list
    private int size; //size of linked list

    //getters
    public LLNode getHead() {
        return head;
    }

    public int getSize() {
        return size;
    }

    //constructor
    public LinkedListStudent() {
        this.head = null;
        this.size = 0;
    }

    //insertion in linked list (IN HEAD! O(1))
    public void insert(ObjectWithCoordinates point) {
        size++; //incrementing size
        if (head == null) { //if linked list is empty
            head = new LLNode(point, null);
        } else {
            head = new LLNode(point, head); //make new head
        }
    }

    //searching for given point by its coordinates
    public ObjectWithCoordinates search(int x, int y) {
        LLNode temp = head;
        //traversing through linked list
        while (temp != null) {
            if (temp.getData().getX() == x && temp.getData().getY() == y) {
                return temp.getData();
            }
            temp = temp.getNext();
        }
        //if not found
        return null;
    }
}

