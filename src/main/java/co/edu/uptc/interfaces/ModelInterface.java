package co.edu.uptc.interfaces;

import co.edu.uptc.pojo.Ball;
import co.edu.uptc.pojo.Racket;

import java.util.List;

public interface ModelInterface {

    void startGame();
    void pauseGame();
    void resumeGame();
    void resetGame();
    void addBall();
    void moveRacket(int y);
    void moveRacketBy(int deltaY);
    void increaseSpeed();
    void decreaseSpeed();
    List<Ball> getBalls();
    Racket getRacket();
    boolean isGameOver();
    boolean isPaused();
    String getStartTime();
    long getElapsedSeconds();
}
