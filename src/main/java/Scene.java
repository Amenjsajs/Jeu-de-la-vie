import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Scene extends JPanel {
    private static Scene instance;
    private static long round;
    private static final int roundPlaceHeight = 30;
    private static boolean start;

    public Scene() {
        setPreferredSize(new Dimension(Cell.CELLS_SIZE_SUM, Cell.CELLS_SIZE_SUM));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Cell cell = cellHovered(e);
                if (cell != null) {
                    cell.setColor(cell.getColor().equals(Cell.SURVIVAL_COLOR) ? Cell.DIE_COLOR : Cell.SURVIVAL_COLOR);
                }
            }
        });
        Cell.buildCells();
        round = 0;
        start = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawRound(g2d);
        drawCell(g2d);
        repaint();
    }

    private void drawRound(Graphics2D g2d){
        g2d.setColor(Color.white);
        g2d.fillRect(0,0,Cell.CELLS_SIZE_SUM,roundPlaceHeight);
        g2d.setColor(Color.red);
        g2d.drawString(round+"",10,13);
    }

    private void drawCell(Graphics2D g2d) {
        for (Cell cell : Cell.getCells()) {
            g2d.setColor(cell.getColor());
            g2d.fillRect(cell.getX(), cell.getY()+roundPlaceHeight, Cell.SIZE, Cell.SIZE);
            g2d.setColor(Cell.BORDER_COLOR);
            g2d.drawRect(cell.getX(), cell.getY()+roundPlaceHeight, Cell.SIZE, Cell.SIZE);
        }
    }

    private Cell cellHovered(MouseEvent e) {
        for (Cell cell : Cell.getCells()) {
            if ((e.getX() >= cell.getX() && e.getX() <= cell.getX() + Cell.SIZE)
                    && (e.getY()-roundPlaceHeight >= cell.getY() && e.getY()-roundPlaceHeight <= cell.getY() + Cell.SIZE)) {
                return cell;
            }
        }
        return null;
    }

    public static void start() {
        start = true;
        Thread t = new Thread(() -> {
            while (start) {
                try {
                    Cell.testify();
                    round++;
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        });
        t.start();
    }

    public static Scene getInstance() {
        if (instance == null) instance = new Scene();
        return instance;
    }

    public static void clear(){
        start = false;
        round = 0;
        Cell.clear();
    }
}
