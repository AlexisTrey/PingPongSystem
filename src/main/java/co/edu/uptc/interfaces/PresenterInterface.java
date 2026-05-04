package co.edu.uptc.interfaces;

public interface PresenterInterface {
    void setModel(ModelInterface model);
    void setView(ViewInterface view);
    void onStartGame();
    void onPauseGame();
    void onResumeGame();
    void onResetGame();
    void onAddBall();
    void onMoveRacket(int y);
    void onIncreaseSpeed();
    void onDecreaseSpeed();
    void refreshView();
    void onGameOver();
}
