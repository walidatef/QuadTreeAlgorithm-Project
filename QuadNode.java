
public interface QuadNode {


    public abstract QuadNode add(Point element, double currentX,
                                 double currentY, double split);

    public abstract LinkedList<String> getDuplicates(double currentX, double currentY,
                                                     double split, LinkedList<String> list, int numOfIndents,
                                                     int[] numOfVisits);

    public abstract LinkedList<String> duplicates(LinkedList<String> list);


    public abstract QuadNode remove(double x, double y, double currentX,
                                    double currentY, double split, Point[] removed);


    public abstract QuadNode remove(Point toRemove, double currentX,
                                    double currentY, double split, Point[] removed);


    public abstract LinkedList<Point> regionSearch(double x, double y, double width,
                                                   double height, LinkedList<Point> result, double currentX, double currentY,
                                                   double split, int[] numOfVisits);




}
