import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    private JButton btnStart;
    private JButton btnClear;
    public Frame(String title){
        super(title);
        setSize(600,650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        container.add(Scene.getInstance(), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        container.add(bottomPanel, BorderLayout.SOUTH);


        btnStart = new JButton("Commencer");
        bottomPanel.add(btnStart);
        btnStart.addActionListener(e-> Scene.start());

        btnClear = new JButton("Effacer");
        bottomPanel.add(btnClear);
        btnClear.addActionListener(e->Scene.clear());

        setVisible(true);
    }
}
