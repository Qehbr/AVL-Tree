
public class AVLNode<T> {
    private T data;
    private int key;
    private AVLNode<T> left;
    private AVLNode<T> right;
    private AVLNode<T> father;
    private int height;

    //constructor
    public AVLNode(T data, int key, AVLNode<T> left, AVLNode<T> right, AVLNode<T> father) {
        this.data = data;
        this.key = key;
        this.left = left;
        this.right = right;
        this.father = father;
        this.height = 1; //height of new node is 1
    }

    //getters and setters
    public AVLNode<T> getLeftChild() {
        return left;
    }

    public AVLNode<T> getRightChild() {
        return right;
    }

    public AVLNode<T> getFather() {
        return father;
    }

    public int getKey() {
        return key;
    }

    public T getData() {
        return data;
    }

    public int getHeight() {
        return height;
    }

    public void setLeft(AVLNode<T> left) {
        this.left = left;
    }

    public void setRight(AVLNode<T> right) {
        this.right = right;
    }

    public void setFather(AVLNode<T> father) {
        this.father = father;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    @Override
    public String toString() {

        String s = "";

        if (getLeftChild() != null) {
            s += "(";
            s += getLeftChild().toString();
            s += ")";
        }
        s += getKey();

        if (getRightChild() != null) {
            s += "(";
            s += getRightChild().toString();
            s += ")";
        }

        return s;
    }
}

