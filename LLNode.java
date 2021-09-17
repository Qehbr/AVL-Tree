public class LLNode {

    private ObjectWithCoordinates data;
    private LLNode next;

    //constructor
    public LLNode(ObjectWithCoordinates data, LLNode next) {
        this.data = data;
        this.next = next;
    }

    //getters
    public ObjectWithCoordinates getData() {
        return data;
    }

    public LLNode getNext() {
        return next;
    }
}