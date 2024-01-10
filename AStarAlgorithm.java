import java.util.*;

class Cell {
    int row, col;
    int f, g, h;
    Cell parent;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Cell cell = (Cell) obj;
    return row == cell.row && col == cell.col;
    }   
}

public class AStarAlgorithm {

    private static final int[][] DIRS = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}}; // Possible directions: up, left, down, right

    public static List<Cell> findPath(Cell start, Cell goal, int[][] GRID, int ROWS, int COLS) {
        List<Cell> path = new ArrayList<>();
        PriorityQueue<Cell> openSet = new PriorityQueue<>(Comparator.comparingInt(c -> c.f));
        Set<Cell> closedSet = new HashSet<>();

        start.g = 0;
        start.h = calculateHeuristic(start, goal);
        start.f = start.g + start.h;

        openSet.add(start);

        while (!openSet.isEmpty()) {
            Cell current = openSet.poll();

            if (current.row == goal.row && current.col == goal.col) {
                // Goal reached, reconstruct the path
                while (current != null) {
                    path.add(current);
                    current = current.parent;
                }
                Collections.reverse(path);
                return path;
            }

            closedSet.add(current);

            for (int[] dir : DIRS) {
                int newRow = current.row + dir[0];
                int newCol = current.col + dir[1];

                if (isValid(newRow, newCol, ROWS, COLS) && GRID[newRow][newCol] == 0) {
                    Cell neighbor = new Cell(newRow, newCol);
                    if (closedSet.contains(neighbor)) {
                        continue; // Ignore already processed cells
                    }

                    int tentativeG = current.g + 1;

                    if (!openSet.contains(neighbor) || tentativeG < neighbor.g) {
                        neighbor.parent = current;
                        neighbor.g = tentativeG;
                        neighbor.h = calculateHeuristic(neighbor, goal);
                        neighbor.f = neighbor.g + neighbor.h;

                        if (!openSet.contains(neighbor)) {
                            openSet.add(neighbor);
                        }
                    }
                }
            }
        }

        return path; // No path found
    }

    private static int calculateHeuristic(Cell a, Cell b) {
        // Manhattan distance heuristic
        return Math.abs(a.row - b.row) + Math.abs(a.col - b.col);
    }

    private static boolean isValid(int row, int col, int ROWS, int COLS) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    public static void printGridWithPath(List<Cell> path, int ROWS, int COLS, int[][] GRID) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Cell currentCell = new Cell(i, j);
                if (GRID[i][j] == 1) {
                    System.out.print("# "); // Blocked cell
                } else if (path.contains(currentCell)) {
                    System.out.print("* "); // Path cell
                } else {
                    System.out.print(". "); // Empty cell
                }
            }
            System.out.println();
        }
    }
    
}
