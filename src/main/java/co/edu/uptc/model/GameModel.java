package co.edu.uptc.model;

import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.pojo.Ball;
import co.edu.uptc.pojo.Racket;

import java.util.List;

public class GameModel implements ModelInterface {

    private List<Ball> balls;
    private Racket racket;
    private boolean paused;
    private boolean gameOver;
    private String startTime;
    private long elapsedSeconds;

    public GameModel() {}

    @Override public void startGame()        {}
    @Override public void pauseGame()        {}
    @Override public void resumeGame()       {}
    @Override public void resetGame()        {}
    @Override public void addBall()          {}
    @Override public void moveRacket(int y)  {}
    @Override public void increaseSpeed()    {}
    @Override public void decreaseSpeed()    {}

    @Override public List<Ball> getBalls()   { return null; }
    @Override public Racket getRacket()      { return null; }
    @Override public boolean isGameOver()    { return false; }
    @Override public boolean isPaused()      { return false; }
    @Override public String getStartTime()   { return null; }
    @Override public long getElapsedSeconds(){ return 0; }
}
