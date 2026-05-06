package co.edu.uptc.view;

import co.edu.uptc.config.AppConfig;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.pojo.Ball;
import co.edu.uptc.pojo.Racket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

public class GamePanel extends JPanel {

    private PresenterInterface presenter;
    private List<Ball> balls;
    private Racket racket;

    public GamePanel() {
        initPanel();
        addMouseMotionListener();
    }

    private void initPanel() {
        setPreferredSize(new Dimension(AppConfig.PANEL_WIDTH, AppConfig.PANEL_HEIGHT));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setFocusable(false);
    }

    private void addMouseMotionListener() {
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (presenter != null)
                    presenter.onMoveRacket(e.getY());
            }
        });
    }

    public void setPresenter(PresenterInterface presenter) {
        this.presenter = presenter;
    }

    public void updateState(List<Ball> balls, Racket racket) {
        this.balls = balls;
        this.racket = racket;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (racket != null)
            drawRacket(g);
        if (balls != null)
            drawBalls(g);
    }

    private void drawRacket(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(racket.getX(), racket.getY(), racket.getWidth(), racket.getHeight());
    }

    private void drawBalls(Graphics g) {
        g.setColor(Color.BLUE);
        synchronized (balls) {
            for (Ball ball : balls) {
                g.fillOval(ball.getX(), ball.getY(), ball.getDiameter(), ball.getDiameter());
            }
        }
    }
}
