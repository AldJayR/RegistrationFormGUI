package view;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {

    private int arcWidth;
    private int arcHeight;

    public RoundedPanel(int arcWidth, int arcHeight) {
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        setOpaque(false); // Allows transparent corners
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());

        int width = getWidth();
        int height = getHeight();
        g2.fillRoundRect(0, 0, width, height, arcWidth, arcHeight);

        g2.dispose();
    }
}
