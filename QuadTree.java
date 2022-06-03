
public class QuadTree {

    private final QuadNode mQuadNode;
    private QuadNode root;
    private int numOfElements;


    public QuadTree() {
        mQuadNode = new EmptyNode();
        root = mQuadNode;
        numOfElements = 0;
    }


    public void insert(Point element) {
        root = root.add(element, 0, 0, 1024);
        numOfElements++;
    }


    public Point remove(double x, double y) {
        Point[] removed = {null};
        root = root.remove(x, y, 0, 0, 1024, removed);
        if (removed[0] != null) {
            numOfElements--;
            return removed[0];
        }
        return null;
    }


    public Point remove(Point toRemove) {
        Point[] removed = {null};
        root = root.remove(toRemove, 0, 0, 1024, removed);
        numOfElements--;
        return removed[0];


    }

    public LinkedList<String> duplicates() {
        LinkedList<String> results = new LinkedList<>();
        root.duplicates(results);
        return results;
    }


    public LinkedList<Point> regionSearch(double x, double y, double width, double height,
                                          int[] numOfVisits) {
        LinkedList<Point> result = new LinkedList<>();
        result = root.regionSearch(x, y, width, height,
                result, 0, 0, 1024, numOfVisits);

        return result;
    }


    public LinkedList<String> dump() {
        int[] numOfVisits = {0};
        LinkedList<String> list = new LinkedList<>();
        if (numOfElements == 0) {
            String temp = "Node at 0, 0, 1024: Empty";
            list.insert(temp);
            temp = "QuadTree Size: 1 QuadTree Nodes Printed.";
            list.insert(temp);
        } else {
            root.getDuplicates(0, 0, 1024, list, 0, numOfVisits);
            list.insert("QuadTree Size: " + numOfVisits[0] + " QuadTree Nodes Printed.");
        }

        return list;
    }

    public class EmptyNode implements QuadNode {


        public EmptyNode() {

        }


        public QuadNode add(Point element, double currentX, double currentY,
                            double split) {

            LeafNode temp = new LeafNode();
            temp.add(element, currentX, currentY, split);
            return temp;
        }


      /*  public LinkedList<Point> find(int x, int y, int currentX,
                                      int currentY, int split, LinkedList<Point> result) {
            return result;
        }*/


        public LinkedList<String> getDuplicates(double currentX, double currentY,
                                                double split, LinkedList<String> list, int numOfIndents,
                                                int[] numOfVisits) {
            String temp = "";
            for (int i = 0; i < numOfIndents; i++) {
                temp = temp + "  ";
            }
            temp = temp + "Node at " + QuadTree.this.toString(currentX , currentY , split) + ": Empty";
            list.insert(temp);
            numOfVisits[0]++;
            return list;
        }


        public LinkedList<String> duplicates(LinkedList<String> list) {

            return list;
        }


        public QuadNode remove(double x, double y, double currentX, double currentY,
                               double split, Point[] removed) {
            return this;
        }


        public QuadNode remove(Point toRemove, double currentX, double currentY,
                               double split, Point[] removed) {
            return this;
        }


        public LinkedList<Point> regionSearch(double x, double y, double width,
                                              double height, LinkedList<Point> result,
                                              double currentX, double currentY, double split, int[] numOfVisits) {
            numOfVisits[0]++;
            return result;
        }


    }

    public class LeafNode implements QuadNode {


        public LinkedList<Point> list;


        public LeafNode() {
            list = new LinkedList<Point>();
        }


        public LeafNode(LinkedList<Point> newList) {
            list = newList;
        }

        public QuadNode add(Point element, double currentX,
                            double currentY, double split) {
            if (list.getSize() == 0) {
                list.insert(element);
                return this;
            } else if (list.getSize() < 3) {
                list.insert(element);
                return this;
            } else if (list.getSize() >= 3) {
                int same = 0;
                for (int i = 0; i < list.getSize(); i++) {
                    if ((list.get(i)).compareTo(element) == 0)
                        same++;

                }

                if (same == list.getSize()) {
                    list.insert(element);
                    return this;
                }
            }

            QuadNode newInternal = new InternalNode();
            for (int i = 0; i < list.getSize(); i++) {
                newInternal = newInternal.add(list.get(i),
                        currentX, currentY, split);
            }
            newInternal = newInternal.add(element, currentX, currentY, split);
            return newInternal;


        }


        public LinkedList<String> getDuplicates(double currentX, double currentY,
                                                double split, LinkedList<String> result, int numOfIndents,
                                                int[] numOfVisits) {
            String indents = "";
            String temp = "";
            for (int i = 0; i < numOfIndents; i++)
                indents = indents + "  ";
            temp = temp + indents;
            temp = temp + "Node at " + QuadTree.this.toString(currentX,currentY ,split)+":";
            result.insert(temp);
            temp = "";
            for (int i = 0; i < this.list.getSize(); i++) {

                temp = indents + "(" + QuadTree.this.toString(this.list.get(i)) + ")";
                result.insert(temp);
            }
            numOfVisits[0]++;
            return result;
        }

