package co.edu.uptc.presenter;

import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.model.MainModel;
import co.edu.uptc.util.TimeFormatter;
import co.edu.uptc.view.MainFrame;

import javax.swing.*;

public class MainPresenter implements PresenterInterface {

    private ModelInterface model;
    private ViewInterface view;
    private Thread timerThread;
    private volatile boolean timerRunning;

    public MainPresenter() {
    }

    @Override
    public void setModel(ModelInterface model) {
        this.model = model;
        ((MainModel) model).setOnGameOver(this::onGameOver);
        ((MainModel) model).setOnUpdate(this::refreshView);
    }

    @Override
    public void setView(ViewInterface view) {
        this.view = view;
    }

    @Override
    public void onStartGame() {
        model.startGame();
        startTimer();
    }

    @Override
    public void onPauseGame() {
        model.pauseGame();
    }

    @Override
    public void onResumeGame() {
        model.resumeGame();
    }

    @Override
    public void onResetGame() {
        stopTimer();
        model.resetGame();
        startTimer();
    }

    @Override
    public void onAddBall() {
        model.addBall();
    }

    @Override
    public void onMoveRacket(int y) {
        model.moveRacket(y);
    }

    @Override
    public void onMoveRacketBy(int deltaY) {
        model.moveRacketBy(deltaY);
    }

    @Override
    public void onIncreaseSpeed() {
        model.increaseSpeed();
    }

    @Override
    public void onDecreaseSpeed() {
        model.decreaseSpeed();
    }

    @Override
    public void refreshView() {
        SwingUtilities.invokeLater(() -> {
            if (view instanceof MainFrame) {
                MainFrame frame = (MainFrame) view;
                frame.updateGameState(model.getBalls(), model.getRacket());
            }
            updateInfoPanel();
            view.refresh();
        });
    }

    @Override
    public void onGameOver() {
        stopTimer();
        SwingUtilities.invokeLater(() -> view.showGameOver());
    }

    private void updateInfoPanel() {
        if (!(view instanceof MainFrame))
            return;
        MainFrame frame = (MainFrame) view;
        frame.updateStartTime(model.getStartTime());
        frame.updateElapsed(TimeFormatter.format(model.getElapsedSeconds()));
        frame.updateBounces(buildBouncesText());
    }

    private String buildBouncesText() {
        StringBuilder sb = new StringBuilder();
        java.util.List<co.edu.uptc.pojo.Ball> balls = model.getBalls();
        for (int i = 0; i < balls.size(); i++) {
            sb.append("Pelota ").append(i + 1)
                    .append(": ").append(balls.get(i).getBounceCount())
                    .append("\n");
        }
        return sb.toString();
    }

    private void startTimer() {
        timerRunning = true;
        timerThread = new Thread(() -> {
            while (timerRunning && !Thread.currentThread().isInterrupted()) {
                SwingUtilities.invokeLater(this::updateInfoPanel);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        timerThread.start();
    }

    private void stopTimer() {
        timerRunning = false;
        if (timerThread != null)
            timerThread.interrupt();
    }
}
