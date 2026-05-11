package co.edu.uptc.model;

import co.edu.uptc.util.Utilities;
import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.pojo.Ball;
import co.edu.uptc.pojo.Racket;
import co.edu.uptc.util.TimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainModel implements ModelInterface {

    private final List<Ball> balls = Collections.synchronizedList(new ArrayList<>());
    private final List<Thread> threads = Collections.synchronizedList(new ArrayList<>());

    private Racket racket;
    private boolean paused;
    private boolean gameOver;
    private String startTime;
    private long startMillis;
    private int globalSpeed;
    private long pauseStartMillis;
    private long totalPausedMillis;

    private Thread speedThread;
    private Runnable onGameOver;
    private Runnable onUpdate;

    public MainModel() {
    }

    public void setOnGameOver(Runnable onGameOver) {
        this.onGameOver = onGameOver;
    }

    public void setOnUpdate(Runnable onUpdate) {
        this.onUpdate = onUpdate;
    }

    @Override
    public void startGame() {
        balls.clear();
        threads.clear();
        paused = false;
        gameOver = false;
        globalSpeed = Utilities.INITIAL_SPEED_X;
        startTime = TimeFormatter.currentTime();
        startMillis = System.currentTimeMillis();
        racket = new Racket(
                Utilities.RACKET_X,
                Utilities.PANEL_HEIGHT / 2 - Utilities.RACKET_HEIGHT / 2,
                Utilities.RACKET_WIDTH,
                Utilities.RACKET_HEIGHT);
        addBall();
        startSpeedThread();
        totalPausedMillis = 0;
        pauseStartMillis = 0;
    }

    private void startSpeedThread() {
        if (speedThread != null)
            speedThread.interrupt();
        speedThread = new Thread(() -> {
            while (!gameOver && !Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(15_000); // aumenta la velocidad cada 15 segundos
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                increaseSpeed();
            }
        });
        speedThread.start();
    }

    @Override
    public void addBall() {
        if (gameOver)
            return;
        Ball ball = new Ball(
                Utilities.PANEL_WIDTH / 4,
                Utilities.PANEL_HEIGHT / 2,
                Utilities.BALL_DIAMETER,
                -globalSpeed,
                globalSpeed,
                0);
        balls.add(ball);
        Thread thread = new Thread(buildBallRunnable(ball));
        threads.add(thread);
        thread.start();
    }

    private Runnable buildBallRunnable(Ball ball) {
        return () -> {
            while (!gameOver && !Thread.currentThread().isInterrupted()) {
                if (!paused) {
                    moveBall(ball);
                    if (onUpdate != null)
                        onUpdate.run();
                }
                sleep();
            }
        };
    }

    private void moveBall(Ball ball) {
        ball.setX(ball.getX() + ball.getSpeedX());
        ball.setY(ball.getY() + ball.getSpeedY());
        bounceVertical(ball);
        bounceHorizontal(ball);
        checkRacketCollision(ball);
        checkGameOver(ball);
    }

    private void bounceVertical(Ball ball) {
        if (ball.getY() <= 0) {
            ball.setY(0);
            ball.setSpeedY(-ball.getSpeedY());
        }
        if (ball.getY() + ball.getDiameter() >= Utilities.PANEL_HEIGHT) {
            ball.setY(Utilities.PANEL_HEIGHT - ball.getDiameter());
            ball.setSpeedY(-ball.getSpeedY());
        }
    }

    private void bounceHorizontal(Ball ball) {
        if (ball.getX() <= 0) {
            ball.setX(0);
            ball.setSpeedX(-ball.getSpeedX());
        }
    }

    private void checkRacketCollision(Ball ball) {
        if (ball.getSpeedX() > 0
                && ball.getX() + ball.getDiameter() >= racket.getX()
                && ball.getY() + ball.getDiameter() >= racket.getY()
                && ball.getY() <= racket.getY() + racket.getHeight()) {
            ball.setX(racket.getX() - ball.getDiameter());
            ball.setSpeedX(-ball.getSpeedX());
            ball.setBounceCount(ball.getBounceCount() + 1);
        }
    }

    private void checkGameOver(Ball ball) {
        if (ball.getX() + ball.getDiameter() >= Utilities.PANEL_WIDTH) {
            gameOver = true;
            stopAllThreads();
            if (onGameOver != null)
                onGameOver.run();
        }
    }

    private void stopAllThreads() {
        for (Thread t : threads)
            t.interrupt();
        if (speedThread != null)
            speedThread.interrupt();
    }

    private void sleep() {
        try {
            Thread.sleep(Utilities.GAME_LOOP_DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void pauseGame() {
        paused = true;
        pauseStartMillis = System.currentTimeMillis();
    }

    @Override
    public void resumeGame() {
        paused = false;
        totalPausedMillis += System.currentTimeMillis() - pauseStartMillis;
    }

    @Override
    public void resetGame() {
        stopAllThreads();
        startGame();
    }

    @Override
    public void moveRacket(int y) {
        if (racket == null)
            return;
        int newY = y - racket.getHeight() / 2;
        if (newY < 0)
            newY = 0;
        if (newY + racket.getHeight() > Utilities.PANEL_HEIGHT) {
            newY = Utilities.PANEL_HEIGHT - racket.getHeight();
        }
        racket.setY(newY);
    }

    @Override
    public void moveRacketBy(int deltaY) {
        if (racket == null)
            return;
        int newY = racket.getY() + deltaY;
        if (newY < 0)
            newY = 0;
        if (newY + racket.getHeight() > Utilities.PANEL_HEIGHT)
            newY = Utilities.PANEL_HEIGHT - racket.getHeight();
        racket.setY(newY);
    }

    @Override
    public void increaseSpeed() {
        if (globalSpeed >= Utilities.MAX_SPEED)
            return;
        globalSpeed += Utilities.SPEED_INCREMENT;
        applySpeedToBalls();
    }

    @Override
    public void decreaseSpeed() {
        if (globalSpeed <= Utilities.MIN_SPEED)
            return;
        globalSpeed -= Utilities.SPEED_INCREMENT;
        applySpeedToBalls();
    }

    private void applySpeedToBalls() {
        synchronized (balls) {
            for (Ball ball : balls) {
                ball.setSpeedX(ball.getSpeedX() > 0 ? globalSpeed : -globalSpeed);
                ball.setSpeedY(ball.getSpeedY() > 0 ? globalSpeed : -globalSpeed);
            }
        }
    }

    @Override
    public List<Ball> getBalls() {
        return balls;
    }

    @Override
    public Racket getRacket() {
        return racket;
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public boolean isPaused() {
        return paused;
    }

    @Override
    public String getStartTime() {
        return startTime;
    }

    @Override
    public long getElapsedSeconds() {
        long pausado = totalPausedMillis;
        if (paused)
            pausado += System.currentTimeMillis() - pauseStartMillis;
        return (System.currentTimeMillis() - startMillis - pausado) / 1000;
    }
}