        public LinkedList<String> duplicates(LinkedList<String> result) {

            for (int i = 0; i < this.list.getSize(); i++) {
                for (int j = i + 1; j < this.list.getSize(); j++) {
                    Point temp1 = this.list.get(i);
                    Point temp2 = this.list.get(j);
                    if (temp1.compareTo(temp2) == 0) {

                        String temp = "(" + QuadTree.this.toString(temp1.getX(), temp2.getY()) + ")";

                        if (!result.exists(temp))
                            result.insert(temp);
                    }
                }

            }

            return result;
        }


        public QuadNode remove(double x, double y, double currentX, double currentY,
                               double split, Point[] removed) {
            int i = 0;
            for (i = 0; i < list.getSize(); i++) {
                if (x == list.get(i).getX() && y == list.get(i).getY()) {
                    removed[0] = list.get(i);
                    list.delete(i);
                    break;
                }
            }
            if (list.getSize() == 0) {

                return mQuadNode;
            } else
                return this;
        }


        public QuadNode remove(Point toRemove, double currentX,
                               double currentY, double split, Point[] removed) {
            int index;
            for (index = 0; index < list.getSize(); index++) {
                if (toRemove == list.get(index)) {
                    removed[0] = list.get(index);
                    list.delete(index);
                    break;
                }
            }
            if (list.getSize() == 0) {

                return mQuadNode;
            } else
                return this;
        }


        public LinkedList<Point> regionSearch(double x, double y, double height,
                                              double width, LinkedList<Point> result, double currentX,
                                              double currentY, double split, int[] numOfVisits) {

            numOfVisits[0]++;

            for (int i = 0; i < list.getSize(); i++) {
                double tempX = (list.get(i)).getX();
                double tempY = (list.get(i)).getY();

                if (tempX >= x && tempX < x + width && tempY >= y
                        && tempY < y + height) {
                    result.insert(list.get(i));
                }
            }

            return result;

        }
    }


    public class InternalNode implements QuadNode {
        private QuadNode nW;
        private QuadNode nE;
        private QuadNode sW;
        private QuadNode sE;


        public InternalNode() {
            nW = (QuadNode) mQuadNode;
            nE = (QuadNode) mQuadNode;
            sW = (QuadNode) mQuadNode;
            sE = (QuadNode) mQuadNode;
        }


        public QuadNode add(Point element, double currentX,
                            double currentY, double split) {

            double bound = split / 2;
            double xBound = currentX + bound;
            double yBound = currentY + bound;
            if (element.getX() < xBound && element.getY() < yBound) {

                nW = nW.add(element, currentX, currentY, bound);
                return this;
            } else if (element.getX() >= xBound && element.getY() < yBound) {

                nE = nE.add(element, xBound, currentY, bound);
                return this;
            } else if (element.getX() < xBound && element.getY() >= yBound) {

                sW = sW.add(element, currentX, yBound, bound);
                return this;
            } else {

                sE = sE.add(element, xBound, yBound, bound);
                return this;
            }

        }


        public LinkedList<String> getDuplicates(double currentX, double currentY,
                                                double bound, LinkedList<String> list, int numOfIndents,
                                                int[] numOfVisits) {
            double split = bound / 2;
            String temp = "";
            for (int i = 0; i < numOfIndents; i++)
                temp = temp + "  ";
            temp = temp + "Node at " + QuadTree.this.toString(currentX ,currentY, bound) + ": Internal";
            list.insert(temp);
            list = nW.getDuplicates(currentX, currentY, split, list,
                    numOfIndents + 1, numOfVisits);
            list = nE.getDuplicates(currentX + split, currentY, split, list,
                    numOfIndents + 1, numOfVisits);
            list = sW.getDuplicates(currentX, currentY + split, split, list,
                    numOfIndents + 1, numOfVisits);
            list = sE.getDuplicates(currentX + split, currentY + split, split,
                    list, numOfIndents + 1, numOfVisits);
            numOfVisits[0]++;
            return list;
        }


        public LinkedList<String> duplicates(LinkedList<String> list) {

            if (nW != mQuadNode)
                list = nW.duplicates(list);

            if (nE != mQuadNode)
                list = nE.duplicates(list);

            if (sW != mQuadNode)
                list = sW.duplicates(list);

            if (sE != mQuadNode)
                list = sE.duplicates(list);

            return list;
        }


        public QuadNode remove(double x, double y, double currentX, double currentY,
                               double split, Point[] removed) {
            double half = split / 2;


            if (x < currentX + half && y < currentY + half)
                nW = nW.remove(x, y, currentX, currentY, half, removed);

            else if (x >= currentX + half && y < currentY + half)
                nE = nE.remove(x, y, currentX + half, currentY, half, removed);

            else if (x < currentX + half && y >= currentY + half)
                sW = sW.remove(x, y, currentX, currentY + half, half, removed);

            else
                sE = sE.remove(x, y, currentX + half, currentY + half, half,
                        removed);

            return trim();
        }


