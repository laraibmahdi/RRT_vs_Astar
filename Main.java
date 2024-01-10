import java.util.List;

public class Main {
        private static final int[][] GRID = {
            {0, 0, 0, 0, 1},
            {1, 1, 0, 0, 1},
            {0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0},
            {0, 0, 0, 0, 0}
        };

        private static final int ROWS = GRID.length;
        private static final int COLS = GRID[0].length;
        public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        Cell start = new Cell(0, 0);
        Cell goal = new Cell(ROWS - 1, COLS - 1);

        List<Cell> path = AStarAlgorithm.findPath(start, goal, GRID, ROWS, COLS);
        AStarAlgorithm.printGridWithPath(path, ROWS, COLS, GRID);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("A* Algorithm Execution Time: " + executionTime + " milliseconds");

        long startTimeRRT = System.currentTimeMillis();

        Point startRRT = new Point(0, 0);
        Point goalRRT = new Point(ROWS - 1, COLS - 1);

        List<Point> pathRRT = RRTAlgorithm.findPath(startRRT, goalRRT, ROWS, COLS, GRID);
        RRTAlgorithm.printGridWithPath(pathRRT, ROWS, COLS, GRID);


        long endTimeRRT = System.currentTimeMillis();
        long executionTimeRRT = endTimeRRT - startTimeRRT;
        System.out.println("RRT Algorithm Execution Time: " + executionTimeRRT + " milliseconds");

    }
}
