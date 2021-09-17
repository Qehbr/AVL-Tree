public class HashTable {

    private LinkedListStudent[] hashArray; //array storing actual data
    private int hashArrayMaxSize;

    public HashTable(int m) {
        //initialization of hashArray
        this.hashArray = new LinkedListStudent[m];
        this.hashArrayMaxSize = m;
        for (int i = 0; i < m; i++) { //O(m)
            hashArray[i] = new LinkedListStudent();
        }
    }

    //insertion by given hashArray
    public void insert(ObjectWithCoordinates object) {
        this.hashArray[hashFunction(object.getX(), object.getY())].insert(object); //θ(1), O(n)
    }

    //searching by given hashArray
    public ObjectWithCoordinates search(int x, int y) {
        return this.hashArray[hashFunction(x, y)].search(x, y); //θ(1), O(n)
    }

    //hashFunction
    private int hashFunction(int x, int y) {
        return (x + y) % hashArrayMaxSize;
    }
}

