
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Main {


    public static void main(String[] args) {

        LinkedList<String> result;
        Point tempPoint;

        String filename = "C:\\Users\\walid\\IdeaProjects\\java_code\\src\\P2test1.txt";

        Data data = new Data();
        Scanner scan = null;
        try {

            scan = new Scanner(System.in);
            while (scan.hasNext()) {

                String s = scan.next();
                String tempName = "";
                double tempX;
                double tempY;
                double tempW;
                double tempH;
                int intY;
                int intX;

                String badPoint;


                switch (s) {

                    case "insert":


                        tempName = scan.next();


                        if (scan.hasNextDouble()) {

                            tempX = scan.nextDouble();
                        } else {
                            tempX = -1;

                            //  badPoint = scan.nextLine();
                        }

                        if (scan.hasNextDouble()) {
                            tempY = scan.nextDouble();
                        } else {
                            // badPoint = scan.nextLine();
                            tempY = -1;
                        }

                        intX = (int) tempX;
                        intY = (int) tempY;


                        Point temp = new Point(tempX, tempY, tempName);

                        if (data.search(new Point(tempX, tempY, tempName))) {
                            System.out.println("Point Inserted: (" + temp.toString(intX, intY) + ")");
                            data.insert(temp);
                        } else {
                            System.out.println("Point REJECTED: (" + temp.toString(intX, intY) + ")");
                        }

                        break;
                    case "remove":
                        tempName = scan.next();
                        if (isNumber(tempName)) {

                            tempX = Double.parseDouble(tempName);
                            tempY = scan.nextDouble();

                            Point tempP = new Point(tempX, tempY);

                            intX = (int) tempX;
                            intY = (int) tempY;

                            if (!data.pointCheck(tempX, tempY)) {
                                System.out.println("Point Rejected: (" + tempP.toString(intX, intY) + ")");
                            } else {
                                tempPoint = data.removeByPoint(tempX,
                                        tempY);
                                if (tempPoint == null) {
                                    System.out.println("point Not Found: (" + tempP.toString(intX, intY) + ")");
                                } else {
                                    System.out.println("Point (" + tempPoint.toString(intX, intY) + ")" + " Removed");
                                }
                            }

                        } else {
                            tempPoint = data.removeByName(tempName);

                            if (tempPoint == null) {
                                System.out.println("point Not Removed: " + tempName);
                            } else {
                                intX = (int) tempPoint.getX();
                                intY = (int) tempPoint.getY();
                                System.out.println("Point (" + tempPoint.toString(intX,intY) + ")" + " Removed");
                            }
                        }
                        break;
                    case "regionsearch":
                        tempX = scan.nextDouble();
                        tempY = scan.nextDouble();
                        tempW = scan.nextDouble();
                        tempH = scan.nextDouble();
                        if (tempW > 0 && tempH > 0) {
                            result = data.regionSearch(tempX, tempY, tempW, tempH);
                            for (int i = 0; i < result.getSize(); i++)
                                System.out.println(result.get(i));
                        } else {
                            System.out.println("Rectangle Rejected: (" + toString( tempX,  tempY, tempW , tempH) + ")");
                        }
                        break;
                    case "search":
                        tempName = scan.next();
                        result = data.search(tempName);
                        for (int i = 0; i < result.getSize(); i++) {
                            System.out.println(result.get(i));
                        }
                        break;
                    case "duplicates":
                        System.out.println("Duplicate Points:");
                        result = data.duplicates();
                        for (int i = 0; i < result.getSize(); i++) {
                            System.out.println(result.get(i));
                        }
                        break;

                    case "dump":
                        result = data.dump();
                        for (int i = 0; i < result.getSize(); i++)
                            System.out.println(result.get(i));
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    private static boolean isNumber(String test) {

        try {
            Double.parseDouble(test);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    private static String toString(double x, double y, double w,double h) {
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