import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cell {
    private int x;
    private int y;
    private Color color;
    private Set<Cell> neighbours = new HashSet<>(8);
    private boolean candidateToPermute;

    private static List<Cell> cells = new ArrayList<>();
    public static Color SURVIVAL_COLOR = new Color(0, 0, 0);
    public static Color DIE_COLOR = new Color(255, 255, 255);
    public static Color BORDER_COLOR = new Color(0, 100, 255);

    public static final int COLUMNS_COUNT = 200;
    public static final int CELLS_SIZE_SUM = 800;
    public static int SIZE = CELLS_SIZE_SUM / COLUMNS_COUNT;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        color = DIE_COLOR;
        cells.add(this);
    }

    public static void buildCells() {
        for (int i = 0; i < Cell.COLUMNS_COUNT; i++) {
            for (int j = 0; j < Cell.COLUMNS_COUNT; j++) {
                new Cell(j * Cell.SIZE, i * Cell.SIZE);
            }
        }

        for (Cell cell : cells) {
            int pos = cells.indexOf(cell);

            populateNeighbours(cell, pos - 1);
            populateNeighbours(cell, pos - COLUMNS_COUNT + 1);
            populateNeighbours(cell, pos - COLUMNS_COUNT);
            populateNeighbours(cell, pos - COLUMNS_COUNT - 1);
            populateNeighbours(cell, pos + 1);
            populateNeighbours(cell, pos + COLUMNS_COUNT + 1);
            populateNeighbours(cell, pos + COLUMNS_COUNT);
            populateNeighbours(cell, pos + COLUMNS_COUNT - 1);
        }
    }

    private static void populateNeighbours(Cell cell, int neighbourPosition) {
        if (neighbourPosition >= 0 && neighbourPosition < cells.size()) {
            Cell neighbour = cells.get(neighbourPosition);
            if (Math.abs(cell.x - neighbour.x) > SIZE || Math.abs(cell.y - neighbour.y) > SIZE) {
                return;
            }
            neighbour.addNeighbours(cell);
            cell.addNeighbours(neighbour);
        }
    }

    public static void clear(){
        for (Cell cell: cells){
            cell.setColor(DIE_COLOR);
        }
    }

    public static void testify(){
        for (Cell cell: cells){
            int aliveNeighbourCount = 0;
            for (Cell neighbour: cell.getNeighbours()){
                if(neighbour.getColor().equals(SURVIVAL_COLOR)){
                    aliveNeighbourCount++;
                }
            }
            if(cell.getColor().equals(DIE_COLOR)){
                if(aliveNeighbourCount == 3){
                    cell.candidateToPermute = true;
                }
            }else if(cell.getColor().equals(SURVIVAL_COLOR)){
                if(aliveNeighbourCount != 2 && aliveNeighbourCount != 3){
                    cell.candidateToPermute = true;
                }
            }
        }

        for (Cell cell: cells){
            cell.permute();
        }
    }

    public void permute() {
        if(candidateToPermute){
            color = color.equals(SURVIVAL_COLOR) ? DIE_COLOR : SURVIVAL_COLOR;
            candidateToPermute = false;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Set<Cell> getNeighbours() {
        return neighbours;
    }

    public void addNeighbours(Cell neighbour) {
        this.neighbours.add(neighbour);
    }

    public static List<Cell> getCells() {
        return cells;
    }

    public boolean isCandidateToPermute() {
        return candidateToPermute;
    }

    public void setCandidateToPermute(boolean candidateToPermute) {
        this.candidateToPermute = candidateToPermute;
    }

    @Override
    public String toString() {
        return String.format("[(%d, %d), %d, %s]", x, y, SIZE, color);
    }
}
