public class StudentSolution implements MyInterface {

    private AVL<Point> xTree = new AVL<>(); //xTree stores point by x coordinate
    private AVL<Point> yTree = new AVL<>(); //yTree stores point by y coordinate

    @Override
    public void insertDataFromDBFile(String objectName, int objectX, int objectY) {
        Point toInsert = new Point(objectName, objectX, objectY); //creating point
        xTree.insert(toInsert.getX(), toInsert); //inserting in xTree O(log(n))
        yTree.insert(toInsert.getY(), toInsert); //inserting in yTree O(log(n))

    }

    @Override
    public String[] firstSolution(int leftTopX, int leftTopY, int rightBottomX, int rightBottomY) {
        //1 step: creating empty linkedLists for point in x and y ranges
        LinkedListStudent xLinkedList = new LinkedListStudent();
        LinkedListStudent yLinkedList = new LinkedListStudent();

        //2 step: performing range function for each linked list in given ranges
        xTree.range(xTree.getRoot(), xLinkedList, leftTopX, rightBottomX); //O(log(n)+m1)
        yTree.range(yTree.getRoot(), yLinkedList, leftTopY, rightBottomY); //O(log(n)+m2)

        //3 step: creating hashTable
        HashTable hash = new HashTable(xLinkedList.getSize() + 1);

        //temp variables for traversing through lists
        LLNode xTemp = xLinkedList.getHead();
        LLNode yTemp = yLinkedList.getHead();

        //4 step: inserting to hash
        while (xTemp != null) { //O(m1)
            hash.insert(xTemp.getData()); //θ(1)
            xTemp = xTemp.getNext();
        }

        //5 step: creating intersection linked list
        LinkedListStudent intersection = new LinkedListStudent();

        //6 step: for each node in yLinkedList
        while (yTemp != null) { // O(m2)
            //if it is in hash
            if (hash.search(yTemp.getData().getX(), yTemp.getData().getY()) != null) { //θ(1), O(m1)
                //add it to intersection list
                intersection.insert(yTemp.getData());
            }
            yTemp = yTemp.getNext();
        }

        //temp variable for traversing through intersection list
        LLNode intersectionPointer = intersection.getHead();

        //7 step: creating intersection array and copy data from intersection linked list
        String[] intersectionArray = new String[intersection.getSize()];
        for (int i = 0; i < intersection.getSize(); i++) { //O(min(m1,m2))
            intersectionArray[i] = intersectionPointer.getData().toString();
            intersectionPointer = intersectionPointer.getNext();
        }

        //8 step: return the result
        return intersectionArray;
    }

    @Override
    public String[] secondSolution(int leftTopX, int leftTopY, int rightBottomX, int rightBottomY) {
        //1 step:
        LinkedListStudent xLinkedList = new LinkedListStudent();
        LinkedListStudent yLinkedList = new LinkedListStudent();

        //2 step:
        xTree.range(xTree.getRoot(), xLinkedList, leftTopX, rightBottomX);
        yTree.range(yTree.getRoot(), yLinkedList, leftTopY, rightBottomY);

        //temp variables for traversing through lists
        LLNode xTemp = xLinkedList.getHead();
        LLNode yTemp = yLinkedList.getHead();

        //creating intersection linked list and array
        LinkedListStudent intersection = new LinkedListStudent();
        String[] intersectionArray = null;

        //3 step: find minimum and maximum between m1 and m2
        if (xLinkedList.getSize() < yLinkedList.getSize()) { //if m1 < m2
            //4 step: creating array for storing points from yLinkedList
            ObjectWithCoordinates[] yArr = new ObjectWithCoordinates[yLinkedList.getSize()];
            //copy data to array
            for (int i = 0; i < yLinkedList.getSize(); i++) { //O(m2)
                yArr[i] = yTemp.getData();
                yTemp = yTemp.getNext();
            }
            //5 step: for each node in xLinkedList
            while (xTemp != null) { //O(m1)
                //perform binarySearch by y coordinate O(log(m2))
                ObjectWithCoordinates obY = binarySearchY(yArr, 0, yArr.length - 1, xTemp.getData().getY(), xTemp.getData().getX());
                //if it's found
                if (obY != null) {
                    intersection.insert(obY); //add it to intersection
                }
                xTemp = xTemp.getNext();
            }
        } else { //if m1>=m2
            //4 step:
            ObjectWithCoordinates[] xArr = new ObjectWithCoordinates[xLinkedList.getSize()];
            //5 step : O(m1)
            for (int i = 0; i < xLinkedList.getSize(); i++) {
                xArr[i] = xTemp.getData();
                xTemp = xTemp.getNext();
            }
            //6step:
            while (yTemp != null) { //O(m2)
                //O(log(m1))
                ObjectWithCoordinates obX = binarySearchX(xArr, 0, xArr.length - 1, yTemp.getData().getX(), yTemp.getData().getY());
                if (obX != null) {
                    intersection.insert(obX);
                }
                yTemp = yTemp.getNext();
            }
        }

        //temp variable for traversing through intersection list
        LLNode intersectionPointer = intersection.getHead();
        //7 step:
        intersectionArray = new String[intersection.getSize()];
        for (int i = 0; i < intersection.getSize(); i++) {
            intersectionArray[i] = intersectionPointer.getData().toString();
            intersectionPointer = intersectionPointer.getNext();
        }
        return intersectionArray;
    }

    //utility functions for binarySearch by x or y coordinate
    private ObjectWithCoordinates binarySearchX(ObjectWithCoordinates arr[], int l, int r, int x, int y) {
        if (r >= l) {
            int mid = l + (r - l) / 2;
            //if the element is present at the middle return itself
            if (arr[mid].getX() == x && arr[mid].getY() == y) {
                return arr[mid];
            }
            //if element is smaller than mid, then it can only be present in left subarray
            if (arr[mid].getX() > x)
                return binarySearchX(arr, l, mid - 1, x, y);
            //else the element can only be presented in right subarray
            return binarySearchX(arr, mid + 1, r, x, y);
        }
        //element is not presented in array
        return null;
    }

    private ObjectWithCoordinates binarySearchY(ObjectWithCoordinates arr[], int l, int r, int y, int x) {
        if (r >= l) {
            int mid = l + (r - l) / 2;
            //if the element is present at the middle return itself
            if (arr[mid].getY() == y && arr[mid].getX() == x)
                return arr[mid];
            //if element is smaller than mid, then it can only be present in left subarray
            if (arr[mid].getY() > y)
                return binarySearchY(arr, l, mid - 1, y, x);
            //else the element can only be presented in right subarray
            return binarySearchY(arr, mid + 1, r, y, x);
        }
        //element is not presented in array
        return null;
    }
}



