package view;

import javax.swing.border.Border;
import java.awt.*;

public class RoundedBorder implements Border {

    private int radius;
    private Color borderColor;
    private int thickness;

    public RoundedBorder(int radius, Color borderColor, int thickness) {
        this.radius = radius;
        this.borderColor = borderColor;
        this.thickness = thickness;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(borderColor);

        // Draw rounded border
        g2.setStroke(new BasicStroke(thickness));
        g2.drawRoundRect(x + thickness / 2, y + thickness / 2,
                width - thickness, height - thickness,
                radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness, thickness, thickness, thickness);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