        public QuadNode remove(Point toRemove, double currentX, double currentY,
                               double split, Point[] removed) {
            double half = split / 2;
            double x = toRemove.getX();
            double y = toRemove.getY();

            if (x < currentX + half && y < currentY + half)
                nW = nW.remove(toRemove, currentX, currentY,
                        half, removed);

            else if (x >= currentX + half && y < currentY + half)
                nE = nE.remove(toRemove, currentX + half, currentY,
                        half, removed);

            else if (x < currentX + half && y >= currentY + half)
                sW = sW.remove(toRemove, currentX, currentY + half,
                        half, removed);

            else
                sE = sE.remove(toRemove, currentX + half, currentY + half,
                        half, removed);

            return trim();
        }


        private QuadNode trim() {


            if (nW.getClass().getName().compareTo("QuadTree$LeafNode") == 0
                    && nE == mQuadNode && sW == mQuadNode && sE == mQuadNode) {
                return nW;
            } else if (nW == mQuadNode &&
                    nE.getClass().getName().compareTo(
                            "QuadTree$LeafNode") == 0
                    && sW == mQuadNode && sE == mQuadNode) {
                return nE;
            } else if (nW == mQuadNode && nE == mQuadNode &&
                    sW.getClass().getName().compareTo(
                            "QuadTree$LeafNode") == 0
                    && sE == mQuadNode) {
                return sW;
            } else if (nW == mQuadNode && nE == mQuadNode && sW == mQuadNode &&
                    sE.getClass().getName().compareTo(
                            "QuadTree$LeafNode") == 0) {
                return sE;
            } else {

                LinkedList<Point> results = new LinkedList<Point>();

                if (nW.getClass().getName().compareTo(
                        "QuadTree$LeafNode") == 0) {
                    for (int i = 0; i < ((LeafNode) nW).list.getSize(); i++) {
                        results.insert(((LeafNode) nW).list.get(i));
                    }

                }
                if (nE.getClass().getName().compareTo(
                        "QuadTree$LeafNode") == 0) {
                    for (int i = 0; i < ((LeafNode) nE).list.getSize(); i++) {
                        results.insert(((LeafNode) nE).list.get(i));
                    }

                }
                if (sW.getClass().getName().compareTo(
                        "QuadTree$LeafNode") == 0) {
                    for (int i = 0; i < ((LeafNode) sW).list.getSize(); i++) {
                        results.insert(((LeafNode) sW).list.get(i));
                    }

                }
                if (sE.getClass().getName().compareTo(
                        "QuadTree$LeafNode") == 0) {
                    for (int i = 0; i < ((LeafNode) sE).list.getSize(); i++) {
                        results.insert(((LeafNode) sE).list.get(i));
                    }

                }

                if (results.getSize() == 3) {
                    return new LeafNode(results);
                }
                return this;
            }

        }


        public LinkedList<Point> regionSearch(double x, double y, double width,
                                              double height, LinkedList<Point> result,
                                              double currentX, double currentY, double split, int[] numOfVisits) {
            numOfVisits[0]++;

            double half = split / 2;
            double xBound = currentX + half;
            double yBound = currentY + half;
            double yMax = y + height - 1;
            double xMax = x + width - 1;
            LinkedList<Point> found = result;


            if (xBound > x && yBound > y) {

                found = nW.regionSearch(x, y, width, height, found,
                        currentX, currentY, half, numOfVisits);

            }

            if (xBound <= xMax && yBound > y) {

                found = nE.regionSearch(x, y, width, height, found,
                        xBound, currentY, half, numOfVisits);

            }

            if (xBound > x && yBound <= yMax) {

                found = sW.regionSearch(x, y, width, height, found,
                        currentX, yBound, half, numOfVisits);

            }

            if (xMax >= xBound && yMax >= yBound) {

                found = sE.regionSearch(x, y, width, height, found,
                        xBound, yBound, half, numOfVisits);

            }


            return found;
        }

    }

    public String toString(double x, double y) {

        String temp = "";


        int intX = (int) x;
        int intY = (int) y;


        if (intX == x) {
            temp = temp + intX;
        } else {
            temp = temp + x;
        }

        if (intY == y) {
            temp = temp + ", " + intY;
        } else {
            temp = temp + ", " + y;
        }

        return temp;


    }

    public String toString(Point point) {

        String temp = point.getName() + ", ";


        int intX = (int) point.getX();
        int intY = (int) point.getY();


        if (intX == point.getX()) {
            temp = temp + intX;
        } else {
            temp = temp + point.getX();
        }

        if (intY == point.getY()) {
            temp = temp + ", " + intY;
        } else {
            temp = temp + ", " + point.getY();
        }

        return temp;


    }

    public String toString(double currentX, double currentY, double split) {
        String temp = "";

        int x = (int) currentX;
        int y = (int) currentY;
        int s = (int) split;

        temp += (x == currentX) ?   x : ", " + currentX;

        temp += (y == currentY) ? ", " + y : ", " + currentY;

        temp += (s == split) ? ", " + s : ", " + split;

        return temp;
    }
}