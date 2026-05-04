package co.edu.uptc.view;

import co.edu.uptc.config.AppConfig;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    public GamePanel() {
        initPanel();
    }

    private void initPanel() {
        setPreferredSize(new Dimension(AppConfig.PANEL_WIDTH, AppConfig.PANEL_HEIGHT));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPlaceholderRacket(g);
        drawPlaceholderBall(g);
    }

    private void drawPlaceholderRacket(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(
                AppConfig.RACKET_X,
                AppConfig.PANEL_HEIGHT / 2 - AppConfig.RACKET_HEIGHT / 2,
                AppConfig.RACKET_WIDTH,
                AppConfig.RACKET_HEIGHT
        );
    }

    private void drawPlaceholderBall(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(
                AppConfig.PANEL_WIDTH / 2,
                AppConfig.PANEL_HEIGHT / 2,
                AppConfig.BALL_DIAMETER,
                AppConfig.BALL_DIAMETER
        );
    }
}
