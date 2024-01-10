import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Point {
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Point point = (Point) obj;
    return x == point.x && y == point.y;
    }   
}

public class RRTAlgorithm {
    private static final int MAX_ITERATIONS = 1000;
    private static final double STEP_SIZE = 1.0;

    public static List<Point> findPath(Point start, Point goal, int ROWS, int COLS, int[][] GRID) {
        List<Point> points = new ArrayList<>();
        points.add(start);

        Random random = new Random();

        for (int iteration = 0; iteration < MAX_ITERATIONS; iteration++) {
            Point randomPoint = new Point(random.nextInt(ROWS), random.nextInt(COLS));
            Point nearestPoint = findNearestPoint(points, randomPoint);
            Point newPoint = moveTowards(nearestPoint, randomPoint);

            if (isValidMove(nearestPoint, newPoint, ROWS, COLS, GRID)) {
                points.add(newPoint);

                if (distance(newPoint, goal) < STEP_SIZE) {
                    // Connect the last point to the goal
                    points.add(goal);
                    break;
                }
            }
        }

        return points;
    }

    private static Point findNearestPoint(List<Point> points, Point target) {
        double minDistance = Double.MAX_VALUE;
        Point nearestPoint = null;

        for (Point point : points) {
            double d = distance(point, target);
            if (d < minDistance) {
                minDistance = d;
                nearestPoint = point;
            }
        }

        return nearestPoint;
    }

    private static Point moveTowards(Point start, Point target) {
        double angle = Math.atan2(target.y - start.y, target.x - start.x);
        int newX = (int) (start.x + STEP_SIZE * Math.cos(angle));
        int newY = (int) (start.y + STEP_SIZE * Math.sin(angle));
        return new Point(newX, newY);
    }

    private static boolean isValidMove(Point start, Point end,int ROWS,int COLS, int[][] GRID) {
        if (!isValid(end.x, end.y,ROWS,COLS)) {
            return false;
        }

        // Check for obstacles in the path
        int minX = Math.min(start.x, end.x);
        int minY = Math.min(start.y, end.y);
        int maxX = Math.max(start.x, end.x);
        int maxY = Math.max(start.y, end.y);

        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                if (GRID[i][j] == 1) {
                    return false; // Obstacle in the path
                }
            }
        }

        return true;
    }

    private static boolean isValid(int x, int y, int ROWS, int COLS) {
        return x >= 0 && x < ROWS && y >= 0 && y < COLS;
    }

    private static double distance(Point a, Point b) {
        return Math.hypot(a.x - b.x, a.y - b.y);
    }

    public static void printGridWithPath(List<Point> path, int ROWS, int COLS, int[][] GRID) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Point currentPoint = new Point(i, j);
                if (GRID[i][j] == 1) {
                    System.out.print("# "); // Obstacle
                } else if (path.contains(currentPoint)) {
                    System.out.print("* "); // Path cell
                } else {
                    System.out.print(". "); // Empty cell
                }
            }
            System.out.println();
        }
    }
}
