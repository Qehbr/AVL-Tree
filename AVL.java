public class AVL<T> {

    //root variable
    private AVLNode<T> root;

    //constructor
    public AVL() {
        this.root = null;
    }

    //iterative! insertion in AVL tree
    public void insert(int key, T data) {
        AVLNode<T> temp = root;     //temp variable for traversing in tree
        if (temp == null) {
            root = new AVLNode<>(data, key, null, null, null); //if tree is empty make new node root
        } else {
            AVLNode<T> ptemp = temp.getFather(); //temp variable of parent of temp
            //1st step traversing and simple inserting
            while (temp != null) { //finding appropriate place O(log(n))
                ptemp = temp;
                if (temp.getKey() > key) {
                    temp = temp.getLeftChild();
                } else {
                    temp = temp.getRightChild();
                }
            }
            //inserting
            if (key <= ptemp.getKey()) {
                ptemp.setLeft(new AVLNode<>(data, key, null, null, ptemp));
            } else {
                ptemp.setRight(new AVLNode<>(data, key, null, null, ptemp));
            }
            //traversing back to the root and performing 2nd step of insertion
            while (ptemp != null) { //for each node O(log(n))
                ptemp.setHeight(max(height(ptemp.getLeftChild()), height(ptemp.getRightChild())) + 1); //updating height
                int balance = getBalance(ptemp); //getting balance factor
                //left-left case
                if (balance > 1 && key <= ptemp.getLeftChild().getKey()) {
                    if (ptemp.getFather() == null) {
                        root = ptemp.getLeftChild();
                    }
                    ptemp = rightRotate(ptemp); //performing rotation
                    //right-right case
                } else if (balance < -1 && key >= ptemp.getRightChild().getKey()) {
                    if (ptemp.getFather() == null) {
                        root = ptemp.getRightChild(); //performing rotation
                    }
                    ptemp = leftRotate(ptemp);
                    //left-right
                } else if (balance > 1 && key >= ptemp.getLeftChild().getKey()) {
                    if (ptemp.getFather() == null) {
                        root = ptemp.getLeftChild().getRightChild();
                    }
                    ptemp.setLeft(leftRotate(ptemp.getLeftChild())); //performing rotation
                    ptemp = rightRotate(ptemp); //performing rotation
                    //right-left
                } else if (balance < -1 && key <= ptemp.getRightChild().getKey()) {
                    if (ptemp.getFather() == null) {
                        root = ptemp.getRightChild().getLeftChild();
                    }
                    ptemp.setRight(rightRotate(ptemp.getRightChild())); //performing rotation
                    ptemp = leftRotate(ptemp); //performing rotation
                }
                ptemp = ptemp.getFather(); //traversing further to the root
            }
        }
    }
    //searching
    public T search(int key) {
        AVLNode<T> temp = root;
        while (temp != null) { //O(log(n))
            if (key == temp.getKey()) {
                return temp.getData();
            } else {
                if (temp.getKey() > key) {
                    temp = temp.getLeftChild();
                } else {
                    temp = temp.getRightChild();
                }
            }
        }
        return null;
    }

    //getter for root
    public AVLNode<T> getRoot() {
        return root;
    }

    //returns SORTED linked list nodes in given range
    public void range(AVLNode subRoot, LinkedListStudent ll, int a, int b) {
        //if range is not correct
        if (a > b) {
            System.out.println("a>b");
            return;
        }
        if (subRoot != null) {
            //if the key is not in the range, then right subtree is not what we are looking for
            if (subRoot.getKey() > b) {
                range(subRoot.getLeftChild(), ll, a, b); //look only in left subtree
                //if the key is not in the range, then left subtree is not what we are looking for
            } else if (subRoot.getKey() < a) {
                range(subRoot.getRightChild(), ll, a, b); //look only in right subtree
                //if it's in given range, then left and right subtrees can store the actual data
            } else {
                //because linked list inserts data in head it MUST!!! be in this order to get sorted linked list
                range(subRoot.getRightChild(), ll, a, b); //search in right subtree
                ll.insert((Point) subRoot.getData()); //add point to linkedList ll
                range(subRoot.getLeftChild(), ll, a, b); //search in left subtree
            }
        }
    }

    //rotations
    private AVLNode<T> rightRotate(AVLNode<T> z) {
        AVLNode<T> pz = z.getFather();
        AVLNode<T> y = z.getLeftChild();
        AVLNode<T> ry = y.getRightChild();

        //perform rotation
        z.setLeft(ry);
        if (ry != null) {
            ry.setFather(z);
        }
        y.setRight(z);
        z.setFather(y);
        y.setFather(pz);

        //update heights
        z.setHeight(max(height(z.getLeftChild()), height(z.getRightChild())) + 1);
        y.setHeight(max(height(y.getLeftChild()), height(y.getRightChild())) + 1);

        //updating parent node if needed
        if (pz != null) {
            if (pz.getLeftChild() != null) {
                if (pz.getLeftChild().getKey() == z.getKey()) {
                    pz.setLeft(y);
                } else {
                    pz.setRight(y);
                }
            } else {
                pz.setRight(y);
            }
        }
        //return new root
        return y;
    }

    private AVLNode<T> leftRotate(AVLNode<T> z) {
        AVLNode<T> pz = z.getFather();
        AVLNode<T> y = z.getRightChild();
        AVLNode<T> ly = y.getLeftChild();

        //perform rotation
        z.setRight(ly);
        if (ly != null) {
            ly.setFather(z);
        }
        y.setLeft(z);
        z.setFather(y);
        y.setFather(pz);

        //update heights
        z.setHeight(max(height(z.getLeftChild()), height(z.getRightChild())) + 1);
        y.setHeight(max(height(y.getLeftChild()), height(y.getRightChild())) + 1);

        //updating parent node if needed
        if (pz != null) {
            if (pz.getLeftChild().getKey() == z.getKey()) {
                pz.setLeft(y);
            } else {
                pz.setRight(y);
            }
        }
        //return new root
        return y;
    }

    //utility methods:
    //returns height of given node
    private int height(AVLNode<T> node) {
        return node != null ? node.getHeight() : 0;
    }

    //returns max between 2 integers
    private int max(int a, int b) {
        return a > b ? a : b;
    }

    //getting balance factor
    private int getBalance(AVLNode<T> node) {
        return node != null ? height((node.getLeftChild())) - height(node.getRightChild()) : 0;
    }
}

