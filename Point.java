public class Point implements ObjectWithCoordinates {
    //x, y coordinates and data
    private int x;
    private int y;
    private Object data;

    //constructor
    public Point(Object data, int x, int y) {
        this.x = x;
        this.y = y;
        this.data = data;
    }

    //getters and toString
    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return data + " X=" + x + " Y=" + y;
    }
}
