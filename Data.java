import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;


public class Data {
    private SkipList<String, Point> list;
    private QuadTree tree;


    public Data() {
        list = new SkipList<String, Point>();
        tree = new QuadTree();
    }


    public void insert(Point element) {
        list.insert(element.getName(), element);
        tree.insert(element);
    }


    public Point removeByPoint(double x, double y) {

        Point toRemove = tree.remove(x, y);
        if (toRemove == null)
            return null;
        list.remove(toRemove);
        return toRemove;
    }


    public Point removeByName(String name) {
        SkipNode<String, Point> result = list.remove(name);
        if (result != null) {
            tree.remove(result.element);
            return result.element;
        } else {
            return null;
        }
    }


    public LinkedList<String> dump() {
        LinkedList<String> result = new LinkedList<String>();
        result.insert("SkipList Dump:");

        SkipNode<String, Point>[] result2 = list.dump();
        if (result2.length == 1) {
            result.insert("level: 1 Value: null");
        } else {
            for (SkipNode<String ,Point> mSkipNode : result2) {

                if (mSkipNode.element != null) {

                    result.insert("level: " + (mSkipNode.forward.length) + " Value: (" +toString(mSkipNode) + ")");
                } else {
                    result.insert("level: " + (mSkipNode.forward.length) + " Value: " + mSkipNode.element);
                }

            }
        }
        result.insert("The SkipList's Size is: " + (result2.length - 1));
        LinkedList<String> treeDump = tree.dump();
        result.insert("QuadTree Dump:");
        for (int i = 0; i < treeDump.getSize(); i++) {
            result.insert(treeDump.get(i));
        }
        return result;
    }


    public LinkedList<String> search(String name) {
        SkipNode<String, Point>[] result = list.find(name);

        LinkedList<String> toReturn = new LinkedList<String>();

        if (result.length == 1) {
            toReturn.insert("Point Not Found: " + name);
        } else {

            for (SkipNode<String, Point> mResult : result) {
                if (mResult!= null) {


                    toReturn.insert("Point Found (" +toString(mResult)+ ")");
                }
            }
        }
        return toReturn;

    }

    public boolean search(Point point) {
        boolean isLetter = false;
        boolean check = false;
        SkipNode<String, Point>[] result = list.find(point.getName());

        for (char mChar : point.getName().toCharArray()) {

            if (isLetter(point.getName().charAt(0))) {

                isLetter = isLetter(mChar) || isDigit(mChar) || mChar == '_';

                if (!isLetter) break;
            }
        }

        if (isLetter && pointCheck(point.getX(), point.getY())) {

            if (result.length > 0) {

                for (SkipNode<String, Point> SkipNode : result) {
                    if (SkipNode != null) {

                        check = !((SkipNode.element().getX() == point.getX()) && (SkipNode.element().getY() == point.getY()));


                        if (!check) {

                            break;
                        }
                    } else {
                        check = true;
                    }
                }

            } else {
                check = true;
            }
        }


        return check;
    }


    public LinkedList<String> regionSearch(double x, double y,
                                           double width, double height) {
        int[] numOfVisits = {0};
        LinkedList<Point> result = tree.regionSearch(x, y, width, height, numOfVisits);

        LinkedList<String> output = new LinkedList<String>();

        output.insert("Points Intersecting Region: (" + toString( x,y , width , height ) + ")");

        for (int i = 0; i < result.getSize(); i++) {

            output.insert("Point Found: (" + toString(result.get(i)) + ")");
        }
        output.insert(numOfVisits[0] + " QuadTree Nodes Visited");
        return output;
    }


    public LinkedList<String> duplicates() {
        return tree.duplicates();
    }


    public boolean pointCheck(double x, double y) {


        return ((x < 1024.0 && x >= 0.0) && (y < 1024.0 && y >= 0.0));

    }

    public String toString(SkipNode<String, Point> skipNode) {
        String temp = skipNode.key;

        if (!skipNode.key.isEmpty()) {
            temp += ", ";
        }
        int intX = (int) skipNode.element().getX();
        int intY = (int) skipNode.element().getY();

        double xCoordinate = skipNode.element().getX();
        double yCoordinate = skipNode.element().getY();

        if (intX == xCoordinate) {
            temp = temp + intX;
        } else {
            temp = temp + xCoordinate;
        }

        if (intY == yCoordinate) {
            temp = temp + ", " + intY;
        } else {
            temp = temp + ", " + yCoordinate;
        }

        return temp;
    }

    public String toString(Point point) {
        String temp = point.getName()+", ";


        int intX = (int) point.getX();
        int intY = (int) point.getY();

        double xCoordinate = point.getX();
        double yCoordinate = point.getY();

        if (intX == xCoordinate) {
            temp = temp + intX;
        } else {
            temp = temp + xCoordinate;
        }

        if (intY == yCoordinate) {
            temp = temp + ", " + intY;
        } else {
            temp = temp + ", " + yCoordinate;
        }

        return temp;
    }
    public String toString(double x, double y, double w,double h) {
        String temp = "";

        int X = (int) x;
        int Y = (int) y;
        int H = (int) h;
        int W = (int) w;

        temp += (X == x) ?   X : ", " + x;

        temp += (Y == y) ? ", " + Y : ", " + y;
        temp += (W == w) ? ", " +W : ", " + w;
        temp += (H == h) ? ", " + H : ", " + h;

        return temp;
    }
}